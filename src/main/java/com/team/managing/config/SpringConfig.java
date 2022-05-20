package com.team.managing.config;

import com.team.managing.captcha.CaptchaService;
import com.team.managing.captcha.ICaptchaService;
import com.team.managing.dao.HibernateUserDao;
import com.team.managing.service.UserDetailService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

import static com.team.managing.constant.ConstantClass.ANNOTATION_SPRING_PROPERTY_SOURCE;
import static com.team.managing.constant.ConstantClass.DATABASE_CONNECTION_DRIVER;
import static com.team.managing.constant.ConstantClass.DATABASE_CONNECTION_PASSWORD;
import static com.team.managing.constant.ConstantClass.DATABASE_CONNECTION_URL;
import static com.team.managing.constant.ConstantClass.DATABASE_CONNECTION_USERNAME;
import static com.team.managing.constant.ConstantClass.HIBERNATE_DIALECT;
import static com.team.managing.constant.ConstantClass.HIBERNATE_DIALECT_H2;
import static com.team.managing.constant.ConstantClass.PACKAGE_TO_SCAN;
import static com.team.managing.constant.ConstantClass.RESOURCE_HANDLERS_CLASSPATH;
import static com.team.managing.constant.ConstantClass.RESOURCE_HANDLERS_ROOT;
import static com.team.managing.constant.ConstantClass.VIEW_RESOLVER_PREFIX;
import static com.team.managing.constant.ConstantClass.VIEW_RESOLVER_SUFFIX;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(PACKAGE_TO_SCAN)
@PropertySource(ANNOTATION_SPRING_PROPERTY_SOURCE)
public class SpringConfig implements WebMvcConfigurer {

    private final Environment environment;

    public SpringConfig(Environment environment) {
        this.environment = environment;
    }

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

        dataSource.setDriverClassName(environment.getProperty(DATABASE_CONNECTION_DRIVER));
        dataSource.setUrl(environment.getProperty(DATABASE_CONNECTION_URL));
        dataSource.setUsername(environment.getProperty(DATABASE_CONNECTION_USERNAME));
        dataSource.setPassword(environment.getProperty(DATABASE_CONNECTION_PASSWORD));

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
        hibernateProperties.setProperty(HIBERNATE_DIALECT, HIBERNATE_DIALECT_H2);
        return hibernateProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        int passwordStrength = 12;
        return new BCryptPasswordEncoder(passwordStrength);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(new UserDetailService(hibernateUserDao()));

        return daoAuthenticationProvider;
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

    @Bean
    public HibernateUserDao hibernateUserDao() {
        return new HibernateUserDao(sessionFactory().getObject());
    }

    @Bean
    public ICaptchaService getCaptcha() {
        return new CaptchaService();
    }
}