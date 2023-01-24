package org.launchcode.productpal.models.data;

import org.launchcode.productpal.models.Category;
import org.launchcode.productpal.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Iterable<Product> findAll();
    public Product findFirstByName (String name);
    Iterable<Category> findByCategoryName(String name);
}
