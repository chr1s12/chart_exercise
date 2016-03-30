package de.guerz.dao;

import de.guerz.domain.Chart;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

public class ChartDAO  implements IChartDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Chart> loadAllCharts() {
		return (List<Chart>) new HibernateTemplate(sessionFactory).find("from Chart");   //loadAll(Chart.class);
	}

	// findChartByName(String name);

	@Override
	public Integer saveChart(Chart chart) {
//		hibernateTemplate.getSessionFactory().getCurrentSession().setReadOnly(chart, false);
//		hibernateTemplate.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		//hibernateTemplate.setCheckWriteOperations(false)



		return (Integer) new HibernateTemplate(sessionFactory).save(chart);
	}
}
