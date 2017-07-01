package com.vertexFun.verticles;

import com.vertexFun.controllers.UserController;
import com.vertexFun.controllers.UserControllerImplementation;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class UserVerticle extends AbstractVerticle {

    private UserController controller;

    public UserVerticle() {
        this.controller = new UserControllerImplementation();
    }

    @Override
    public void start(Future<Void> future) {
        controller.createSomeData();
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/html")
                    .end("<h1>Hello world</h1>");
        });

        router.route("/assets/*").handler(StaticHandler.create("assets"));
        router.route("/api/users").handler(BodyHandler.create());
        router.get("/api/users").handler(controller::getAll);
        router.post("/api/users").handler(controller::addOne);
        router.delete("/api/users/:id").handler(controller::deleteOne);

        vertx.
                createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        config().getInteger("http.port", 8080)
                        , result -> {
            if (result.succeeded()) {
                future.complete();
            } else {
                future.fail(result.cause());
            }
        });
    }
}
