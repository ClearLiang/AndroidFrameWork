package com.clearliang.frameworkdemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.clearliang.frameworkdemo.view.base.BaseApplication;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;

/**
 * @作者 ClearLiang
 * @日期 2018/5/4
 * @描述 RSA加解密工具
 **/

public class RSAUtil {
    private static RSAUtil sRSAUtil;
    private Context mContext;
    private RSAUtil() {
        mContext = BaseApplication.getInstance();// 使用Application 的context
    }
    public static RSAUtil getRSAUtil() {
        if (sRSAUtil != null) {
            synchronized (RSAUtil.class){
                sRSAUtil = new RSAUtil();
            }
        }
        return sRSAUtil;
    }

    private static String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密算法
    private static int KEYLENGTH = 2048;// 密钥位数

    private KeyPair keyPair = null;

    /**
     * 产生密钥对
     * 密钥长度，小于1024长度的密钥已经被证实是不安全的，通常设置为1024或者2048，建议2048
     */
    public void generateRSAKeyPair() {

        String publicKey = SPUtils.getInstance("RSAKey").getString("PublicKey");
        String privateKey = SPUtils.getInstance("RSAKey").getString("PrivateKey");
        if(TextUtils.isEmpty(publicKey) || TextUtils.isEmpty(privateKey)){
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                // 设置密钥长度
                keyPairGenerator.initialize(KEYLENGTH);
                // 产生密钥对
                keyPair = keyPairGenerator.generateKeyPair();

                LogUtils.e("生成新的密钥对");
                SPUtils.getInstance("RSAKey").put("PublicKey",getPublicKeyString());
                SPUtils.getInstance("RSAKey").put("PrivateKey",getPrivateKeyString());

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }else {
            LogUtils.e("本地存在密钥对");
        }

    }

    /**
     * 获取公钥
     **/
    private PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    private String getPublicKeyString() {
        return Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT);
    }

    public PublicKey getPubKeyFromSP() {
        PublicKey publicKey = null;
        try {
            publicKey = getPublicKey(SPUtils.getInstance("RSAKey").getString("PublicKey"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }


    /**
     * 获取私钥
     **/
    private PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    private String getPrivateKeyString() {
        return Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.DEFAULT);
    }

    public PrivateKey getPriKeyFromSP() {
        PrivateKey privateKey = null;
        try {
            privateKey = getPrivateKey(SPUtils.getInstance("RSAKey").getString("PublicKey"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /*******************************************************************************************************************************/


    /**
     * @函数名 - myEncrypt
     * @功能 - 公钥加密
     * @参数 - srcData 加密的数据
     * @返回值 - 加密后的字符串
     **/
    public String myEncrypt(String srcData) {

        Cipher cipher = null;
        byte[] output = null;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            output = cipher.doFinal(srcData.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(output, Base64.DEFAULT);

    }

    public String myEncrypt(String data, PublicKey publicKey) {

        Cipher cipher = null;
        byte[] output = null;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            output = cipher.doFinal(data.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(output, Base64.DEFAULT);

    }

    /**
     * @函数名 - myDecryption
     * @功能 - 私钥解密
     * @参数 - srcData 已加密的数据
     * @返回值 - 解密后的字符串
     **/
    public String myDecryption(String srcData) {

        Cipher cipher = null;
        byte[] output = null;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            output = cipher.doFinal(Base64.decode(srcData, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return new String(output);

    }

    public String myDecryption(String decrypData, PrivateKey privateKey) {

        Cipher cipher = null;
        byte[] output = null;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            output = cipher.doFinal(Base64.decode(decrypData, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return new String(output);

    }

    /**
     * String转公钥PublicKey
     *
     * @param key
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * String转私钥PrivateKey
     *
     * @param key
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

}