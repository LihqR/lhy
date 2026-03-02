package test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

public class Aes_128 {
    public static void main(String[] args) {
        try {
            int numberOfTests = 1000;
            long totalEncryptTime = 0;
            long totalDecryptTime = 0;

            for (int i = 0; i < numberOfTests; i++) {
                // 生成随机密钥
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128); // 使用AES-128
                SecretKey secretKey = keyGen.generateKey();

                // 生成随机明文
                byte[] plainText = new byte[16]; // 128位明文
                new SecureRandom().nextBytes(plainText);

                // 获取AES Cipher实例
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

                // 加密
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                long startTimeEncrypt = System.nanoTime();
                byte[] encryptedText = cipher.doFinal(plainText);
                long endTimeEncrypt = System.nanoTime();

                // 解密
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                long startTimeDecrypt = System.nanoTime();
                byte[] decryptedText = cipher.doFinal(encryptedText);
                long endTimeDecrypt = System.nanoTime();

                // 计算加密和解密时间并累加
                totalEncryptTime += (endTimeEncrypt - startTimeEncrypt);
                totalDecryptTime += (endTimeDecrypt - startTimeDecrypt);
            }

            // 计算平均加密和解密时间
            double averageEncryptTime = (double) totalEncryptTime / numberOfTests;
            double averageDecryptTime = (double) totalDecryptTime / numberOfTests;

            // 输出结果
            System.out.println("Average encryption time: " + averageEncryptTime + " nanoseconds");
            System.out.println("Average decryption time: " + averageDecryptTime + " nanoseconds");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
