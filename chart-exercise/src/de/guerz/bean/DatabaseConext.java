package de.guerz.bean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import de.guerz.dao.ChartRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.beans.PropertyVetoException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = ChartRepository.class)
public class DatabaseConext {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(ComboPooledDataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan(new String[]{"de.guerz.domain"});

        return emfb;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        return adapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory((entityManagerFactory(dataSource(), jpaVendorAdapter()).getObject()));
        return txManager;
    }

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setDriverClass("org.postgresql.Driver");
        source.setJdbcUrl("jdbc:postgresql://localhost:5432/chart_exercise");
        source.setUser("postgres");
        source.setPassword("postgres");
        return source;
    }

/*
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
    }*/

}
