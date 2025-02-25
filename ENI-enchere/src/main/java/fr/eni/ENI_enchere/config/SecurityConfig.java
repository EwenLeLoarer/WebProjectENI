package fr.eni.ENI_enchere.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		
		manager.setUsersByUsernameQuery("select pseudo, password, "
								+ "active from utilisateur where pseudo = ?");
		
		manager.setUsersByUsernameQuery("select email, password, "
				+ "active from utilisateur where email = ?");

		manager.setAuthoritiesByUsernameQuery(("select pseudo, email, [role] from [role] "
											+ "where pseudo=?"));
		
		manager.setAuthoritiesByUsernameQuery(("select email, [role] from [role] "
				+ "where email=?"));
		return manager;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> {
					//authoriser l'accès à la liste uniquement au employé
					auth.requestMatchers(HttpMethod.GET, "/security").hasRole("EMPLOYE");

					//authoriser l'accès à la page de création uniquement au admin
					auth.requestMatchers(HttpMethod.GET, "/security/create").hasRole("ADMIN");
					
					//authoriser l'accès à l'envoie de la requête post de création uniquement au admin
					auth.requestMatchers(HttpMethod.POST, "/security/create").hasRole("ADMIN");

					//authorise l'accès à la page d'accueil du site à tout le monde
					auth.requestMatchers("/*").permitAll();

					//accès au ressource pour tous
					auth.requestMatchers("/css/*").permitAll();
					auth.requestMatchers("/image/*").permitAll();
					
					//refuse toutes autre url
					auth.anyRequest().denyAll();	
				});
		
		//customisation de ma page login
		http.formLogin(form -> {
					//définir la nouvelle page de login
					form.loginPage("/login").permitAll();
					//définir la page sur laquelle on arrive après la connexion
					form.defaultSuccessUrl("/");
				});
		
		http.logout( logout -> {
			//supprimer la session du côté serveur
			logout.invalidateHttpSession(true);
			logout.clearAuthentication(true);
			//destuction du cookie
			logout.deleteCookies("JSESSIONID");
			//création de l'appel au logout
			logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
			//page afficher après la déconnexion
			logout.logoutSuccessUrl("/");
			logout.permitAll();
		});
		
		return http.build();
		
	}
	


}
