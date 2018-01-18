package com.mlc.statistic.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class TransactionVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        /*
         * HttpServer server = vertx.createHttpServer();
         * 
         * Router router = Router.router(vertx);
         * router.get("/").handler(this::indexHandler);
         * router.get("/wiki/:page").handler(this::pageRenderingHandler);
         * router.post().handler(BodyHandler.create());
         * router.post("/save").handler(this::pageUpdateHandler);
         * router.post("/create").handler(this::pageCreateHandler);
         * router.post("/delete").handler(this::pageDeletionHandler);
         * 
         * int portNumber = config().getInteger(CONFIG_HTTP_SERVER_PORT, 8080);
         * (3) server .requestHandler(router::accept) .listen(portNumber, ar ->
         * { if (ar.succeeded()) { LOGGER.info("HTTP server running on port " +
         * portNumber); startFuture.complete(); } else {
         * LOGGER.error("Could not start a HTTP server", ar.cause());
         * startFuture.fail(ar.cause()); } });
         */
    }

}
