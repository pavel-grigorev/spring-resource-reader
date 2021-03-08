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

import java.io.File;
import java.io.IOException;

public class FileResourceMapper extends BaseReturnTypeResourceMapper<File> {
  public static final String NAME = "org.thepavel.resource.mapper.internalFileResourceMapper";

  @Override
  public File map(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return resource.getFile();
  }
}
