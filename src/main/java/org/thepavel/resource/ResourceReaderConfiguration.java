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

package org.thepavel.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.thepavel.resource.deserializer._DeserializerConfiguration;
import org.thepavel.resource.handler.ResourceReaderMethodHandler;
import org.thepavel.resource.location.ResourceLocationResolver;
import org.thepavel.resource.location.ResourceLocationResolverBean;
import org.thepavel.resource.mapper._ResourceMapperConfiguration;

@Configuration
@Import({
    _DeserializerConfiguration.class,
    _ResourceMapperConfiguration.class
})
public class ResourceReaderConfiguration {
  @Bean(ResourceLocationResolverBean.NAME)
  ResourceLocationResolver resourceLocationResolver() {
    return new ResourceLocationResolverBean();
  }

  @Bean(ResourceReaderMethodHandler.NAME)
  ResourceReaderMethodHandler resourceReaderMethodHandler() {
    return new ResourceReaderMethodHandler();
  }
}
