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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.ResourceMapper;
import org.thepavel.resource.annotations.GsonBuilderConfigurator;
import org.thepavel.resource.configurator.AnnotationConfiguratorChainAware;
import org.thepavel.resource.deserializer.JsonDeserializer;
import org.thepavel.resource.mapper.BufferedReaderResourceMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;

import static org.thepavel.resource.deserializer.gson.GsonUtils.getGsonBuilderClass;

public class GsonJsonDeserializer extends AnnotationConfiguratorChainAware implements JsonDeserializer {
  public static final String NAME = "org.thepavel.resource.deserializer.gson.internalGsonJsonDeserializer";

  private ResourceMapper<BufferedReader> bufferedReaderResourceMapper;

  @Autowired
  public void setBufferedReaderResourceMapper(@Qualifier(BufferedReaderResourceMapper.NAME) ResourceMapper<BufferedReader> bufferedReaderResourceMapper) {
    this.bufferedReaderResourceMapper = bufferedReaderResourceMapper;
  }

  @Override
  protected Class<? extends Annotation> getConfiguratorChainAnnotationType() {
    return GsonBuilderConfigurator.class;
  }

  @Override
  public boolean isAvailable() {
    return GsonUtils.isGsonOnClasspath();
  }

  @Override
  public Object read(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return GsonUtils.read(
        getGson(methodMetadata),
        () -> getReader(resource, methodMetadata),
        methodMetadata.getReturnTypeMetadata().getResolvedType()
    );
  }

  private Object getGson(MethodMetadata methodMetadata) {
    if (hasBuilderConfiguration(methodMetadata)) {
      Object builder = getGsonBuilder(methodMetadata);
      return GsonUtils.createGson(builder);
    } else {
      return GsonUtils.getGson();
    }
  }

  private boolean hasBuilderConfiguration(MethodMetadata methodMetadata) {
    return methodMetadata.getAnnotations().isPresent(getConfiguratorChainAnnotationType());
  }

  private Object getGsonBuilder(MethodMetadata methodMetadata) {
    return getConfiguredInstance(getGsonBuilderClass(), methodMetadata);
  }

  private Reader getReader(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return bufferedReaderResourceMapper.map(resource, methodMetadata);
  }
}
