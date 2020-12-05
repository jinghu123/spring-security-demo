package com.security.demo.config;

import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.DelegatingLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) //启用基于注解的安全性
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
//                .password("{noop}111")
//                .roles("USER");
        .userDetailsService(userServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/css/**")
                   .permitAll()
//                .regexMatchers("")
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
//                            .logoutSuccessHandler(new LogoutSuccessHandler() {
//                                @Override
//                                public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//
//                                }
//                            })
                             //session失效
                            .invalidateHttpSession(true)
                            //删除token
//                            .deleteCookies()
//                            .addLogoutHandler(new LogoutHandler() {
//                                @Override
//                                public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
//
//                                }
//                            })
                            .permitAll()
                    .and()
                        .csrf()
                        .disable();
    }
}
