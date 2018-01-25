package com.mlc.statistic.vertx;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

@SpringBootApplication
public class Application {

    @Autowired
    private HTTPServerVerticle httpServerVerticle;

    @Autowired
    private TransactionVerticle transactionVerticle;

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        return router;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        /*
         * TransactionHandler handler = ctx.getBean(TransactionHandler.class);
         * 
         * Vertx vertx = Vertx.factory.vertx(); HttpServer server =
         * vertx.createHttpServer(); Router router = Router.router(vertx);
         * router.route().handler(BodyHandler.create());
         * router.post("/transactions").consumes("application/json").handler(
         * handler); server.requestHandler(router::accept).listen(8080);
         */
    }

    @PostConstruct
    public void deployVerticle() {
        Vertx vertx = ctx.getBean(Vertx.class);
        Future<String> httpServerVerticleDeployment = Future.future();
        vertx.deployVerticle(httpServerVerticle, httpServerVerticleDeployment.completer());
        vertx.deployVerticle(transactionVerticle);

    }
}
