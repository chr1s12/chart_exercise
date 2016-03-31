package de.guerz.bean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConext {

  @Bean(name = "hibernateTemplate")
    public HibernateTemplate hibernateTemplate() throws PropertyVetoException {
      return new HibernateTemplate(sessionFactory().getObject());
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(dataSource());
        //lsfb.setAnnotatedClasses(new Class<?>[] {Chart.class, ChartData.class});
        lsfb.setPackagesToScan(new String[]{"de.guerz.domain"});
        lsfb.setHibernateProperties(hibernateProperties());
        return lsfb;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        // props.setProperty("hibernate.hbm2ddl.auto", "create");
        props.setProperty("hibernate.jdbc.batch_size", "0");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.cache.use_second_level_cache", "false");
        props.setProperty("hibernate.cache.use_query_cache", "false");
        props.setProperty("hibernate.cache.provider_class", "net.sf.ehcache.hibernate.SingletonEhCacheProvider");
        return props;
    }

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("org.postgresql.Driver");
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/chart_exercise");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");
        return cpds;
    }

    @Bean
    public PlatformTransactionManager txManager() throws PropertyVetoException {
        return new HibernateTransactionManager(sessionFactory()
                .getObject());
    }

}
