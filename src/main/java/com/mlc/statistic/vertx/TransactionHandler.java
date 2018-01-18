package com.mlc.statistic.vertx;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@Component
public class TransactionHandler implements Handler<RoutingContext> {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void handle(RoutingContext rc) {
        Statistic stat = new Gson().fromJson(rc.getBodyAsString(), Statistic.class);

        HttpServerResponse response = rc.response();
        response.putHeader("content-type", "application/json");

        if (Instant.ofEpochMilli(stat.getTimestamp()).isBefore(Instant.now().minus(60, ChronoUnit.SECONDS))) {
            response.setStatusCode(HttpResponseStatus.NO_CONTENT.code());
            response.end();
            return;
        }

        /*
         * Map<String, Object> message = new HashMap<>(); message.put("amount",
         * stat.getAmount()); message.put("timestamp", stat.getTimestamp());
         */
        kafkaTemplate.send("transactions", new Gson().toJson(stat));

        response.setStatusCode(HttpResponseStatus.CREATED.code());
        response.end();
    }

}
