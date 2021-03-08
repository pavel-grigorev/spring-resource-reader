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

package org.thepavel.resource.deserializer.jackson;

import org.springframework.core.io.Resource;
import org.thepavel.icomponent.metadata.MethodMetadata;
import org.thepavel.resource.deserializer.XmlDeserializer;

import java.io.IOException;

public class JacksonXmlDeserializer extends JacksonDeserializerSupport implements XmlDeserializer {
  public static final String NAME = "org.thepavel.resource.deserializer.jackson.internalJacksonXmlDeserializer";

  @Override
  public boolean isAvailable() {
    return JacksonUtils.isXmlMapperOnClasspath();
  }

  @Override
  public Object read(Resource resource, MethodMetadata methodMetadata) throws IOException {
    return read(JacksonUtils.getXmlMapperClass(), resource, methodMetadata);
  }
}
