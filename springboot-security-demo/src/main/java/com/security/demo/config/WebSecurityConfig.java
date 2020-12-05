package com.security.demo.config;

import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userServiceImpl;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//            .inMemoryAuthentication()
//                .withUser("xiaochen")
//                .password("{noop}1111")
//                .roles("USER");
        .userDetailsService(userServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(31556926);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/css/**")
                   .permitAll()
//                .regexMatchers("[a-zA-Z\\-\\.]css","*.js")
//                    .permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
                        .formLogin()
                            //设置登录用户名参数
                            .usernameParameter("username")
                            //设置登录密码参数
                            .passwordParameter("password")
                             //设置登录页
                            .loginPage("/login.html")
                             //设置登录访问的url
                            .loginProcessingUrl("/login")

//                            .successHandler(new AuthenticationSuccessHandler() {
//                                @Override
//                                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                                    httpServletResponse.setContentType("application/json;charset=UTF-8");
//                                    PrintWriter writer = httpServletResponse.getWriter();
//                                    writer.write("登录成功！！！");
//                                }
//                            })
//                            .successForwardUrl("/getUser")
//                            .failureHandler(new AuthenticationFailureHandler() {
//                                @Override
//                                public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                                    httpServletResponse.setContentType("application/json;charset=UTF-8");
//                                    PrintWriter writer = httpServletResponse.getWriter();
//                                    writer.write("登录失败！！！");
//                                }
//                            })
                            .permitAll()
                    .and()
                        .logout()
                            .logoutUrl("/")
                            .logoutUrl("/loginOut")
                            .logoutSuccessUrl("/login.html")
                            .invalidateHttpSession(true)
                            .permitAll()
                    .and()
                        .csrf()
                        .disable();
    }
}
