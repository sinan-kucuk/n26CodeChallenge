package services;

import models.StatsSummary;
import models.StatsSummaryWithTime;
import models.Transaction;

import javax.inject.Singleton;
import java.time.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class TransactionService {

    ConcurrentHashMap<Integer, StatsSummaryWithTime> transactionStats = new ConcurrentHashMap<Integer, StatsSummaryWithTime>();

    public void add(Transaction transaction) {
        LocalDateTime transactionTime = LocalDateTime.ofInstant(transaction.time, ZoneOffset.UTC);
        int secondsInMinute = transactionTime.getSecond();

        if(summaryExistsWithSameTime(secondsInMinute, transaction)) {
            StatsSummaryWithTime currentSummary = transactionStats.get(secondsInMinute);
            currentSummary.statsSummary.addStatsOf(transaction);
        } else {
            StatsSummaryWithTime summary = StatsSummaryWithTime.ofTransaction(transaction);
            transactionStats.put(secondsInMinute, summary);
        }
    }

    private boolean summaryExistsWithSameTime(int secondsInMinute, Transaction transaction) {
        if(transactionStats.containsKey(secondsInMinute)) {
            StatsSummaryWithTime currentSummary = transactionStats.get(secondsInMinute);
            return currentSummary.isInSameSecond(transaction.time);
        } else
            return false;
    }

    public StatsSummary summarizeStats() {
        StatsSummary finalSummary = new StatsSummary();
        transactionStats.forEach((time, summary) -> finalSummary.merge(summary));

        return finalSummary;
    }
}
