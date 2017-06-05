package models;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class Transaction implements Serializable {

    public double amount;

    public Instant time;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(Instant timestamp) {
        this.time = timestamp;
    }

    public Transaction() {

    }

    public Transaction(double amount, String timestamp) {
        this.amount = amount;
        this.time = Instant.ofEpochMilli(Long.valueOf(timestamp));
    }

    public boolean isValid() {
        Instant thresholdTime = Instant.now().minusSeconds(60);
        return time.isAfter(thresholdTime);
    }
}
