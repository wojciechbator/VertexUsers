package com.vertexFun.controllers;

import com.vertexFun.domain.User;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserControllerImplementation implements UserController {
    private Map<Integer, User> products = new LinkedHashMap<>();

    public void createSomeData() {
        User mark = new User("Mark", "mark@fb.com");
        User larry = new User("Larry", "larry@fb.com");
        products.put(mark.getId(), mark);
        products.put(larry.getId(), larry);
    }

    public void getAll(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=UTF-8")
                .end(Json.encodePrettily(products.values()));
    }

    public void addOne(RoutingContext routingContext) {
        final User user = Json.decodeValue(routingContext.getBodyAsString(), User.class);
        products.put(user.getId(), user);
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=UTF-8")
                .end(Json.encodePrettily(user));
    }

    public void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Integer idAsInteger = Integer.valueOf(id);
            products.remove(idAsInteger);
        }
    }
}
