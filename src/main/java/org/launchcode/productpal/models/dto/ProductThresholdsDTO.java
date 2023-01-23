package org.launchcode.productpal.models.dto;

import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.Thresholds;


import javax.validation.constraints.NotNull;

public class ProductThresholdsDTO {

    @NotNull
    private Product product;

    private Thresholds thresholds;

//    public ProductThresholdsDTO() {
//    }

    public ProductThresholdsDTO() {
        this.product = new Product();
        this.thresholds = new Thresholds();
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Thresholds getThresholds() {
        return thresholds;
    }

    public void setThresholds(Thresholds thresholds) {
        this.thresholds = thresholds;
    }
}