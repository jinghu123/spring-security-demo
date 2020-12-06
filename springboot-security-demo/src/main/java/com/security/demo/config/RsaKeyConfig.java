package com.security.demo.config;

import com.securitydemo.commons.util.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;


@ConfigurationProperties("rsa.key")
public class RsaKeyConfig {
    private String privateFilePath;
    private String publicFilePath;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(publicFilePath);
        privateKey = RsaUtils.getPrivateKey(privateFilePath);
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }



    public String getPublicFilePath() {
        return publicFilePath;
    }

    public void setPublicFilePath(String publicFilePath) {
        this.publicFilePath = publicFilePath;
    }

    public String getPrivateFilePath() {
        return privateFilePath;
    }

    public void setPrivateFilePath(String privateFilePath) {
        this.privateFilePath = privateFilePath;
    }
}
