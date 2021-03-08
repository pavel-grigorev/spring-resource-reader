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

import org.thepavel.resource.annotations.Classpath;
import org.thepavel.resource.annotations.GsonBuilderConfigurator;
import org.thepavel.resource.annotations.Json;
import org.thepavel.resource.annotations.ResourceReader;
import org.thepavel.resource.annotations.Xml;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

@ResourceReader
public interface TestResourceReader {
  @Classpath("${text.file}")
  String string();

  @Classpath("${text.file}")
  List<String> listLines();

  @Classpath("${text.file}")
  Stream<String> streamLines();

  @Classpath("${text.file}")
  byte[] bytes();

  @Classpath("${users.file}")
  @Json
  @GsonBuilderConfigurator("userDeserializer")
  List<User> users();

  @Classpath("${users.file}")
  @Json
  List<Map<String, Object>> usersAsMap();

  @Classpath("${book.file}")
  @Xml
  Book book();

  @Classpath("application.properties")
  Properties properties();

  @Classpath("strings.txt")
  @CommaSeparatedStrings
  List<String> commaSeparatedStrings();
}
