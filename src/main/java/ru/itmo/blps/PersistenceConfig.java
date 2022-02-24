package ru.itmo.blps;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("ru.itmo.blps.DAO.mappers")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class})
public class PersistenceConfig {
    @Value("${POSTGRES_URL}")
    private String postgresURL;
    @Value("${POSTGRES_USER}")
    private String postgresUser;
    @Value("${POSTGRES_PASSWORD}")
    private String postgresPassword;

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(postgresURL);
        ds.setUser(postgresUser);
        ds.setPassword(postgresPassword);
        ds.setSslMode("disable");
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }

}