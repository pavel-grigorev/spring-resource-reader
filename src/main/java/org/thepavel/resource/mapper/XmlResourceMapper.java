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
import org.thepavel.resource.annotations.Xml;
import org.thepavel.resource.deserializer.XmlDeserializer;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public class XmlResourceMapper extends BaseDeserializingResourceMapper<Object, XmlDeserializer> {
  public static final String NAME = "org.thepavel.resource.mapper.internalXmlResourceMapper";

  private Map<String, XmlDeserializer> xmlDeserializerMap;
  private List<XmlDeserializer> xmlDeserializerList;

  @Autowired
  public void setXmlDeserializerMap(Map<String, XmlDeserializer> xmlDeserializerMap) {
    this.xmlDeserializerMap = xmlDeserializerMap;
  }

  @Autowired
  public void setXmlDeserializerList(List<XmlDeserializer> xmlDeserializerList) {
    this.xmlDeserializerList = xmlDeserializerList;
  }

  @Override
  protected Class<? extends Annotation> getMarkerAnnotation() {
    return Xml.class;
  }

  @Override
  protected XmlDeserializer getDeserializer(String name) {
    return xmlDeserializerMap.get(name);
  }

  @Override
  protected List<XmlDeserializer> getDeserializers() {
    return xmlDeserializerList;
  }
}
