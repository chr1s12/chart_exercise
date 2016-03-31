package de.guerz.dao;


import de.guerz.domain.Chart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChartRepository extends JpaRepository<Chart, Integer> {
}
