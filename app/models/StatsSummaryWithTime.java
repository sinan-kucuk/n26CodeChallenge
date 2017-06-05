package models;

import java.time.Duration;
import java.time.Instant;

public class StatsSummaryWithTime {

    public StatsSummary statsSummary;

    public Instant time;

    public StatsSummaryWithTime(StatsSummary statsSummary, Instant time) {
        this.statsSummary = statsSummary;
        this.time = time;
    }

    public static StatsSummaryWithTime ofTransaction(Transaction transaction) {
        return new StatsSummaryWithTime(StatsSummary.ofTransaction(transaction), transaction.time);
    }

    public boolean isInSameSecond(Instant time) {
        Duration difference = Duration.between(time, this.time);
        return Math.abs(difference.getSeconds()) <= 1;
    }
}
