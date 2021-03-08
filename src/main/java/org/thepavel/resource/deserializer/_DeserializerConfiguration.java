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

package org.thepavel.resource.deserializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.configurator.AnnotationConfiguratorChain;
import org.thepavel.resource.deserializer.gson.GsonJsonDeserializer;
import org.thepavel.resource.deserializer.jackson.JacksonJsonDeserializer;
import org.thepavel.resource.deserializer.jackson.JacksonXmlDeserializer;

import java.lang.annotation.Annotation;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration(_DeserializerConfiguration.NAME)
public class _DeserializerConfiguration {
  public static final String NAME = "org.thepavel.resource.deserializer.internal_DeserializerConfiguration";

  @Bean(GsonJsonDeserializer.NAME)
  @Order(10)
  JsonDeserializer gsonJsonDeserializer() {
    return new GsonJsonDeserializer();
  }

  @Bean(JacksonJsonDeserializer.NAME)
  @Order(20)
  JsonDeserializer jacksonJsonDeserializer() {
    return new JacksonJsonDeserializer();
  }

  @Bean(JacksonXmlDeserializer.NAME)
  @Order(10)
  XmlDeserializer jacksonXmlDeserializer() {
    return new JacksonXmlDeserializer();
  }

  @Bean(AnnotationConfiguratorChain.NAME)
  @Scope(SCOPE_PROTOTYPE)
  AnnotationConfiguratorChain annotationConfiguratorChain(MethodMetadata methodMetadata, Class<? extends Annotation> annotationType) {
    return new AnnotationConfiguratorChain(methodMetadata, annotationType);
  }
}
