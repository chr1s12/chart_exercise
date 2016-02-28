package de.guerz.dao;

import java.util.List;

import de.guerz.domain.Chart;

public interface IChartDAO {

	List<Chart> loadAllCharts();

	Integer saveChart(Chart chart);
}
