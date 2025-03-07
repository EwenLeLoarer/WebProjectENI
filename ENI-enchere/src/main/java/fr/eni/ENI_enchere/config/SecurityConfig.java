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
                    // Authorize the access to the register page only for ADMIN role
                    //auth.requestMatchers(HttpMethod.GET, "/users/register").hasRole("ADMIN");
                    //auth.requestMatchers(HttpMethod.POST, "/users/register").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/profil/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/profil/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST,"/enchere/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/nouvelle-vente").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/nouvelle-vente").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/").permitAll()
                    .requestMatchers(request -> request.getParameter("lang") != null).permitAll() // Allow ?lang=xx parameter
                    .requestMatchers(request -> request.getParameter("invalid") != null).permitAll(); // Allow ?invalid&continue parameter
                    auth.requestMatchers(HttpMethod.GET, "/register").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/enchere/*").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
                    // Permit access to the homepage and other public pages
                    auth.requestMatchers("/*").denyAll();

                    // Allow access to static resources (CSS, Images) for all users
                    auth.requestMatchers("/css/*").permitAll();
                    auth.requestMatchers("/image/*").permitAll();
                    auth.requestMatchers("/js/*").permitAll();
                    auth.requestMatchers("/assets/image/*").permitAll();
                    
                    // Refuse all other URLs
                    auth.anyRequest().authenticated();  
                });

        // Customize the login page
        http.formLogin(form -> {
            form.loginPage("/login").permitAll();  // Set custom login page
            form.defaultSuccessUrl("/");  // Redirect to home page after login
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
        
        http.sessionManagement(
	  			session -> session
	  			    .sessionFixation().migrateSession()
	  			    .invalidSessionUrl("/login?invalid")
	  			    .maximumSessions(1) // une  session active
	  			    .expiredUrl("/login?expired")
	  			);
 

        return http.build();
    }
	


}
