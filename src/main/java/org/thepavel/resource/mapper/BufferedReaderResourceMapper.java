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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.ResourceMapper;
import org.thepavel.resource.annotations.BufferSize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import static org.springframework.core.annotation.MergedAnnotation.VALUE;

public class BufferedReaderResourceMapper extends BaseReturnTypeResourceMapper<BufferedReader> {
  public static final String NAME = "org.thepavel.resource.mapper.internalBufferedReaderResourceMapper";

  private ResourceMapper<Reader> readerResourceMapper;

  @Autowired
  public void setReaderResourceMapper(@Qualifier(ReaderResourceMapper.NAME) ResourceMapper<Reader> readerResourceMapper) {
    this.readerResourceMapper = readerResourceMapper;
  }

  @Override
  public BufferedReader map(Resource resource, MethodMetadata methodMetadata) throws IOException {
    Reader reader = getReader(resource, methodMetadata);

    return getBufferSize(methodMetadata)
        .map(size -> new BufferedReader(reader, size))
        .orElseGet(() -> new BufferedReader(reader));
  }

  private Reader getReader(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return readerResourceMapper.map(resource, methodMetadata);
  }

  private static Optional<Integer> getBufferSize(MethodMetadata methodMetadata) {
    return methodMetadata
        .getAnnotations()
        .get(BufferSize.class)
        .getValue(VALUE, Integer.class)
        .filter(size -> size > 0);
  }
}
