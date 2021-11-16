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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConfigurationProperties(prefix="spring.datasource.hikari")
@MapperScan(
        basePackages = "com.assignment.board.database.mybatis.dao",
        sqlSessionFactoryRef = "SqlSessionFactory")
public class DatabaseConfig extends HikariConfig {

    /**
     * FUNCTION :: DataBase Connection Pool Config  ( 데이터 베이스 연결 )
     * @return
     */
    @Bean(name="hikariDataSource")
    @Primary
    public HikariDataSource hikariDataSource(){ return new HikariDataSource(this); }

    /**
     * FUNCTION :: DataBase connect to Mybatis ( DAO )
     * @param hikariDataSource
     * @return
     * @throws Exception
     */
    @Bean(name="SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDataSource") HikariDataSource hikariDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        setConfigureSqlSessionFactory(sqlSessionFactoryBean, hikariDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * FUNCTION :: Set Resource Path ( Mapper.xml, DTO ) & Alias set CamelCase
     * @param sqlSessionFactoryBean
     * @param hikariDataSource
     * @throws Exception
     */
    protected void setConfigureSqlSessionFactory(SqlSessionFactoryBean sqlSessionFactoryBean, HikariDataSource hikariDataSource) throws Exception {
        sqlSessionFactoryBean.setDataSource(hikariDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.assignment.board.database.mybatis.dto");
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
    }

    /**
     * FUNCTION :: Set SqlSession ( DataSource Life Cycle Management )
     * @param sqlSessionFactory
     * @return
     */
    @Bean("SqlSession")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * FUNCTION :: Set TransactionManager ( Commit, RollBack )
     * @param hikariDataSource
     * @return
     */
    @Bean(name="TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("hikariDataSource") HikariDataSource hikariDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(hikariDataSource);
        dataSourceTransactionManager.setNestedTransactionAllowed(true);
        return dataSourceTransactionManager;
    }
}