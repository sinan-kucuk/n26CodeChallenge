package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Transaction;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class TransactionController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        JsonNode json = request().body().asJson();
        Transaction transaction = Json.fromJson(json, Transaction.class);

        if(transaction.isValid()) {
            return created();
        } else {
            return noContent();
        }
    }

}