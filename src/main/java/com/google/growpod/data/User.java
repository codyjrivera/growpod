// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.growpod.data;

import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Entity.Builder;
import com.google.cloud.datastore.Key;
import lombok.AllArgsConstructor;
import lombok.Data;

/** User data class. */
@Data
@AllArgsConstructor
public class User {

  /** A unique id. */
  private String id;

  /** The user's primary email. */
  private String email;

  /** This is the only name we track. */
  private String preferredName;

  /** The user's biography. */
  private String biography;

  /**
   * We do not wish to keep exact user locations -- this will store some approximate location data.
   * Unfortunately, this will only work in the US.
   */
  private String zipCode;

  /**
   * Generates a user from an entity.
   *
   * @param entity the entity to generate the user from
   * @return the new user with the entity's information.
   */
  public static User from(Entity entity) {
    String id = entity.getKey().getId().toString();
    String email = entity.getString("email");
    String preferredName = entity.getString("preferred-name");
    String biography = entity.getString("biography");
    String zipCode = entity.getString("zip-code");
    return new User(id, email, preferredName, biography, zipCode);
  }

  /**
   * Generates an entity from a user.
   *
   * @param instance The datastore instance the new entity will be associated with.
   * @return the new entity representing a user.
   */
  public Entity toEntity(DatastoreOptions instance) {
    // I use a different API here than in the portfolio
    String projectId = instance.getProjectId();
    Key key = Key.newBuilder(projectId, "User", Long.parseLong(id)).build();
    Builder builder = Entity.newBuilder(key);
    builder.set("email", email);
    builder.set("preferred-name", preferredName);
    builder.set("biography", biography);
    builder.set("zip-code", zipCode);
    return builder.build();
  }
}
