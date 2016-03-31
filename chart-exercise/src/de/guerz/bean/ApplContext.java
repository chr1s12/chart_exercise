package de.guerz.bean;

import de.guerz.dao.ChartDAO;
import de.guerz.dao.ChartDAOService;
import de.guerz.dao.ChartDAOServiceImpl;
import de.guerz.utils.ReadChartFile;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Arrays;

@Configuration
@Import(DatabaseConext.class)
@ComponentScan(basePackageClasses = ChartDAO.class)
public class ApplContext {

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public ReadChartFile readChartFile() {
        return new ReadChartFile();
    }

    @Bean
    public ChartDAO chartDAO() {
        return new ChartDAO();
    }

    @Bean
    public ChartDAOService chartDAOService() {
        return new ChartDAOServiceImpl();
    }



}
