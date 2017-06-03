package models;

import java.io.Serializable;
import java.time.Instant;

public class Transaction implements Serializable {

    public double amount;

    public Instant time;

    public Transaction(double amount, String timestamp) {
        this.amount = amount;
        this.time = Instant.ofEpochMilli(Long.valueOf(timestamp));
    }

    public boolean isValid() {
        Instant thresholdTime = Instant.now().minusSeconds(60);
        return this.time.isAfter(thresholdTime);
    }
}
