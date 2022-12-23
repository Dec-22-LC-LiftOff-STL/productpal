package org.launchcode.techjobs.persistent.models.data;

import org.launchcode.techjobs.persistent.models.Thresholds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdsRepository extends JpaRepository<Thresholds, Integer> {
}