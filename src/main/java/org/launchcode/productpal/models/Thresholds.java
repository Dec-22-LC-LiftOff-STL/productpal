package org.launchcode.productpal.models;

import org.launchcode.productpal.models.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Thresholds {

    @GeneratedValue
    @Id
    private int id;

    //This is the placeholder for the product
    @NotNull(message = "Product must not be null!")
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int lowThreshold;

    private int highThreshold;

    private boolean reorderNeeded;

    private boolean saleNeeded;

    public Thresholds() {
    }

    public Thresholds(Product product, int lowThreshold, int highThreshold) {
        this();
        this.product = product;
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
        this.reorderNeeded = (this.product.getAmount() < this.lowThreshold);
    }

    public boolean isSaleNeeded() {
        return saleNeeded;
    }

    public void setSaleNeeded(boolean saleNeeded) {
        this.saleNeeded = (this.product.getAmount() > this.highThreshold);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}