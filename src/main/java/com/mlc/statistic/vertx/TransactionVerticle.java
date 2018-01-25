package com.mlc.statistic.vertx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

@Component
public class TransactionVerticle extends AbstractVerticle {

    @Autowired
    private Router router;

    @Autowired
    private TransactionHandler transactionHandler;

    @Override
    public void start() throws Exception {
        router.post("/transactions").consumes("application/json").handler(transactionHandler::add);

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
