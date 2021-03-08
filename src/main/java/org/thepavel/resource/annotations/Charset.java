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

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;

/**
 * Configures a {@link java.nio.charset.Charset} to be used to read a resource.
 * Can be declared on methods that return {@link Reader} or {@link BufferedReader}
 * or implicitly use {@link Reader} to read a resource (String, JSON, XML, etc.).
 *
 * When not declared, UTF-8 is used by default.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Charset {
  /**
   * Charset name.
   */
  String value();
}
