package de.guerz.dao;

import de.guerz.domain.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class ChartDAOServiceImpl implements ChartDAOService {

    @Autowired
    private ChartRepository chartRepository;

    @Override
    public List<Chart> loadAllCharts() {
        return new ArrayList<>(chartRepository.findAll());
    }

    @Override
    @Transactional(readOnly = false)
    public Chart saveChart(Chart chart) {
        return chartRepository.save(chart);
    }
}
