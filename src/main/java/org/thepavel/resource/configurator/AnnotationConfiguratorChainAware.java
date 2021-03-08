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

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.util.Utils;

import java.lang.annotation.Annotation;

public abstract class AnnotationConfiguratorChainAware {
  private ObjectProvider<AnnotationConfiguratorChain> configuratorChain;

  protected abstract Class<? extends Annotation> getConfiguratorChainAnnotationType();

  @Autowired
  public void setConfiguratorChain(@Qualifier(AnnotationConfiguratorChain.NAME) ObjectProvider<AnnotationConfiguratorChain> configuratorChain) {
    this.configuratorChain = configuratorChain;
  }

  protected AnnotationConfiguratorChain getConfiguratorChain(MethodMetadata methodMetadata, Class<? extends Annotation> annotationType) {
    return configuratorChain.getObject(methodMetadata, annotationType);
  }

  protected AnnotationConfiguratorChain getConfiguratorChain(MethodMetadata methodMetadata) {
    return getConfiguratorChain(methodMetadata, getConfiguratorChainAnnotationType());
  }

  protected Object getConfiguredInstance(Class<?> type, MethodMetadata methodMetadata) {
    Object instance = Utils.getInstance(type);
    getConfiguratorChain(methodMetadata).configure(instance);
    return instance;
  }
}
