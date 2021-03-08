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

import org.springframework.core.io.Resource;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.icomponent.util.AnnotationAttributes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ReaderResourceMapper extends BaseReturnTypeResourceMapper<Reader> {
  public static final String NAME = "org.thepavel.resource.mapper.internalReaderResourceMapper";

  @Override
  public Reader map(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return new InputStreamReader(resource.getInputStream(), getCharset(methodMetadata));
  }

  private static Charset getCharset(MethodMetadata methodMetadata) {
    return AnnotationAttributes
        .of(org.thepavel.resource.annotations.Charset.class)
        .declaredOn(methodMetadata)
        .getValueAsString()
        .map(Charset::forName)
        .orElse(StandardCharsets.UTF_8);
  }
}
