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

import org.springframework.core.io.Resource;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.icomponent.util.AnnotationAttributes;
import org.thepavel.resource.deserializer.Deserializer;
import org.thepavel.resource.ResourceMapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

import static org.thepavel.resource.util.Utils.canNotDeserialize;

public abstract class BaseDeserializingResourceMapper<T, D extends Deserializer<T>> implements ResourceMapper<T> {
  private static final String ANNOTATION_ATTRIBUTE = "deserializer";

  protected abstract Class<? extends Annotation> getMarkerAnnotation();
  protected abstract D getDeserializer(String name);
  protected abstract List<D> getDeserializers();

  @Override
  public boolean isFor(MethodMetadata methodMetadata) {
    return methodMetadata.getAnnotations().isPresent(getMarkerAnnotation());
  }

  @Override
  public T map(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return getDeserializerName(methodMetadata)
        .map(this::getDeserializerNotNull)
        .orElseGet(this::getAvailableDeserializer)
        .read(resource, methodMetadata);
  }

  private Optional<String> getDeserializerName(MethodMetadata methodMetadata) {
    return AnnotationAttributes
        .of(getMarkerAnnotation())
        .declaredOn(methodMetadata)
        .getString(ANNOTATION_ATTRIBUTE);
  }

  private D getDeserializerNotNull(String name) {
    D deserializer = getDeserializer(name);
    if (deserializer == null) {
      throw canNotDeserialize("deserializer " + name + " not found");
    }
    if (!deserializer.isAvailable()) {
      throw canNotDeserialize("deserializer " + name + " is not available");
    }
    return deserializer;
  }

  private D getAvailableDeserializer() {
    return getDeserializers()
        .stream()
        .filter(Deserializer::isAvailable)
        .findFirst()
        .orElseThrow(() -> canNotDeserialize("no deserializer is available for mapper " + getClass().getName()));
  }
}
