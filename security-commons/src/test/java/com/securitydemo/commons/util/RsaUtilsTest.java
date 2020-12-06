package com.securitydemo.commons.util;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

public class RsaUtilsTest {
    private final String privateFilePath = "D:\\auth_key\\id_key_rsa";
    private final String publicFilePath = "D:\\auth_key\\id_key_rsa.pub";

    @org.junit.Test
    public void getPublicKey() throws Exception {
        RsaUtils.generateKey(publicFilePath,privateFilePath,"xiaochen");
    }

    @org.junit.Test
    public void getPrivateKey() throws Exception {
        PublicKey publicKey = RsaUtils.getPublicKey(publicFilePath);
        System.out.println(publicKey);
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateFilePath);
        System.out.println(privateKey);
    }

    @org.junit.Test
    public void testGetPublicKey() {
    }

    @org.junit.Test
    public void testGetPrivateKey() {
    }
}