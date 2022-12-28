package org.launchcode.productpal.models.data;

import org.launchcode.productpal.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
