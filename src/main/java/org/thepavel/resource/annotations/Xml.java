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

package org.thepavel.resource.annotations;

import org.thepavel.resource.deserializer.Deserializer;
import org.thepavel.resource.configurator.Configurator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the content of a resource is XML. The resource will be mapped
 * to an object.
 *
 * If Jackson is on the classpath then it is going to be used to read the resource.
 *
 * If Jackson is not on the classpath then {@link IllegalStateException} is going to be thrown.
 *
 * If Jackson's XmlMapper requires additional configuration, it should be done in
 * beans of type {@link Configurator}. The bean must implement {@code Configurator<XmlMapper>}.
 *
 * The bean names of such beans then should be listed in {@link JacksonMapperConfigurator}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Xml {
  /**
   * Bean name of a {@link Deserializer} to be used to read the resource.
   */
  String deserializer() default "";
}
