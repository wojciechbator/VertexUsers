package com.vertexFun.controllers;

import io.vertx.ext.web.RoutingContext;

public interface UserController {
    void createSomeData();
    void getAll(RoutingContext routingContext);
    void addOne(RoutingContext routingContext);
    void deleteOne(RoutingContext routingContext);
}
