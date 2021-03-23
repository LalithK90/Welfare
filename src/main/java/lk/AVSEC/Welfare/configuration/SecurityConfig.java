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
                                        .antMatchers("/district").hasRole("MEMBER")

                                        .antMatchers("/dependent").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/dependent/add").hasRole("SECRETORY")
                                        .antMatchers("/dependent/save").hasRole("SECRETORY")
                                        .antMatchers("/dependent/update").hasRole("SECRETORY")
                                        .antMatchers("/dependent/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/dependent/edit/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/dependent/delete/{{id}}").hasRole("SECRETORY")

                                        .antMatchers("/designation").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/designation/add").hasRole("SECRETORY")
                                        .antMatchers("/designation/save").hasRole("SECRETORY")
                                        .antMatchers("/designation/update").hasRole("SECRETORY")
                                        .antMatchers("/designation/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/designation/edit/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/designation/delete/{{id}}").hasRole("SECRETORY")

                                        .antMatchers("/district").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/district/add").hasRole("ADMIN")
                                        .antMatchers("/district/save").hasRole("ADMIN")
                                        .antMatchers("/district/update").hasRole("ADMIN")
                                        .antMatchers("/district/{{id}}").hasRole("ADMIN")
                                        .antMatchers("/district/edit/{{id}}").hasRole("ADMIN")
                                        .antMatchers(" /district/delete/{{id}}").hasRole("ADMIN")
                                        .antMatchers("/district/getDistrict/{{province}}").hasRole("ADMIN")

                                        .antMatchers("/emailMessage").hasRole("MEMBER")
                                        .antMatchers("/emailMessage/add").hasRole("SECRETORY")
                                        .antMatchers("/emailMessage/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/emailMessage/add").hasRole("SECRETORY")

                                        .antMatchers("/employee").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/employee/add").hasRole("SECRETORY")
                                        .antMatchers("/employee/save").hasRole("ADMIN")
                                        .antMatchers("/employee/update").hasRole("SECRETORY")
                                        .antMatchers("/employee/delete").hasRole("ADMIN")
                                        .antMatchers("/employee/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/employee/**").hasRole("ADMIN")



                                        .antMatchers("/grievances").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/grievances/add").hasRole("MEMBER")
                                        .antMatchers("/grievances/update").hasRole("SECRETORY")
                                        .antMatchers("/grievances/{{id}}").hasRole("MEMBER")
                                        .antMatchers("/grievances/edit/{{id}}").hasRole("PRESIDENT")
                                        .antMatchers("/grievances/delete/{{id}}").hasRole("PRESIDENT")
                                        .antMatchers("/grievances/action/{{id}}").hasRole("HOSS")

                                        .antMatchers("/promotion/").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/promotion/add").hasRole("SECRETORY")
                                        .antMatchers("/promotion/save").hasRole("SECRETORY")
                                        .antMatchers("/promotion/update").hasRole("SECRETORY")
                                        .antMatchers("/promotion/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/promotion/*").hasRole("SECRETORY")

                                        .antMatchers("/qualification").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/qualification/add").hasRole("SECRETORY")
                                        .antMatchers("/qualification/save").hasRole("SECRETORY")
                                        .antMatchers("/qualification/update").hasRole("SECRETORY")
                                        .antMatchers("/qualification/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/qualification/edit/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/qualification/delete/{{id}}").hasRole("HOSS")

                                        .antMatchers("/register").hasRole("ADMIN")
                                        .antMatchers("/register/token").hasRole("ADMIN")
                                        .antMatchers("/register/active/{{token}}").hasRole("ADMIN")

                                        .antMatchers("/role").hasRole("ADMIN")
                                        .antMatchers("/role/add").hasRole("ADMIN")
                                        .antMatchers("/role/update").hasRole("ADMIN")
                                        .antMatchers("/role/search").hasRole("ADMIN")
                                        .antMatchers("/role/{{id}}").hasRole("ADMIN")
                                        .antMatchers("/role/edit/{{id}}").hasRole("ADMIN")
                                        .antMatchers("/role/remove/{{id}}").hasRole("ADMIN")


                                        .antMatchers("/user").hasAnyRole("HOSS","PRESIDENT","ADMIN")
                                        .antMatchers("/user/add").hasAnyRole("HOSS","PRESIDENT","ADMIN")
                                        .antMatchers("/user/update").hasAnyRole("HOSS","PRESIDENT","ADMIN")
                                        .antMatchers("/user/workingPlace").hasAnyRole("HOSS","PRESIDENT","ADMIN")
                                        .antMatchers("/user/{{id}}").hasAnyRole("HOSS","PRESIDENT","ADMIN")
                                        .antMatchers("/user/edit/{{id}}").hasAnyRole("HOSS","PRESIDENT","ADMIN")
                                        .antMatchers("/user/remove/{{id}}").hasAnyRole("HOSS","PRESIDENT","ADMIN")
                                        .antMatchers("/user/search").hasAnyRole("HOSS","PRESIDENT","ADMIN")

                                        .antMatchers("/workingPlace").hasRole("MEMBER")
                                        .antMatchers("/workingPlace/add").hasRole("SECRETORY")
                                        .antMatchers("/workingPlace/save").hasRole("SECRETORY")
                                        .antMatchers("/workingPlace/update").hasRole("SECRETORY")
                                        .antMatchers("/workingPlace/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/workingPlace/edit/{{id}}").hasRole("SECRETORY")
                                        .antMatchers("/workingPlace/delete/{{id}}").hasRole("HOSS")

                                        .antMatchers("/employeeWorkingPlace").hasRole("ADMIN")

                                        .antMatchers("/briefing").hasAnyRole("ADMIN","HOSS","PRESIDENT","SECRETORY","TREASURE","AGENT","MEMBER")
                                        .antMatchers("/briefing/add").hasAnyRole("HOSS","PRESIDENT")
                                        .antMatchers("/briefing/save").hasAnyRole("HOSS","PRESIDENT")
                                        .antMatchers("/briefing/update").hasAnyRole("HOSS","PRESIDENT")
                                        .antMatchers("/briefing/{{id}}").hasRole("MEMBER")
                                        .antMatchers("/briefing/edit/{{id}}").hasAnyRole("HOSS","PRESIDENT")
                                        .antMatchers("/briefing/delete/{{id}}").hasAnyRole("HOSS","PRESIDENT")
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
                                        .failureForwardUrl("/login?error")
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

