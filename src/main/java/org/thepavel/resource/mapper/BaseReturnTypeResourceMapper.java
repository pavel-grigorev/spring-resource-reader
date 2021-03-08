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

package org.thepavel.resource.mapper;

import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.ResourceMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseReturnTypeResourceMapper<T> implements ResourceMapper<T> {
  private final Type declaredType;

  public BaseReturnTypeResourceMapper() {
    declaredType = getDeclaredType();
  }

  private Type getDeclaredType() {
    Type superclass = getClass().getGenericSuperclass();

    if (superclass instanceof ParameterizedType) {
      return ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    throw new IllegalStateException("Generic type argument of a superclass is undefined in " + getClass().getName());
  }

  @Override
  public boolean isFor(MethodMetadata methodMetadata) {
    return methodMetadata
        .getReturnTypeMetadata()
        .getResolvedType()
        .equals(declaredType);
  }
}
