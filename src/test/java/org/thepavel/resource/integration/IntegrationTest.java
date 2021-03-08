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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource("/application.properties")
public class IntegrationTest {
  @Autowired
  private TestResourceReader testResourceReader;

  @Test
  public void string() {
    assertEquals("line 1\nline 2", testResourceReader.string());
  }

  @Test
  public void stringList() {
    assertEquals(asList("line 1", "line 2"), testResourceReader.listLines());
  }

  @Test
  public void stringStream() {
    assertEquals(
        "line 1,line 2",
        testResourceReader.streamLines().collect(joining(","))
    );
  }

  @Test
  public void bytes() {
    assertArrayEquals("line 1\nline 2".getBytes(), testResourceReader.bytes());
  }

  @Test
  public void jsonToObject() {
    assertEquals(
        singletonList(new UserImpl("John Smith", "john.smith@gmail.com")),
        testResourceReader.users()
    );
  }

  @Test
  public void jsonToMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "John Smith");
    map.put("email", "john.smith@gmail.com");

    assertEquals(singletonList(map), testResourceReader.usersAsMap());
  }

  @Test
  public void xmlToObject() {
    assertEquals(
        new Book("The Theory of Everything", "Stephen Hawking"),
        testResourceReader.book()
    );
  }

  @Test
  public void properties() {
    Properties properties = testResourceReader.properties();

    assertNotNull(properties);
    assertEquals(3, properties.size());
    assertEquals("dummy.txt", properties.get("text.file"));
    assertEquals("users.json", properties.get("users.file"));
    assertEquals("book.xml", properties.get("book.file"));
  }

  @Test
  public void customMapper() {
    assertEquals(asList("one", "two", "three"), testResourceReader.commaSeparatedStrings());
  }
}
