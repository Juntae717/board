package com.assignment.board.database.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix="spring.datasource.hikari")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.assignment.board.database",
        entityManagerFactoryRef = "EntityManagerFactory",
        transactionManagerRef = "TransactionManager")
@MapperScan(
        basePackages = "com.assignment.board.database.mybatis.dao",
        sqlSessionFactoryRef = "SqlSessionFactory")
public class DatabaseConfig extends HikariConfig {

    @Bean(name="hikariDataSource")
    @Primary
    public HikariDataSource hikariDataSource(){
        return new HikariDataSource(this);
    }

    @Bean(name="EntityManagerFactory")
    @Primary
    public EntityManagerFactory entityManagerFactory(@Qualifier("hikariDataSource") HikariDataSource hikariDataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(hikariDataSource);
        factory.setPackagesToScan("com.assignment.board.database");
        factory.setPersistenceUnitName("board");
        setConfigureEntityManagerFactory(factory);
        return factory.getObject();
    }

    protected void setConfigureEntityManagerFactory(LocalContainerEntityManagerFactoryBean factory) {
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        Map<String, Object> hibernateOption = new HashMap<String, Object>();
        hibernateOption.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
        hibernateOption.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        hibernateOption.put("hibernate.show_sql", "true");
        hibernateOption.put("hibernate.hbm2ddl.auto", "update");
        hibernateOption.put("hibernate.format_sql", "true");
        hibernateOption.put("hibernate.enable_lazy_load_no_trans", "true");
        factory.setJpaPropertyMap(hibernateOption);
        factory.afterPropertiesSet();
    }

    @Bean(name="SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDataSource") HikariDataSource hikariDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        setConfigureSqlSessionFactory(sqlSessionFactoryBean, hikariDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    protected void setConfigureSqlSessionFactory(SqlSessionFactoryBean sqlSessionFactoryBean, HikariDataSource hikariDataSource) throws Exception {
        sqlSessionFactoryBean.setDataSource(hikariDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.assignment.board.database.mybatis.dto");
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
    }

    @Bean("SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name="TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("hikariDataSource") HikariDataSource hikariDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(hikariDataSource);
        dataSourceTransactionManager.setNestedTransactionAllowed(true);
        return dataSourceTransactionManager;
    }
}