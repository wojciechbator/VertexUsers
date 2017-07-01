package com.vertexFun;

import com.vertexFun.verticles.UserVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class BasicTests {
    private Vertx vertx;
    private int port;

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();
        port = 8081;
        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.port", port));
        vertx.deployVerticle(UserVerticle.class.getName(), options, testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void test_basicSetup(TestContext context) {
        final Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains(("Hello")));
                        async.complete();
                    });
                });
    }
}

