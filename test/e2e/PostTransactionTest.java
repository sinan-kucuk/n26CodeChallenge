package e2e;

import models.Transaction;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;


import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.CREATED;
import static play.mvc.Http.Status.NO_CONTENT;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;

public class PostTransactionTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void test_whenTransactionIsReceived_thenRespondsWithHTTP201() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(new Transaction(12.3, "1478192204000")))
                .uri("/transactions");

        Result result = route(app, request);
        assertEquals(CREATED, result.status());
    }

    @Test
    public void test_whenReceivedTransactionIsOlderThan60Secs_thenRespondsWithHTTP204() {
        Instant seventySecondsAgo = Instant.now().minusSeconds(70);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(new Transaction(12.3, String.valueOf(seventySecondsAgo.toEpochMilli()))))
                .uri("/transactions");

        Result result = route(app, request);
        assertEquals(NO_CONTENT, result.status());
    }

}
