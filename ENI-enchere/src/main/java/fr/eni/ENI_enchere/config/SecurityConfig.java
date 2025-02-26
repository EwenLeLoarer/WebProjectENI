package fr.eni.ENI_enchere.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
	@Bean 
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
		
		manager.setUsersByUsernameQuery("select pseudo, mot_de_passe, "
								+ "active from utilisateurs where pseudo = ?");

		manager.setAuthoritiesByUsernameQuery("SELECT u.pseudo, r.ROLE " +
		        "FROM UTILISATEURS u " +
		        "JOIN ROLES r ON (u.administrateur = r.IS_ADMIN) " +
		        "WHERE u.pseudo = ?");
		
		return manager;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> {
					//authoriser l'accès à la liste uniquement au employé
					//auth.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.GET, "/users/register").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.POST, "/users/register").hasRole("ADMIN");
					//authorise l'accès à la page d'accueil du site à tout le monde
					auth.requestMatchers("/*").permitAll();

					//accès au ressource pour tous
					auth.requestMatchers("/css/*").permitAll();
					auth.requestMatchers("/image/*").permitAll();
					
					//refuse toutes autre url
					auth.anyRequest().permitAll();	
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
