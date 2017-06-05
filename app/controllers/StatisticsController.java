package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.StatsSummary;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.TransactionService;

import javax.inject.Inject;


public class StatisticsController extends Controller {

    private TransactionService service;

    @Inject
    public StatisticsController(TransactionService service) {
        this.service = service;
    }

    public Result summarize() {
        StatsSummary summary = service.summarizeStats();
        JsonNode response = Json.toJson(summary);
        return ok(response);
    }

}