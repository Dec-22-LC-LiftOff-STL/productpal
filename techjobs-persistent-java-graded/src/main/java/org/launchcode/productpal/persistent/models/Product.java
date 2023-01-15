package org.launchcode.productpal.persistent.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends AbstractEntity{

@ManyToOne(cascade= CascadeType.ALL)
private Category category;
    @ManyToMany
    private List<Skill> skills= new ArrayList<>();

    private int amount;

    @OneToOne
    private Thresholds thresholds;

    public Product() {
    }

    public Product(Category aCategory, List<Skill> someSkills) {
        super();
        this.category = aCategory;
        this.skills = someSkills;
    }

    // Getters and setters.

   public Category getCategory(){
        return category;
   }
    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public Thresholds getThresholds() {
        return thresholds;
    }

    public void setThresholds(Thresholds thresholds) {
        this.thresholds = thresholds;
    }
}
