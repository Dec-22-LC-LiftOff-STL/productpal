package org.launchcode.productpal.persistent.models.data;

import org.launchcode.productpal.persistent.models.Thresholds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdsRepository extends JpaRepository<Thresholds, Integer> {
}