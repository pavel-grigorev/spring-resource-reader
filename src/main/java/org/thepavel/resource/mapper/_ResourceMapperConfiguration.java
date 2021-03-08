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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.thepavel.resource.ResourceMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

@Configuration(_ResourceMapperConfiguration.NAME)
public class _ResourceMapperConfiguration {
  public static final String NAME = "org.thepavel.resource.mapper.internal_ResourceMapperConfiguration";

  @Bean(SelfResourceMapper.NAME)
  @Order
  ResourceMapper<Resource> selfResourceMapper() {
    return new SelfResourceMapper();
  }

  @Bean(FileResourceMapper.NAME)
  @Order
  ResourceMapper<File> fileResourceMapper() {
    return new FileResourceMapper();
  }

  @Bean(InputStreamResourceMapper.NAME)
  @Order
  ResourceMapper<InputStream> inputStreamResourceMapper() {
    return new InputStreamResourceMapper();
  }

  @Bean(ReaderResourceMapper.NAME)
  @Order
  ResourceMapper<Reader> readerResourceMapper() {
    return new ReaderResourceMapper();
  }

  @Bean(BufferedReaderResourceMapper.NAME)
  @Order
  ResourceMapper<BufferedReader> bufferedReaderResourceMapper() {
    return new BufferedReaderResourceMapper();
  }

  @Bean(BytesResourceMapper.NAME)
  @Order
  ResourceMapper<byte[]> bytesResourceMapper() {
    return new BytesResourceMapper();
  }

  @Bean(StringResourceMapper.NAME)
  @Order
  ResourceMapper<String> stringResourceMapper() {
    return new StringResourceMapper();
  }

  @Bean(StringListResourceMapper.NAME)
  @Order
  ResourceMapper<List<String>> stringListResourceMapper() {
    return new StringListResourceMapper();
  }

  @Bean(StringStreamResourceMapper.NAME)
  @Order
  ResourceMapper<Stream<String>> stringStreamResourceMapper() {
    return new StringStreamResourceMapper();
  }

  @Bean(JsonResourceMapper.NAME)
  @Order(10)
  ResourceMapper<Object> jsonResourceMapper() {
    return new JsonResourceMapper();
  }

  @Bean(XmlResourceMapper.NAME)
  @Order(20)
  ResourceMapper<Object> xmlResourceMapper() {
    return new XmlResourceMapper();
  }

  @Bean(PropertiesResourceMapper.NAME)
  @Order
  ResourceMapper<Properties> propertiesResourceMapper() {
    return new PropertiesResourceMapper();
  }
}
