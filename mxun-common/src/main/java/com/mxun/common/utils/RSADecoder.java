package com.mxun.common.utils;

import com.mxun.common.resultView.BusinessException;
import com.mxun.common.enums.ErrorEnum;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import javax.crypto.Cipher;
import java.io.StringReader;
import java.security.*;
import java.util.Base64;

/**
 * @Description: RSA解密工具
 * @Author: liuzhilin
 * @Date: 2025/2/28
 */
public class RSADecoder {

    private static final String privateKeyPem = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEA5HbdPt4WacsqgvZpa28hsqR9JUvMBw8NYL7VXUxknVZg07JF\n" +
            "PKavhkEAUXZxqkeXXWVf5vAAB2o3EzG0xA07FN/HpBoYJ3gsu4LRKQqM2wiIJccq\n" +
            "2jzQoEcWgPWWJbgAD+Fo4WI7BBUq55J2t2eQcaWSEuVxPj/4I82kY2VIW7A/OGt2\n" +
            "P5JhsES7eiVyPTc2hutxWrg37NFt3h0e58S4qIcApVwuM/Mp5Xe5T5mQRp2H7z0b\n" +
            "4XJu+SPkroEk3oV6ecUCVtQ5qeniApKA44sqN5bdxIAqkJb/SdZ2bDnXuzcHdMOZ\n" +
            "ZQB4+BJfWhN9oFh0H/D4ydhAdytdwCToOghsWwIDAQABAoIBAFRuiT1enjHi34ce\n" +
            "wb7uhymaGJXb+T7ylSMX44pjXRu4fHg//c54W43mH9P7Te58HrhpapPSRnB3FHuH\n" +
            "dyKtMRR5zzIaYqrTgtSZd+sO9xdLg+VSQcf9iaxyGSL3n9G4um0kBLqmruDHYd/L\n" +
            "BQyqvXsCkYViPgOfaHjy2rnRCHi5atx7yEFWaqk7ndQImP+aHNZ2THjceYsxzqJ5\n" +
            "nKk+b+fdVDBZYPrDww6zRHfzQ94GPgR9mZ+/CPBRo/RebKL7/Sv/JjACtulaih3c\n" +
            "M36OywDr1l8Qo4+eQ77PO3bIjLKh1MLbAHBauO5o4bFSmVgzmBEWFsnQ6uu3Y3pz\n" +
            "rABlzYECgYEA/6ZLvFed6kaBWwKkmnH4chALKN6IdCF/hwTE3/BzavWsoXClGVJe\n" +
            "SI3BewemQ++E2h3e3F1Wa1/O36Sy5GRZ2mqZKnfwi8OGyt2ct2LYKlDvs8LaC6jO\n" +
            "1VwhKLuwGgKrWRMKN5CtVaQ4OXGg00Vlw6yhGd1Q2FIGdtEULr6q7ysCgYEA5McH\n" +
            "iOSLXFDhBj80Q7r6JM/3oeoPWoXR50+jmLHHKl8K8zUUQCxoYiQbV2cyjLe5YaTv\n" +
            "1cMDhveLg7pA5vUch6oUGgEKlmRdDNL017i8tRHqsPkTo1z11Pp8wJU5wSd1k1uu\n" +
            "RSvETjqXpFdtb9qJU67RdSSCc86zhJ2PaLpiX5ECgYA+Cvy8Y8MmVSAVl0mdVeWG\n" +
            "wRAERiV1kZ+ig+zHsFJCdgcEUxMuVwgcsZJwKkYWnK1wRemNL3XGr0TEj/L+bMBF\n" +
            "urMbVGpjcPtohmn/M5TkiCn0RWinhbtUmnxNp2cwmM8+2dkT7R+z9hvTDRmLzH7L\n" +
            "Az1Byq1RIm8BLPKp+YoAawKBgHW6ShFS8rgt0q6BReb4yBK7hoYmyYHezVaIL/q6\n" +
            "uW//esYO8X3YnFqsk/lVXLj3Loml89l/wkfpCT1fUljHl8fkaPW8xXMA8LVRtbdx\n" +
            "FILlVm19r3JouwS/ZgVAhXj1UlbjXfmwfRN4qWJjryhZQEP0+Uqb4BmyK8U5w3u0\n" +
            "Nj1RAoGAMBJVTvH/j3Tt2qLT4dOSr8QqeUPOx2qkA4PQuAeExE4TwaEvqHw0HqyC\n" +
            "djEEsIMSX1Dts9wGm3VkFB/aA1V5BheDBFj7gifeAhWVhYMisOgRHoSTrBj/ccyX\n" +
            "hPs3yNITSf0+Qo//pFNIl139teqZes3uErTvAk49qlgNbplmRRg=\n" +
            "-----END RSA PRIVATE KEY-----";

    public static void main(String[] args) {
        String password = RSADecoder.decode("Xkoz09OvBbmXpgadSb8BdekycahSYXg0+f25+egA/pUS1pMX+1hAIVXNmaxlptgVKlj8I257becGEnBcHb+qpZ0X9fqLdCZtSHWTN51o9Uk7l9xG5RNCE0B7AJhS9qbY3fjf93n3RLWmuIV47cgnMPTIKmlcHr+UovP8Xu14U7fGPxuJXn2nnntvGkJMk1/8MWgiA/tpmuOAff1Sj40ZPTXMtS39FB4L471BYbjv5mOMyggvi3CsL8yVgGvj0o/hi+y9gvPEjSY1mi8uWyyyqEk5GYTQDjYFzIXiqWXV+xGmNM6wcVJ4jvjXMHtflXwqzVa1EO55M5JP8nX/4dn4sg==");
        System.out.println(password);
    }

    /**
     * @Description: 解密方法
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:29
     */
    public static String decode(String encryptedPasswordBase64) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            PEMParser pemParser = new PEMParser(new StringReader(privateKeyPem));
            Object object = pemParser.readObject();
            PrivateKey privateKey;

            if (object instanceof PEMKeyPair) {
                PEMKeyPair kp = (PEMKeyPair) object;
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                KeyPair keyPair = converter.getKeyPair(kp);
                privateKey = keyPair.getPrivate();
            } else if (object instanceof PrivateKeyInfo) {
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                privateKey = converter.getPrivateKey((PrivateKeyInfo) object);
            } else {
                throw new IllegalArgumentException("Invalid PEM object type.");
            }

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedPasswordBytes = Base64.getDecoder().decode(encryptedPasswordBase64);
            byte[] decryptedPasswordBytes = cipher.doFinal(encryptedPasswordBytes);

            return new String(decryptedPasswordBytes);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.SYS_VALID_DATA_ERROR);
        }
    }
}
