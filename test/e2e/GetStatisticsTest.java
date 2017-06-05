package e2e;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.StatsSummary;
import models.Transaction;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class GetStatisticsTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void test_givenASavedTransaction_whenStatisticsRequestReceived_thenStatsSummaryIsRespondedWithSingleTransactionsInfo() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(new Transaction(15, String.valueOf(Instant.now().toEpochMilli()))))
                .uri("/transactions");
        route(app, request);

        Http.RequestBuilder statsRequest = new Http.RequestBuilder()
                .method(GET)
                .uri("/statistics");
        Result result = route(app, statsRequest);
        final String responseBody = contentAsString(result);

        StatsSummary expectedSummary = new StatsSummary(15, 15, 15, 15, 1);
        JsonNode expectedResponse = Json.toJson(expectedSummary);
        assertEquals(expectedResponse.toString(), responseBody);
    }

    @Test
    public void test_givenTwoSavedTransactions_whenStatisticsRequestReceived_thenStatsSummaryIsRespondedWithSummaryOfTransactionsInfo() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(new Transaction(15, String.valueOf(Instant.now().toEpochMilli()))))
                .uri("/transactions");
        route(app, request);

        Http.RequestBuilder request2 = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(new Transaction(35, String.valueOf(Instant.now().toEpochMilli()))))
                .uri("/transactions");
        route(app, request2);

        Http.RequestBuilder statsRequest = new Http.RequestBuilder()
                .method(GET)
                .uri("/statistics");
        Result result = route(app, statsRequest);
        final String responseBody = contentAsString(result);

        StatsSummary expectedSummary = new StatsSummary(50, 25, 35, 15, 2);
        JsonNode expectedResponse = Json.toJson(expectedSummary);
        assertEquals(expectedResponse.toString(), responseBody);
    }

}
