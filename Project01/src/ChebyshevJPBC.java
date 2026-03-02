import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class ChebyshevJPBC {
    public static void main(String[] args) {
        // 1. 初始化配对参数
        Pairing pairing = PairingFactory.getPairing("params/curves/a.properties"); // 确保路径正确！
        Field Zp = pairing.getZr(); // 整数环 Z_p

        // 2. 设置 x 和 n（示例值）
        Element x = Zp.newElement(2).getImmutable(); // x = 2 ∈ Z_p
        int n = 100; // 计算 T_100(x)

        // 3. 计算并测量时间
        long startTime = System.nanoTime();
        Element result = computeChebyshev(Zp, x, n);
        long endTime = System.nanoTime();

        System.out.println("T_" + n + "(" + x + ") = " + result);
        System.out.println("Time: " + (endTime - startTime) / 1e6 + " ms");
    }

    // 迭代计算切比雪夫多项式（避免递归栈溢出）
    public static Element computeChebyshev(Field Zp, Element x, int n) {
        if (n == 0) return Zp.newOneElement();  // T_0(x) = 1
        if (n == 1) return x.duplicate();       // T_1(x) = x

        Element T0 = Zp.newOneElement();        // T_0(x) = 1
        Element T1 = x.duplicate();             // T_1(x) = x
        Element Tn = Zp.newElement();

        for (int i = 2; i <= n; i++) {
            Tn = x.mul(T1).mul(2).sub(T0);     // T_n = 2x*T_{n-1} - T_{n-2}
            T0 = T1.duplicate();               // 更新 T_{n-2}
            T1 = Tn.duplicate();               // 更新 T_{n-1}
        }

        return Tn;
    }
}