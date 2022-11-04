package br.fatec.financas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.fatec.financas.repository.ClienteRepository;
import br.fatec.financas.security.JWTAuthenticationFilter;
import br.fatec.financas.security.JWTAuthorizationFilter;
import br.fatec.financas.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ClienteRepository repository;

	private static final String[] PUBLIC_MATCHERS = { 
			"/categorias/**", 
			"/pessoas-fisicas/**",
			"/pessoas-juridicas/**" 
		};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/pessoas-fisicas/**",
			"/pessoas-juridicas/**"
		};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
		         .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
		         .permitAll()
		         .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS)
		         .permitAll()
		         .antMatchers("/v3/api-docs/**", "/swagger-ui/**",
		        		 "/swagger-ui.html")
		         .permitAll()
		         .anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), 
				jwtUtil, repository));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
