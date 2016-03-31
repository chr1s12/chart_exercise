package de.guerz.dao;

import de.guerz.domain.Chart;

import java.util.List;

public interface ChartDAOService {
    List<Chart> loadAllCharts();

    Chart saveChart(Chart chart);
}
