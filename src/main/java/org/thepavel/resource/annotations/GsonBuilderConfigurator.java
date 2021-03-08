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

import org.thepavel.resource.configurator.Configurator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applies configurators to GsonBuilder when a JSON resource is being
 * mapped to an object, i.e. the {@link Json} annotation is declared,
 * and Gson is on the classpath.
 *
 * @see Configurator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GsonBuilderConfigurator {
  /**
   * Bean names of the beans that implement {@code Configurator<GsonBuilder>}.
   * All configurators are applied in the order of declaration.
   */
  String[] value();
}
