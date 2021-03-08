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

package org.thepavel.resource.integration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.ResourceMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(30)
public class CommaSeparatedStringsResourceMapper implements ResourceMapper<List<String>> {
  private ResourceMapper<String> stringResourceMapper;

  @Autowired
  public void setStringResourceMapper(ResourceMapper<String> stringResourceMapper) {
    this.stringResourceMapper = stringResourceMapper;
  }

  @Override
  public List<String> map(Resource resource, MethodMetadata methodMetadata) throws IOException {
    String s = stringResourceMapper.map(resource, methodMetadata);

    if (StringUtils.isBlank(s)) {
      return Collections.emptyList();
    }

    return Arrays
        .stream(s.split(","))
        .map(String::trim)
        .filter(StringUtils::isNotBlank)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isFor(MethodMetadata methodMetadata) {
    return methodMetadata.getAnnotations().isPresent(CommaSeparatedStrings.class);
  }
}
