package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class TransactionController extends Controller {

    public Result add() {
        return created();
    }

}