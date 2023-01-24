package org.launchcode.productpal.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javassist.runtime.Desc;
import org.launchcode.productpal.models.Category;

@Entity
public class Product extends AbstractEntity{

    @NotNull
    @Size(min=3, max=50)
    private String name;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Description description;
    private Integer amount;
//    @OneToOne
//    private Thresholds thresholds;

    private int lowThreshold;

    private int highThreshold;

    private boolean reorderNeeded;

    private boolean saleNeeded;

    public Product() {
    }

    // Initialize the id and value fields.
    public Product(String aName, Category aCategory, Description aDescription, Integer anAmount) {
        super();
        this.name = aName;
        this.category = aCategory;
        this.description = aDescription;
        this.amount = anAmount;
    }

    // Getters and setters.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public boolean isReorderNeeded() {
        return reorderNeeded;
    }

    public void setReorderNeeded(boolean reorderNeeded) {
        this.reorderNeeded = (this.getAmount() < this.lowThreshold);
    }

    public boolean isSaleNeeded() {
        return saleNeeded;
    }

    public void setSaleNeeded(boolean saleNeeded) {
        this.saleNeeded = (this.getAmount() > this.highThreshold);
    }

    @Override
    public String toString() {
        return name;
    }
}