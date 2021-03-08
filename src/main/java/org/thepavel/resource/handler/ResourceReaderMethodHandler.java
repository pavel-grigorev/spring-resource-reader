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

package org.thepavel.resource.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.thepavel.icomponent.handler.MethodHandler;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.ResourceMapper;
import org.thepavel.resource.location.ResourceLocationResolver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ResourceReaderMethodHandler implements MethodHandler {
  public static final String NAME = "org.thepavel.resource.handler.internalResourceReaderMethodHandler";

  private List<ResourceMapper<?>> resourceMappers;
  private ResourceLocationResolver locationResolver;
  private ResourceLoader resourceLoader;

  @Autowired
  public void setResourceMappers(List<ResourceMapper<?>> resourceMappers) {
    this.resourceMappers = resourceMappers;
  }

  @Autowired
  public void setLocationResolver(ResourceLocationResolver locationResolver) {
    this.locationResolver = locationResolver;
  }

  @Autowired
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  public Object handle(Object[] arguments, MethodMetadata methodMetadata) {
    ResourceMapper<?> resourceMapper = getResourceMapper(methodMetadata);
    String location = getLocation(arguments, methodMetadata);
    Resource resource = getResource(location);
    try {
      return resourceMapper.map(resource, methodMetadata);
    } catch (IOException e) {
      throw new UncheckedIOException(e.getMessage(), e);
    }
  }

  private ResourceMapper<?> getResourceMapper(MethodMetadata methodMetadata) {
    return resourceMappers
        .stream()
        .filter(resourceMapper -> resourceMapper.isFor(methodMetadata))
        .findFirst()
        .orElseThrow(() -> unsupportedMethod(methodMetadata));
  }

  private static IllegalStateException unsupportedMethod(MethodMetadata methodMetadata) {
    return new IllegalStateException("Unsupported method " + methodMetadata.getSourceMethod());
  }

  private String getLocation(Object[] arguments, MethodMetadata methodMetadata) {
    String location = locationResolver.getResourceLocation(arguments, methodMetadata);

    if (isBlank(location)) {
      throw new IllegalStateException("Resource location is undefined");
    }

    return location;
  }

  private Resource getResource(String location) {
    return resourceLoader.getResource(location);
  }
}
