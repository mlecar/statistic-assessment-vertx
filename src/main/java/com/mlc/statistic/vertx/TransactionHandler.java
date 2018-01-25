package com.mlc.statistic.vertx;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.google.gson.Gson;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@Component
public class TransactionHandler {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private Gson gson;

    public void add(RoutingContext rc) {
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

        // Async
        final ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("transactions", gson.toJson(stat));
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(producerRecord);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("success");
            }

        });

        // Sync
        // kafkaTemplate.send("transactions", stat);

        response.setStatusCode(HttpResponseStatus.CREATED.code());
        response.end();
    }

}
