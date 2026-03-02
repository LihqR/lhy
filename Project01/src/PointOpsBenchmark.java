import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class PointOpsBenchmark {
    public static void main(String[] args) {
        // 初始化
        Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
        Field G1 = pairing.getG1();
        Field Zr = pairing.getZr();

        // 生成随机点和标量
        Element P = G1.newRandomElement();
        Element Q = G1.newRandomElement();
        Element k = Zr.newRandomElement();

        // 测量点乘
        long start = System.nanoTime();
        //Element kP = P.mul(k);
        double mulTime = (System.nanoTime() - start) / 1e6;

        // 测量点加
        start = System.nanoTime();
        //Element sum = P.add(Q);
        double addTime = (System.nanoTime() - start) / 1e6;

        System.out.println("点乘 [k]P 耗时: " + mulTime + " ms");
        System.out.println("点加 P + Q 耗时: " + addTime + " ms");
    }
}