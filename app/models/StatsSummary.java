package models;

import java.io.Serializable;

public class StatsSummary implements Serializable {

    public double sum;

    public double avg;

    public double max;

    public double min;

    public double count;

    public StatsSummary() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatsSummary summary = (StatsSummary) o;

        if (Double.compare(summary.sum, sum) != 0) return false;
        if (Double.compare(summary.avg, avg) != 0) return false;
        if (Double.compare(summary.max, max) != 0) return false;
        if (Double.compare(summary.min, min) != 0) return false;
        return Double.compare(summary.count, count) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(sum);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(avg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(min);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(count);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public StatsSummary(double sum, double avg, double max, double min, double count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public void addStatsOf(Transaction transaction) {
        this.sum += transaction.amount;
        this.count++;
        this.avg = this.sum / count;
        this.max = Math.max(this.max, transaction.amount);
        this.min = this.min != 0 ? Math.min(this.min, transaction.amount) : transaction.amount;
    }

    public void merge(StatsSummaryWithTime summaryWithTime) {
        StatsSummary summary = summaryWithTime.statsSummary;
        this.sum += summary.sum;
        this.count+= summary.count;
        this.avg = this.sum / count;
        this.max = Math.max(this.max, summary.max);
        this.min = this.min != 0 ? Math.min(this.min, summary.min) : summary.min;
    }

    public static StatsSummary ofTransaction(Transaction transaction) {
        return new StatsSummary(transaction.amount, transaction.amount, transaction.amount, transaction.amount, 1);
    }

}
