package de.guerz.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.guerz.domain.Chart;

public class ChartDAO extends HibernateDaoSupport implements IChartDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Chart> loadAllCharts() {
		return (List<Chart>) getHibernateTemplate().find("from Chart");   //loadAll(Chart.class);
	}

	@Override
	public Integer saveChart(Chart chart) {
		return (Integer) getHibernateTemplate().save(chart);
	}
}
