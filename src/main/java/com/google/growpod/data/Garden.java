// Copyright 2019 Google LLC
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

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Entity.Builder;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.LatLng;


/** Garden data class. */
public class Garden {

  /** A unique id. */
  private String id;

  /** The garden's name. */
  private String name;

  /** Latitude of garden. */
  private double lat;

  /** Longitude of garden. */
  private double lng;

  /** Foreign Key to this garden's administrator. Must be a valid user key. */
  private String adminId;

  /**
   * Constructs a new Garden.
   *
   * @param id A unique ID for the garden. This value must be supplied by the user.
   * @param name The garden's name
   * @param lat The garden's latitude
   * @param lng The garden's longitude
   * @param adminId The garden's administrator. Must be a valid user key.
   */
  public Garden(String id, String name, double lat, double lng, String adminId) {
    this.id = id;
    this.name = name;
    this.lat = lat;
    this.lng = lng;
    this.adminId = adminId;
  }

  /**
   * Generates a garden from an entity.
   *
   * @param entity the entity to generate the garden from
   * @return the new garden with the entity's information.
   */
  public static Garden from(Entity entity) {
    String id = entity.getKey().toUrlSafe();
    String name = entity.getString("name");
    LatLng latLng = entity.getLatLng("lat-lng");
    String adminId = entity.getString("admin-id");
    return new Garden(id, name, latLng.getLatitude(), latLng.getLongitude(), adminId);
  }

  /**
   * Generates an entity from a garden.
   * 
   * @return the new entity representing a garden.
   */
  public Entity toEntity() {
    // I use a different API here than in the portfolio
    Builder builder = Entity.newBuilder(Key.fromUrlSafe(id));
    builder.set("name", name);
    builder.set("lat-lng", LatLng.of(lat, lng));
    builder.set("admin-id", adminId);
    return builder.build();
  }

  /* Getters and setters. */
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public String getAdminId() {
    return adminId;
  }

  public void setAdminId(String id) {
    this.adminId = id;
  }
}
