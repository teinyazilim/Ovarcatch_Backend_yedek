package com.tein.overcatchbackend.config;

import com.tein.overcatchbackend.security.jwt.JWTConfigurer;
import com.tein.overcatchbackend.security.jwt.JwtAuthenticationFilter;
import com.tein.overcatchbackend.security.jwt.TokenProvider;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //, securedEnabled = true
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final SecurityProblemSupport problemSupport;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(TokenProvider tokenProvider, SecurityProblemSupport problemSupport, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.problemSupport = problemSupport;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter(this.tokenProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**")
            .antMatchers("/h2-console/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
            .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
            .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
            .and()
            .frameOptions()
            .deny()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            //.antMatchers(HttpMethod.POST, SecurityConstants.AUTHENTICATE_URL).permitAll()
            //.antMatchers(HttpMethod.POST, SecurityConstants.AUTHENTICATE_SIGN_IN).permitAll()
            .antMatchers("/auth/signin").permitAll()
            .antMatchers("/api/file/downloadFile/**").permitAll()
            .antMatchers("/auth/signin").permitAll()
            .antMatchers("/api/**").authenticated()
            //.antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on

        //http.sessionManagement().maximumSessions(5).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry());
       /* http.sessionManagement()
            .maximumSessions(1).maxSessionsPreventsLogin(false)
            .sessionRegistry(sessionRegistry());*/
        //http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());

    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    /**
     * This method allows users to login the system.
     * */
    /*@Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder = auth.inMemoryAuthentication();
        try {
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        } catch (Exception e) {

            System.out.println("Login Failed");
        }
    }
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
      @Bean
    public RegisterSessionAuthenticationStrategy registerSessionAuthStr( ) {
        return new RegisterSessionAuthenticationStrategy( sessionRegistry( ) );
    }
    */

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
