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

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import org.thepavel.resource.configurator.Configurator;

import java.lang.reflect.Type;

@Component
public class UserDeserializer implements JsonDeserializer<User>, Configurator<GsonBuilder> {
  @Override
  public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    JsonObject jsonObject = json.getAsJsonObject();
    String name = jsonObject.get("name").getAsString();
    String email = jsonObject.get("email").getAsString();
    return new UserImpl(name, email);
  }

  @Override
  public void configure(GsonBuilder gsonBuilder) {
    gsonBuilder.registerTypeAdapter(User.class, this);
  }
}
