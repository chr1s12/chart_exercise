package de.guerz.dao;

import de.guerz.domain.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ChartDAOServiceImpl implements ChartDAOService {

    @Autowired
    private ChartDAO chartDAO;

    @Override
    public List<Chart> loadAllCharts() {
        return chartDAO.loadAllCharts();
    }

    @Override
    @Transactional(readOnly = false)
    public Integer saveChart(Chart chart) {
        return chartDAO.saveChart(chart);
    }

    public void setChartDAO(ChartDAO chartDAO) {
        this.chartDAO = chartDAO;
    }
}
