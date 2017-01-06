package sapp.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
	
	@Profile("dev")
	@Bean
	public DataSource dataSource(){
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:sql/schema.sql")
				.addScript("classpath:sql/init-data.sql")
				.build();
	}

	@Profile("dev")
	@Bean
	public SessionFactory sessionFactory() {
		DataSource dataSource = dataSource();
	    Properties properties = new Properties();
	    properties.put("hibernate.show_sql", "true");
	    properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	    properties.put("hibernate.connection.url", "jdbc:h2:~/test");
	    properties.put("hibernate.hbm2ddl.auto", "update");
	    properties.put("connection.driver_class", "org.h2.Driver");
	    //properties.put("connection.username", "sa");
	    //properties.put("connection.password.", "");
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);	
	    sessionBuilder.addProperties(properties);
	    sessionBuilder.scanPackages("sapp.model");
	    return sessionBuilder.buildSessionFactory();
	}
	
	@Bean
	public HibernateTransactionManager getTransactionManager() {
		SessionFactory sessionFactory = sessionFactory();
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
	
	@Bean
    public UserDetailsService userDetailsService(){
    	JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
    	jdbcImpl.setDataSource(dataSource());
    	jdbcImpl.setUsersByUsernameQuery("select username, password, enabled from users where lower(username)=lower(?)");
    	jdbcImpl.setAuthoritiesByUsernameQuery("select b.username, a.role from user_roles a, users b where lower(b.username)=lower(?) and a.userid=b.userid");
    	return jdbcImpl;
    }
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource());
		return db;
	}
}
