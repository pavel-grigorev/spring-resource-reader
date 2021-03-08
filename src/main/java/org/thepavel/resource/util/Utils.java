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

package org.thepavel.resource.util;

public class Utils {
  private Utils() {
  }

  public static Class<?> getClassOrNull(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  public static <T> T getInstance(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException("Failed to instantiate " + clazz.getName(), e);
    }
  }

  public static IllegalStateException canNotDeserialize(String message) {
    return new IllegalStateException("Can not deserialize JSON: " + message);
  }
}
