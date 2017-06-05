package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Transaction;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.TransactionService;

import javax.inject.Inject;

public class TransactionController extends Controller {

    private TransactionService service;

    @Inject
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        JsonNode json = request().body().asJson();
        Transaction transaction = Json.fromJson(json, Transaction.class);

        if(transaction.isValid()) {
            service.add(transaction);
            return created();
        } else {
            return noContent();
        }
    }

}