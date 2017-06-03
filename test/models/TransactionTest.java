package models;

import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TransactionTest {

    @Test
    public void test_transactionOlderThan60SecondsAreNotValid() {
        Instant seventySecondsAgo = Instant.now().minusSeconds(700);
        Transaction oldTransaction = new Transaction(14.6, String.valueOf(seventySecondsAgo.toEpochMilli()));

        assertThat(oldTransaction.isValid(), is(false));
    }

    @Test
    public void test_transactionNewerThan60SecondsAreNotValid() {
        Instant twentySecondsAgo = Instant.now().minusSeconds(20);
        Transaction freshTransaction = new Transaction(14.6, String.valueOf(twentySecondsAgo.toEpochMilli()));

        assertThat(freshTransaction.isValid(), is(true));
    }

}