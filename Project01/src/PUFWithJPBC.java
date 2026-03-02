import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.security.SecureRandom;

public class PUFWithJPBC {
    public static void main(String[] args) {
        // 初始化 JPBC
        Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
        Field Zp = pairing.getZr();

        // 模拟 PUF 生成 + JPBC 转换
        long startTime = System.nanoTime();
        byte[] pufResponse = generatePUFResponse(256);
        Element key = Zp.newElementFromBytes(pufResponse).getImmutable();
        long endTime = System.nanoTime();

        // 输出结果
        System.out.println("PUF 密钥: " + key);
        System.out.println("总耗时: " + (endTime - startTime) / 1e6 + " ms");
    }

    // 模拟 PUF
    public static byte[] generatePUFResponse(int bits) {
        SecureRandom random = new SecureRandom();
        byte[] response = new byte[bits / 8];
        random.nextBytes(response);
        return response;
    }
}