package models;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    public double amount;

    public Date time;

    public Transaction(double amount, String timestamp) {
        this.amount = amount;
        this.time = new Date(Long.valueOf(timestamp) / 1000L);
    }
}
