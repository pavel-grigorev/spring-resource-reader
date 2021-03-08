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

import org.springframework.core.annotation.AliasFor;
import org.springframework.util.ResourceUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a shortcut for {@link Location} with the prefix "file:".
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Location(prefix = ResourceUtils.FILE_URL_PREFIX)
public @interface File {
  /**
   * Resource location in the file system.
   */
  @AliasFor(annotation = Location.class)
  String value() default "";
}
