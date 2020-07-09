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

package com.google.growpod.servlets;

import com.google.growpod.data.Garden;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles garden entities on the server.
 *
 * <p>API DOCUMENTATION: /garden/{id} {id} -- A garden UUID GET: Retrieves the garden data structure
 * for {id} No parameters Returns data in JSON format along with (200 OK), otherwise (404 NOT FOUND)
 * /garden/{id}/plant-list {id} -- A garden UUID GET: Retrieves all plants currently on {id} garden
 * No parameters Returns data in JSON format along with (200 OK), otherwise (404 NOT FOUND)
 * /garden/{id}/user-list {id} -- A garden UUID GET: Retrieves all users currently on {id} garden No
 * parameters Returns data in JSON format along with (200 OK), otherwise (404 NOT FOUND)
 */
@WebServlet({"/garden", "/garden/*"})
public class GardenServlet extends HttpServlet {

  static final long serialVersionUID = 1L;

  /** Static test data. */
  private static final double newYorkLat = 40.82;

  private static final double newYorkLng = -73.93;
  private static final Map<String, Garden> GARDEN_MAP = createGardenMap();

  private static Map<String, Garden> createGardenMap() {
    Map<String, Garden> map = new HashMap<String, Garden>();
    map.put("0", new Garden("0", "Flower Garden", newYorkLat, newYorkLng, "0"));
    map.put("1", new Garden("1", "Pea Garden", newYorkLat, newYorkLng, "1"));
    return Collections.unmodifiableMap(map);
  }

  private static final Map<String, List<String>> GARDEN_USER_LIST_MAP = createGardenUserListMap();

  private static Map<String, List<String>> createGardenUserListMap() {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    map.put("0", Arrays.asList("0", "1"));
    map.put("1", Arrays.asList("0", "1", "2"));
    return Collections.unmodifiableMap(map);
  }

  private static final Map<String, List<String>> GARDEN_PLANT_LIST_MAP = createGardenPlantListMap();

  private static Map<String, List<String>> createGardenPlantListMap() {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    map.put("0", Arrays.asList("0", "1"));
    map.put("1", Arrays.asList("2", "3"));
    return Collections.unmodifiableMap(map);
  }

  /**
   * Processes HTTP GET requests for the /garden servlet. Dispatches functionality based on
   * structure of GET request.
   *
   * @param request Information about the GET Request
   * @param response Information about the servlet's response
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    /* uriList will have "" as element 0 */
    String[] uriList = request.getRequestURI().split("/");
    assert (uriList[1].equals("garden"));

    // Dispatch based on method specified.
    // /garden/{id}
    if (uriList.length == 3) {
      Garden garden = getGardenById(uriList[2]);
      if (garden == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid garden id: " + uriList[2]);
        return;
      }
      response.setContentType("application/json;");
      response.getWriter().println(new Gson().toJson(garden));
      return;
    }

    if (uriList.length == 4) {
      if (uriList[3].equals("user-list")) {
        // /garden/{id}/user-list
        List<String> list = getGardenUserListById(uriList[2]);
        if (list == null) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid garden id: " + uriList[2]);
          return;
        }
        response.setContentType("application/json;");
        response.getWriter().println(new Gson().toJson(list));
        return;
      } else if (uriList[3].equals("plant-list")) {
        // /garden/{id}/plant-list
        List<String> list = getGardenPlantListById(uriList[2]);
        if (list == null) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid garden id: " + uriList[2]);
          return;
        }
        response.setContentType("application/json;");
        response.getWriter().println(new Gson().toJson(list));
        return;
      }
      // If the uriList does not match the above two methods, fall through.
    }
    response.sendError(
        HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unimplemented: " + request.getRequestURI());
  }

  /**
   * Retrieves a garden in the database by id, or null if said id does not exist.
   *
   * @param id the garden's id
   * @return the garden with id's data or null.
   */
  private Garden getGardenById(String id) {
    // MOCK IMPLEMENTATION
    return GARDEN_MAP.get(id);
  }

  /**
   * Retrieves a list of garden members. Returns null if the garden does not exist.
   *
   * @param id the garden's id
   * @return a list of user ids in the garden or null.
   */
  private List<String> getGardenUserListById(String id) {
    // MOCK IMPLEMENTATION
    return GARDEN_USER_LIST_MAP.get(id);
  }

  /**
   * Retrieves a list of garden plants. Returns null if the garden does not exist.
   *
   * @param id the garden's id
   * @return a list of plant ids in the garden or null.
   */
  private List<String> getGardenPlantListById(String id) {
    // MOCK IMPLEMENTATION
    return GARDEN_PLANT_LIST_MAP.get(id);
  }
}
