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

package org.thepavel.resource.configurator;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.icomponent.util.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.util.List;

public class AnnotationConfiguratorChain implements ConfiguratorChain<Object> {
  public static final String NAME = "org.thepavel.resource.configurator.internalAnnotationBasedConfiguratorChain";

  private final MethodMetadata methodMetadata;
  private final Class<? extends Annotation> annotationType;
  private BeanFactory beanFactory;

  public AnnotationConfiguratorChain(MethodMetadata methodMetadata, Class<? extends Annotation> annotationType) {
    this.methodMetadata = methodMetadata;
    this.annotationType = annotationType;
  }

  @Autowired
  public void setBeanFactory(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  @Override
  public void configure(Object object) {
    AnnotationAttributes
        .of(annotationType)
        .declaredOn(methodMetadata)
        .getValueAsStrings()
        .ifPresent(configNames -> configure(object, configNames));
  }

  private void configure(Object object, List<String> configNames) {
    configNames
        .stream()
        .map(this::getConfigurator)
        .forEach(configurator -> configurator.configure(object));
  }

  @SuppressWarnings("unchecked")
  private Configurator<Object> getConfigurator(String name) {
    return (Configurator<Object>) beanFactory.getBean(name);
  }
}
