package test;


import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * ECCDSA加签验签工具类
 * @author Administrator
 *
 */
public class ECDSAUtil {

    private static final String SIGNALGORITHMS = "SHA256withECDSA";
    private static final String ALGORITHM = "EC";
    private static final String SECP256K1 = "secp256k1";


    public static void main(String[] args) throws Exception {

//        生成公钥私钥
        KeyPair keyPair1 = getKeyPair();
        PublicKey publicKey1 = keyPair1.getPublic();
        PrivateKey privateKey1 = keyPair1.getPrivate();
        //密钥转16进制字符串
        String publicKey = HexUtil.encodeHexString(publicKey1.getEncoded());
        String privateKey = HexUtil.encodeHexString(privateKey1.getEncoded());
        System.out.println("生成公钥："+publicKey);
        System.out.println("生成私钥："+privateKey);
        //16进制字符串转密钥对象
        PrivateKey privateKey2 = getPrivateKey(privateKey);
        PublicKey publicKey2 = getPublicKey(publicKey);
        //加签验签
       // String data="需要签名的数据";
        byte[] dataBytes = new byte[20];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(dataBytes);
        String data = HexUtil.encodeHexString(dataBytes);

        String signECDSA = signECDSA(privateKey2, data);
        boolean verifyECDSA = verifyECDSA(publicKey2, signECDSA, data);
        System.out.println("验签结果："+verifyECDSA);

        // 测试迭代的次数
        int iterations = 100;
        long totalSignTime = 0;
        long totalVerifyTime = 0;

        for (int i = 0; i < iterations; i++) {
            long signStartTime = System.currentTimeMillis();
            String signature = signECDSA(privateKey2, data);
            long signEndTime = System.currentTimeMillis();
            long signElapsedTime = signEndTime - signStartTime;
            totalSignTime += signElapsedTime;

            long verifyStartTime = System.currentTimeMillis();
            boolean isVerified = verifyECDSA(publicKey2, signature, data);
            long verifyEndTime = System.currentTimeMillis();
            long verifyElapsedTime = verifyEndTime - verifyStartTime;
            totalVerifyTime += verifyElapsedTime;

            if (!isVerified) {
                System.out.println("Signature verification failed!");
            }
        }

        // 计算平均用时
        double averageSignTime = (double) totalSignTime / iterations;
        double averageVerifyTime = (double) totalVerifyTime / iterations;

        System.out.println("Average signing time: " + averageSignTime + " ms");
        System.out.println("Average verification time: " + averageVerifyTime + " ms");
    }



    /**
     * 加签
     * @param privateKey 私钥
     * @param data 数据
     * @return
     */
    public static String signECDSA(PrivateKey privateKey, String data) {
        String result = "";
        try {
            //执行签名
            Signature signature = Signature.getInstance(SIGNALGORITHMS);
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] sign = signature.sign();
            return HexUtil.encodeHexString(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 验签
     * @param publicKey 公钥
     * @param signed 签名
     * @param data 数据
     * @return
     */
    public static boolean verifyECDSA(PublicKey publicKey, String signed, String data) {
        try {
            //验证签名
            Signature signature = Signature.getInstance(SIGNALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(data.getBytes());
            byte[] hex = HexUtil.decode(signed);
            boolean bool = signature.verify(hex);
            // System.out.println("验证：" + bool);
            return bool;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从string转private key
     * @param key 私钥的字符串
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] bytes = DatatypeConverter.parseHexBinary(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 从string转publicKey
     * @param key 公钥的字符串
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {

        byte[] bytes = DatatypeConverter.parseHexBinary(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }




    /**
     * 生成密钥对
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair() throws Exception {

        ECGenParameterSpec ecSpec = new ECGenParameterSpec(SECP256K1);
        KeyPairGenerator kf = KeyPairGenerator.getInstance(ALGORITHM);
        kf.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = kf.generateKeyPair();
        return keyPair;
    }


}

