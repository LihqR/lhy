package test;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
public class Hash_256 {

        public static void main(String[] args) {
            try {
                int numberOfTests = 1000;
                long totalTime = 0;
                SecureRandom random = new SecureRandom();
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                for (int i = 0; i < numberOfTests; i++) {
                    // 生成随机明文
                    byte[] bytes = new byte[32]; // 256位随机数据
                    random.nextBytes(bytes);

                    // 开始计时
                    long startTime = System.nanoTime();

                    // 执行SHA-256哈希
                    byte[] hash = digest.digest(bytes);

                    // 结束计时
                    long endTime = System.nanoTime();

                    // 计算时间并累加
                    totalTime += (endTime - startTime);
                }

                // 计算平均时间
                double averageTime = (double) totalTime / numberOfTests;

                // 输出结果
                System.out.println("Average SHA-256 hash time: " + averageTime + " nanoseconds");

            } catch (NoSuchAlgorithmException e) {
                System.out.println("SHA-256 algorithm not available.");
            }
        }
    }


