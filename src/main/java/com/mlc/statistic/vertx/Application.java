package com.mlc.statistic.vertx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        TransactionHandler handler = ctx.getBean(TransactionHandler.class);

        Vertx vertx = Vertx.factory.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/transactions").consumes("application/json").handler(handler);
        server.requestHandler(router::accept).listen(8080);
    }
}
