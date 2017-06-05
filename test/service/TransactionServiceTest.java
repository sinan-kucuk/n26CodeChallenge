package service;

import models.StatsSummary;
import models.Transaction;
import org.junit.Test;
import services.TransactionService;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class TransactionServiceTest {

    @Test
    public void test_addingTransactionReplacesOldTransactionWithTheSameSecondsInMinute() {


        Transaction transaction120SecondsAgo = new Transaction(14.9, String.valueOf(Instant.now().minusSeconds(120).toEpochMilli()));
        Transaction transactionNow = new Transaction(15, String.valueOf(Instant.now().toEpochMilli()));
        TransactionService service = new TransactionService();
        service.add(transaction120SecondsAgo);
        service.add(transactionNow);

        StatsSummary summary = service.summarizeStats();
        StatsSummary expected = new StatsSummary(15, 15, 15, 15, 1);

        assertEquals(summary, expected);
    }

    @Test
    public void test_summarizingTransactionSummarizesAllSavedRecentTransactions() {

        Transaction transaction10SecondsAgo = new Transaction(14.9, String.valueOf(Instant.now().minusSeconds(10).toEpochMilli()));
        Transaction transaction25SecondsAgo = new Transaction(35, String.valueOf(Instant.now().minusSeconds(25).toEpochMilli()));
        Transaction transactionNow = new Transaction(17, String.valueOf(Instant.now().toEpochMilli()));

        TransactionService service = new TransactionService();
        service.add(transaction10SecondsAgo);
        service.add(transaction25SecondsAgo);
        service.add(transactionNow);

        StatsSummary summary = service.summarizeStats();
        StatsSummary expected = new StatsSummary(66.9, 22.3, 35, 14.9, 3);

        assertEquals(summary, expected);
    }

}
