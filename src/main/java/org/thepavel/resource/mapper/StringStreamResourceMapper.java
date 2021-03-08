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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

public class StringStreamResourceMapper extends BaseReturnTypeResourceMapper<Stream<String>> {
  public static final String NAME = "org.thepavel.resource.mapper.internalStringStreamResourceMapper";

  private ResourceMapper<BufferedReader> bufferedReaderResourceMapper;

  @Autowired
  public void setBufferedReaderResourceMapper(@Qualifier(BufferedReaderResourceMapper.NAME) ResourceMapper<BufferedReader> bufferedReaderResourceMapper) {
    this.bufferedReaderResourceMapper = bufferedReaderResourceMapper;
  }

  @Override
  public Stream<String> map(Resource resource, MethodMetadata methodMetadata) throws IOException {
    BufferedReader reader = getReader(resource, methodMetadata);
    try {
      return reader.lines().onClose(() -> close(reader));
    } catch (Throwable e) {
      close(reader, e);
      throw e;
    }
  }

  private BufferedReader getReader(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return bufferedReaderResourceMapper.map(resource, methodMetadata);
  }

  private static void close(Closeable c) {
    try {
      c.close();
    } catch (IOException e) {
      throw new UncheckedIOException(e.getMessage(), e);
    }
  }

  private static void close(Closeable c, Throwable context) {
    try {
      c.close();
    } catch (IOException e) {
      try {
        context.addSuppressed(e);
      } catch (Throwable ignored) {}
    }
  }
}
