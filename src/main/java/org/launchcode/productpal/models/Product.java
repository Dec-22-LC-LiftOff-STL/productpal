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
    @OneToOne
    private Thresholds thresholds;

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

    public Thresholds getThresholds() {
        return thresholds;
    }

    public void setThresholds(Thresholds thresholds) {
        this.thresholds = thresholds;
    }

    @Override
    public String toString() {
        return name;
    }
}