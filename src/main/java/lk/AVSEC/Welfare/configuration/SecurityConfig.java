package lk.AVSEC.Welfare.configuration;

import lk.AVSEC.Welfare.asset.userManagement.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] ALL_PERMIT_URL = {"/favicon.ico", "/img/**", "/css/**", "/js/**", "/webjars/**",
            "/login", "/select/**", "/", "/index"};

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /*Session management - bean start*/
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    /*Session management - bean end*/

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     /*       http.csrf().disable();
            http.authorizeRequests().antMatchers("/").permitAll();*/
        // For developing easy to give permission all lin
        http
                .authorizeRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        //Anytime users can access without login
                                        //to see actuator details
                                        .antMatchers(ALL_PERMIT_URL).permitAll()
                                        //this is used the normal admin to give access every url mapping
                                        .antMatchers("/employee").hasRole("ADMIN")
                                        //Need to login for access those are
                                           .antMatchers("/employee/**").hasRole("ADMIN")
                                        .antMatchers("/mainWindow").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/user/**").hasRole("ADMIN")
                                        .antMatchers("/district/**").hasRole("MEMBER")

                                        .antMatchers("/dependent/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")


                                        .antMatchers("/designation/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")


                                        .antMatchers("/district/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")


                                        .antMatchers("/emailMessage/**").hasRole("MEMBER")

                                        .antMatchers("/employee/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")




                                        .antMatchers("/grievances/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")


                                        .antMatchers("/promotion/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")

                                        .antMatchers("/qualification/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")


                                        .antMatchers("/register").hasRole("ADMIN")
                                        .antMatchers("/register/token").hasRole("ADMIN")
                                        .antMatchers("/register/active/{{token}}").hasRole("ADMIN")

                                        .antMatchers("/role").hasRole("ADMIN")



                                        .antMatchers("/user/**").hasAnyRole("HOSS","PRESIDENT","ADMIN")

                                        .antMatchers("/workingPlace/**").hasRole("MEMBER")
                                        .antMatchers("/employeeWorkingPlace/**").hasRole("ADMIN")

                                        .antMatchers("/briefing/**").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .anyRequest()
                                        .authenticated())
                // Role base authentication
                // Login form
                .formLogin(
                        formLogin ->
                                formLogin
                                        .loginPage("/login")
                                        .loginProcessingUrl("/login")
                                        //Username and password for validation
                                        .usernameParameter("username")
                                        .passwordParameter("password")
                                        .successHandler(customAuthenticationSuccessHandler())
                                        .failureUrl("/login?error")
                          )
                //Logout controlling
                .logout(
                        logout ->
                                logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessHandler(customLogoutSuccessHandler())
                                        .deleteCookies("JSESSIONID")
                                        .invalidateHttpSession(true)
                                        .clearAuthentication(true))
                //session management
                //remember me
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
                .and()
                //session management
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement
                                        .sessionFixation()
                                        .migrateSession()
                                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                        .invalidSessionUrl("/login")
                                        .maximumSessions(2)
                                        .sessionRegistry(sessionRegistry()))
                //Cross site disable
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling();



    }
}

