package com.team.managing.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.util.Properties;

import static com.team.managing.constant.ConstantClass.HIBERNATE_DIALECT;
import static com.team.managing.constant.ConstantClass.PACKAGE_TO_SCAN;
import static com.team.managing.constant.ConstantClass.PROPERTY_SOURCE;
import static com.team.managing.constant.ConstantClass.RESOURCE_HANDLERS_CLASSPATH;
import static com.team.managing.constant.ConstantClass.RESOURCE_HANDLERS_ROOT;
import static com.team.managing.constant.ConstantClass.VIEW_RESOLVER_PREFIX;
import static com.team.managing.constant.ConstantClass.VIEW_RESOLVER_SUFFIX;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(PACKAGE_TO_SCAN)
@PropertySource(PROPERTY_SOURCE)
public class SpringConfig implements WebMvcConfigurer {

    @Value("${org.hibernate.dialect.H2Dialect}")
    private String hibernateDialectH2;

    @Value("${hibernate.driver.class.name}")
    private String driverClassName;

    @Value("${hibernate.connection.url}")
    private String databaseConnectionUrl;

    @Value("${hibernate.connection.username}")
    private String databaseConnectionUsername;

    @Value("${hibernate.connection.password}")
    private String databaseConnectionPassword;

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseConnectionUrl);
        dataSource.setUsername(databaseConnectionUsername);
        dataSource.setPassword(databaseConnectionPassword);

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(PACKAGE_TO_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());

        return hibernateTransactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(HIBERNATE_DIALECT, hibernateDialectH2);
        return hibernateProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        int passwordStrength = 12;
        return new BCryptPasswordEncoder(passwordStrength);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCE_HANDLERS_ROOT)
                .addResourceLocations(RESOURCE_HANDLERS_CLASSPATH);
    }

    @Bean
    public RestOperations getRestTemplate() {
        return new RestTemplate();
    }
}