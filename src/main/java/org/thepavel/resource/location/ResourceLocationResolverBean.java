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

package org.thepavel.resource.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.icomponent.util.AnnotationAttributes;
import org.thepavel.resource.annotations.Location;

import java.lang.annotation.Annotation;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ResourceLocationResolverBean implements ResourceLocationResolver {
  public static final String NAME = "org.thepavel.resource.location.internalResourceLocationResolverBean";

  private Environment environment;

  @Autowired
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  @Override
  public String getResourceLocation(Object[] arguments, MethodMetadata methodMetadata) {
    String location = getFromArguments(arguments);

    if (hasAnnotation(methodMetadata)) {
      location = getFromAnnotation(methodMetadata, location);
    }

    if (isNotBlank(location)) {
      location = resolvePlaceholders(location);
    }

    return location;
  }

  protected String getFromArguments(Object[] arguments) {
    return arguments.length == 1 && arguments[0] instanceof String ? (String) arguments[0] : null;
  }

  protected Class<? extends Annotation> getAnnotation() {
    return Location.class;
  }

  protected boolean hasAnnotation(MethodMetadata methodMetadata) {
    return methodMetadata.getAnnotations().isPresent(getAnnotation());
  }

  protected String getFromAnnotation(MethodMetadata methodMetadata, String fallback) {
    AnnotationAttributes<?> attributes = AnnotationAttributes
        .of(getAnnotation())
        .declaredOn(methodMetadata);

    String location = attributes
        .getValueAsString()
        .orElse(fallback);

    if (isBlank(location)) {
      return location;
    }

    return attributes
        .getString("prefix")
        .map(prefix -> prefix + location)
        .orElse(location);
  }

  protected String resolvePlaceholders(String text) {
    return environment.resolvePlaceholders(text);
  }
}
