package org.java.training.hibernate.config;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import org.hibernate.SessionFactory;
import org.java.training.hibernate.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.vocalink.training.hibernate")
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    @Autowired
    public UserDao userDao(SessionFactory sessionFactory) throws ClassNotFoundException {
        return new UserDao(sessionFactory);
    }
    @Bean
    public LocalSessionFactoryBean sessionFactory() throws SQLException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourcePayPort());
        sessionFactory.setPackagesToScan("com.vocalink.training.hibernate");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean
    public DataSource dataSourcePayPort() throws SQLException {
        OracleConnectionPoolDataSource driverManagerDataSource = new OracleConnectionPoolDataSource();
        driverManagerDataSource.setURL("jdbc:oracle:thin:@10.105.10.41:1521/paypdsdv.test.vocalink.co.uk");
        driverManagerDataSource.setUser("rat_paypds");
        driverManagerDataSource.setPassword("rat_paypds123-");
        return driverManagerDataSource;
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "true");
                setProperty("hibernate.show_sql", "true");
                setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
            }
        };
    }
}
