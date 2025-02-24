package fr.eni.ENI_enchere.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;


@Configuration
public class SecurityConfig {
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		
		manager.setUsersByUsernameQuery("select pseudo, password, "
								+ "active from utilisateur where pseudo = ? ");
		
		manager.setAuthoritiesByUsernameQuery(("select pseudo, [role] from [role] "
											+ "where pseudo=?"));
		
		return manager;
	}
	


}
