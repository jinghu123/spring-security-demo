package com.security.demo.config;

import com.security.demo.filter.JwtLoginFilter;
import com.security.demo.filter.JwtVerifyFilter;
import com.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) //启用基于注解的安全性
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private RsaKeyConfig rsaKeyConfig;

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

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                // 前后端分离就不需要添加这个
//                .antMatchers( "/css/**")
//                   .permitAll()
////                .regexMatchers("")
////                    .permitAll()
//                .anyRequest()
//                    .authenticated()
//                // 前后端分离就不需要添加这个
//                    .and()
//                        .formLogin()
//                            //设置登录用户名参数
//                            .usernameParameter("username")
//                            //设置登录密码参数
//                            .passwordParameter("password")
//                             //设置登录页
//                            .loginPage("/login.html")
//                             //设置登录访问的url
//                            .loginProcessingUrl("/login")
//
////                            .successHandler(new AuthenticationSuccessHandler() {
////                                @Override
////                                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
////                                    httpServletResponse.setContentType("application/json;charset=UTF-8");
////                                    PrintWriter writer = httpServletResponse.getWriter();
////                                    writer.write("登录成功！！！");
////                                }
////                            })
////                            .successForwardUrl("/getUser")
////                            .failureHandler(new AuthenticationFailureHandler() {
////                                @Override
////                                public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
////                                    httpServletResponse.setContentType("application/json;charset=UTF-8");
////                                    PrintWriter writer = httpServletResponse.getWriter();
////                                    writer.write("登录失败！！！");
////                                }
////                            })
//                            .permitAll()
//                    .and()
//                        .logout()
//                            .logoutUrl("/")
//                            .logoutUrl("/loginOut")
//                            .logoutSuccessUrl("/login.html")
////                            .logoutSuccessHandler(new LogoutSuccessHandler() {
////                                @Override
////                                public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
////
////                                }
////                            })
//                             //session失效
//                            .invalidateHttpSession(true)
//                            //删除token
////                            .deleteCookies()
////                            .addLogoutHandler(new LogoutHandler() {
////                                @Override
////                                public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
////
////                                }
////                            })
//                            .permitAll()
//                    .and()
//                        //实现跨域
////                        .cors()
////                    .and()
//                        .csrf()
//                            .disable();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests()
                .anyRequest()
                .authenticated()

        .and()
        .addFilter(new JwtLoginFilter(super.authenticationManager(),rsaKeyConfig))
        .addFilter(new JwtVerifyFilter(super.authenticationManager(),rsaKeyConfig))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
