/*
 * Copyright (c) 2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.resource.deserializer.jackson;

import org.thepavel.resource.util.IOSupplier;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;
import static org.thepavel.resource.util.Utils.canNotDeserialize;
import static org.thepavel.resource.util.Utils.getClassOrNull;

public class JacksonUtils {
  private static final Class<?> OBJECT_MAPPER_CLASS = getClassOrNull("com.fasterxml.jackson.databind.ObjectMapper");
  private static final Class<?> XML_MAPPER_CLASS = getClassOrNull("com.fasterxml.jackson.dataformat.xml.XmlMapper");
  private static final Class<?> TYPE_FACTORY_CLASS = getClassOrNull("com.fasterxml.jackson.databind.type.TypeFactory");
  private static final Class<?> JAVA_TYPE_CLASS = getClassOrNull("com.fasterxml.jackson.databind.JavaType");

  private JacksonUtils() {
  }

  public static boolean isObjectMapperOnClasspath() {
    return OBJECT_MAPPER_CLASS != null && isTypeFactoryOnClasspath();
  }

  public static boolean isXmlMapperOnClasspath() {
    return XML_MAPPER_CLASS != null && isTypeFactoryOnClasspath();
  }

  private static boolean isTypeFactoryOnClasspath() {
    return TYPE_FACTORY_CLASS != null && JAVA_TYPE_CLASS != null;
  }

  public static Class<?> getObjectMapperClass() {
    return requireNonNull(OBJECT_MAPPER_CLASS, "ObjectMapper class not found");
  }

  public static Class<?> getXmlMapperClass() {
    return requireNonNull(XML_MAPPER_CLASS, "XmlMapper class not found");
  }

  private static Class<?> getTypeFactoryClass() {
    return requireNonNull(TYPE_FACTORY_CLASS, "TypeFactory class not found");
  }

  private static Class<?> getJavaTypeClass() {
    return requireNonNull(JAVA_TYPE_CLASS, "JavaType class not found");
  }

  public static Object read(Object mapper, IOSupplier<Reader> reader, Type type) throws IOException {
    Class<?> mapperClass = mapper.getClass();
    String mapperClassName = mapperClass.getSimpleName();

    Method getTypeFactory = findMethod(mapperClass, "getTypeFactory");
    if (getTypeFactory == null) {
      throw canNotDeserialize("method " + mapperClassName + "#getTypeFactory() not found");
    }

    Method readValue = findMethod(mapperClass, "readValue", Reader.class, getJavaTypeClass());
    if (readValue == null) {
      throw canNotDeserialize("method " + mapperClassName + "#readValue(Reader,JavaType) not found");
    }

    Method constructType = findMethod(getTypeFactoryClass(), "constructType", Type.class);
    if (constructType == null) {
      throw canNotDeserialize("method TypeFactory#constructType(Type) not found");
    }

    Object typeFactory = invokeMethod(getTypeFactory, mapper);
    if (typeFactory == null) {
      throw canNotDeserialize("method " + mapperClassName + "#getTypeFactory() returned null");
    }

    Object javaType = invokeMethod(constructType, typeFactory, type);
    if (javaType == null) {
      throw canNotDeserialize("method TypeFactory#constructType(Type) returned null");
    }

    return invokeMethod(readValue, mapper, reader.get(), javaType);
  }
}
