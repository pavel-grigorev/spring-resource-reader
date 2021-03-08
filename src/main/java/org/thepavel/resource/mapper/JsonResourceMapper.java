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
import org.thepavel.resource.annotations.Json;
import org.thepavel.resource.deserializer.JsonDeserializer;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public class JsonResourceMapper extends BaseDeserializingResourceMapper<Object, JsonDeserializer> {
  public static final String NAME = "org.thepavel.resource.mapper.internalJsonResourceMapper";

  private Map<String, JsonDeserializer> jsonDeserializerMap;
  private List<JsonDeserializer> jsonDeserializerList;

  @Autowired
  public void setJsonDeserializerMap(Map<String, JsonDeserializer> jsonDeserializerMap) {
    this.jsonDeserializerMap = jsonDeserializerMap;
  }

  @Autowired
  public void setJsonDeserializerList(List<JsonDeserializer> jsonDeserializerList) {
    this.jsonDeserializerList = jsonDeserializerList;
  }

  @Override
  protected Class<? extends Annotation> getMarkerAnnotation() {
    return Json.class;
  }

  @Override
  protected JsonDeserializer getDeserializer(String name) {
    return jsonDeserializerMap.get(name);
  }

  @Override
  protected List<JsonDeserializer> getDeserializers() {
    return jsonDeserializerList;
  }
}
