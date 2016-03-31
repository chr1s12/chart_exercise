package de.guerz.dao;

import de.guerz.domain.Chart;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

public class ChartDAO {

/*	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Chart> loadAllCharts() {
		return (List<Chart>) new HibernateTemplate(sessionFactory).find("from Chart");
	}

	// findChartByName(String name);

	public Integer saveChart(Chart chart) {
//		hibernateTemplate.getSessionFactory().getCurrentSession().setReadOnly(chart, false);
//		hibernateTemplate.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		//hibernateTemplate.setCheckWriteOperations(false)



		return (Integer) new HibernateTemplate(sessionFactory).save(chart);
	}*/
}
