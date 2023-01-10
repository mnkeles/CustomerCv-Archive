package com.customercvarchive.security;


import com.customercvarchive.model.Role;
import com.customercvarchive.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${authentication.internal-api-key}")
    private String internalApiKey;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .antMatchers("/api/authentication/**").permitAll()
                //.antMatchers(HttpMethod.GET, "/api/file/**").permitAll() //Http get istek (@GetMapping) methodlarına izin verdil sadece
                .antMatchers("/api/customer/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/file/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/internal/**").hasRole(Role.SYSTEM_MANAGER.name())
                .anyRequest().authenticated(); // dieğr bütün endpointler için authenticated iste diyoruz


        //jwt filter
        //internal > jwt > authentication
        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)// burada gelen isteğin hangi filter sıralaması ile olacağını belirtiyoruz
                .addFilterBefore(internalApiAuthenticationFilter(), JwtAuthorizationFilter.class);
    }



    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }

    @Bean   // Spring anatasyonu kullanmadığımız clasları  spring içine bu şekilde dahil edebiliriz
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }
    @Bean
    public InternalApiAuthenticationFilter internalApiAuthenticationFilter() {
        //InternalApiAuthentication class ı spring bean olmadığı için böyle bir tanımlama ile springe dahil ettik
        return new InternalApiAuthenticationFilter(internalApiKey);
    }
}

