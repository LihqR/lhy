import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.security.MessageDigest;

public class HashTimeMeasurement {
    public static void main(String[] args) throws Exception {
        // 初始化 JPBC
        Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
        Field G1 = pairing.getG1();
        Field Zp = pairing.getZr();

        String input = "Hello, PBC!";
        byte[] inputBytes = input.getBytes();

        // 1. 测量标准 SHA-256 时间
        long startTime = System.nanoTime();
        byte[] sha256Hash = MessageDigest.getInstance("SHA-256").digest(inputBytes);
        long sha256Time = System.nanoTime() - startTime;

        // 2. 测量 JPBC 哈希到 G1 的时间
        startTime = System.nanoTime();
        Element g1Hash = G1.newElementFromHash(inputBytes, 0, inputBytes.length);
        long jpbcHashTime = System.nanoTime() - startTime;

        // 3. 测量哈希到 Zp 的时间（先调 SHA-256，再映射到 Zp）
        startTime = System.nanoTime();
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(inputBytes);
        Element zpHash = Zp.newElementFromBytes(hash).getImmutable();
        long zpTime = System.nanoTime() - startTime;

        // 输出结果
        System.out.println("SHA-256 时间: " + sha256Time / 1e6 + " ms");
        System.out.println("JPBC G1 哈希时间: " + jpbcHashTime / 1e6 + " ms");
        System.out.println("SHA-256 + Zp 映射时间: " + zpTime / 1e6 + " ms");
    }
}