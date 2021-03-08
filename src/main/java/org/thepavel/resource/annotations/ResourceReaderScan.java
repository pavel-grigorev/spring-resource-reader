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

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.thepavel.icomponent.InterfaceComponentScan;
import org.thepavel.resource.ResourceReaderConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ResourceReaderConfiguration.class)
@InterfaceComponentScan(annotation = ResourceReader.class)
public @interface ResourceReaderScan {
  /**
   * Packages to scan.
   */
  @AliasFor(annotation = InterfaceComponentScan.class)
  String[] value() default {};

  /**
   * Packages to scan.
   */
  @AliasFor(annotation = InterfaceComponentScan.class)
  String[] basePackages() default {};

  /**
   * The package of each class will be scanned.
   */
  @AliasFor(annotation = InterfaceComponentScan.class)
  Class<?>[] basePackageClasses() default {};
}
