package com.security.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.config.RsaKeyConfig;
import com.security.demo.entity.Role;
import com.security.demo.entity.User;
import com.securitydemo.commons.util.JwtUtils;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录认证
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RsaKeyConfig rsaKeyConfig;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyConfig rsaKeyConfig) {
        this.authenticationManager = authenticationManager;
        this.rsaKeyConfig = rsaKeyConfig;
    }

    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authRequest);
        }catch (Exception e){
            try{
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter writer = response.getWriter();
                Map<String, String> map = new HashMap<>();
                map.put("code",HttpServletResponse.SC_UNAUTHORIZED+"");
                map.put("msg","用户名或密码错误！！！");
                writer.write(new ObjectMapper().writeValueAsString(map));
                writer.flush();
                writer.close();
            }catch (Exception ex){
                e.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = new User();
        user.setUsername(authResult.getName());
        user.setList((List<Role>) authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(user, rsaKeyConfig.getPrivateKey(), 24 * 60);
        response.addHeader("Authorization","Bearer"+token);
        try{
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter writer = response.getWriter();
            Map map = new HashMap();
            map.put("code",HttpServletResponse.SC_OK);
            map.put("msg","认证成功！！！");
            writer.write(new ObjectMapper().writeValueAsString(map));
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
