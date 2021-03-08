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

package org.thepavel.resource.deserializer.gson;

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
import static org.thepavel.resource.util.Utils.getInstance;

public class GsonUtils {
  private static final Class<?> GSON_CLASS = getClassOrNull("com.google.gson.Gson");
  private static final Class<?> GSON_BUILDER_CLASS = getClassOrNull("com.google.gson.GsonBuilder");

  private GsonUtils() {
  }

  public static boolean isGsonOnClasspath() {
    return GSON_CLASS != null && GSON_BUILDER_CLASS != null;
  }

  public static Class<?> getGsonClass() {
    return requireNonNull(GSON_CLASS, "Gson class not found");
  }

  public static Class<?> getGsonBuilderClass() {
    return requireNonNull(GSON_BUILDER_CLASS, "GsonBuilder class not found");
  }

  public static Object getGson() {
    return getInstance(getGsonClass());
  }

  public static Object createGson(Object builder) {
    Method create = findMethod(getGsonBuilderClass(), "create");
    if (create == null) {
      throw canNotDeserialize("method GsonBuilder#create() not found");
    }
    return invokeMethod(create, builder);
  }

  public static Object read(Object gson, IOSupplier<Reader> reader, Type type) throws IOException {
    Method fromJson = findMethod(getGsonClass(), "fromJson", Reader.class, Type.class);
    if (fromJson == null) {
      throw canNotDeserialize("method Gson#fromJson(Reader,Type) not found");
    }
    return invokeMethod(fromJson, gson, reader.get(), type);
  }
}
