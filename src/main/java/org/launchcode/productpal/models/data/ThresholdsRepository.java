package org.launchcode.productpal.models.data;

import org.launchcode.productpal.models.Thresholds;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThresholdsRepository extends CrudRepository<Thresholds, Integer> {
}