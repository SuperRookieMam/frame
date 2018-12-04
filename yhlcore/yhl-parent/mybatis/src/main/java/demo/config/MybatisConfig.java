package demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer {

    /**
     * 获取mysql数据源
     * */
    @Primary
    @Bean("mybatisDatasource")
    @ConfigurationProperties("datasource.mysql")
    public DataSource getMysqlDatasource(){
        return DataSourceBuilder.create().build();
    }
    @Bean("sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("mybatisDatasource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver solver=new PathMatchingResourcePatternResolver();
        Resource[] resources =solver.getResources("classpath*:mapping/**/*.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();

    }

    @Bean("sqlSessionTemplate ")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 实现接口 TransactionManagementConfigurer 方法，
     * 其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
     * **/
    @Override

    public PlatformTransactionManager annotationDrivenTransactionManager() {

        return  new DataSourceTransactionManager(getMysqlDatasource());
    }
}
