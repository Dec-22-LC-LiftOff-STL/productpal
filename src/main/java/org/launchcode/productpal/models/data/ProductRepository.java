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
    public ArrayList<Category> findByName (String name);
}
