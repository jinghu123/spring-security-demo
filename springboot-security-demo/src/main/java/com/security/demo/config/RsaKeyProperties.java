package com.security.demo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RsaKeyConfig.class)
public class RsaKeyProperties {
}
