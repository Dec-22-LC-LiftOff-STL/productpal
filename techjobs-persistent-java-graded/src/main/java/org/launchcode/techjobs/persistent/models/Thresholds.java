package org.launchcode.techjobs.persistent.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Thresholds {

    @GeneratedValue
    @Id
    private int id;

    //This is the placeholder for the product
    @NotNull
    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;
    private int lowThreshold;

    private int highThreshold;

    private boolean reorderNeeded;

    private boolean saleNeeded;

    public Thresholds() {
    }

    public Thresholds(Job job, int lowThreshold, int highThreshold) {
        this();
        this.job = job;
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
    }

    public int getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(int lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public int getHighThreshold() {
        return highThreshold;
    }

    public void setHighThreshold(int highThreshold) {
        this.highThreshold = highThreshold;
    }

    public int getId() {
        return id;
    }

    public boolean isReorderNeeded() {
        return reorderNeeded;
    }

    public void setReorderNeeded(boolean reorderNeeded) {
        this.reorderNeeded = (this.job.getAmount() < this.lowThreshold);
    }

    public boolean isSaleNeeded() {
        return saleNeeded;
    }

    public void setSaleNeeded(boolean saleNeeded) {
        this.saleNeeded = (this.job.getAmount() > this.highThreshold);
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}
