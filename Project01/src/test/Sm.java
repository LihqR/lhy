package test;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Sm {

    public static void main(String[] args) {
        // 定义椭圆曲线参数
//        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16); // 椭圆曲线的有限域参数
//        BigInteger a = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000", 16); // 椭圆曲线的系数a
//        BigInteger b = new BigInteger("0000000000000000000000000000000000000000000000000000000000000007", 16); // 椭圆曲线的系数b
//        BigInteger Gx = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16); // 基点G的横坐标
//        BigInteger Gy = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16); // 基点G的纵坐标
//        BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16); // 基点G的阶数
//
//        // 创建椭圆曲线
//        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFF", 16); // 160位素数
//        BigInteger a = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFC", 16); // 椭圆曲线的系数a
//        BigInteger b = new BigInteger("1C97BEFC54BD7A8B65ACF89F81D4D4ADC565FA45", 16); // 椭圆曲线的系数b
//        BigInteger Gx = new BigInteger("4A96B5688EF573284664698968C38BB913CBFC82", 16); // 基点G的横坐标
//        BigInteger Gy = new BigInteger("23A628553168947D59DCC912042351377AC5FB32", 16); // 基点G的纵坐标
       // BigInteger n = new BigInteger("0100000000000000000001F4C8F927AED3CA752257", 16); // 基点G的阶数

        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73", 16); // 160位素数
        BigInteger a = new BigInteger("0", 16); // 椭圆曲线的系数a
        BigInteger b = new BigInteger("7", 16); // 椭圆曲线的系数b
        BigInteger Gx = new BigInteger("3B4C382CE37AA192A4019E763036F4F5DD4D7EBB", 16); // 基点G的横坐标
        BigInteger Gy = new BigInteger("938CF935318FDCED6BC28286531733C3F03C4FEE", 16); // 基点G的纵坐标

        // 创建椭圆曲线
        ECCurve curve = new ECCurve.Fp(p, a, b);

        // 创建基点G
        ECPoint G = curve.createPoint(Gx, Gy);

//        // 随机生成标量k
//        BigInteger k = new BigInteger(160, new SecureRandom());

        // 执行点乘操作
        int iterations = 1000; // 迭代次数
        long startMultiply=0;
        long endMultiply=0;
        long totalTimeMultiply=0;
//        long startMultiply = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            // 随机生成标量k
            BigInteger k = new BigInteger(160, new SecureRandom());
             startMultiply = System.currentTimeMillis();
            ECPoint Q = (new FixedPointCombMultiplier()).multiply(G, k);
             endMultiply = System.currentTimeMillis();
             totalTimeMultiply += (endMultiply - startMultiply);
            Q = Q.normalize();
        }
//        long endMultiply = System.currentTimeMillis();
//        long totalTimeMultiply = endMultiply - startMultiply;

        // 执行点加操作
//        ECPoint P1 = curve.createPoint(Gx, Gy);
//        ECPoint P2 = curve.createPoint(new BigInteger(160, new SecureRandom()), new BigInteger(160, new SecureRandom()));
        long startAdd = 0;
        long endAdd = 0;
        long totalTimeAdd =0;
        for (int i = 0; i < iterations; i++) {
            ECPoint P1 = curve.createPoint(Gx, Gy);
            ECPoint P2 = curve.createPoint(new BigInteger(160, new SecureRandom()), new BigInteger(160, new SecureRandom()));
            startAdd = System.currentTimeMillis();
            ECPoint P = P1.add(P2);
            endAdd = System.currentTimeMillis();
            totalTimeAdd += (endAdd - startAdd);
            P = P.normalize();
        }
//         long  endAdd = System.currentTimeMillis();
//        long totalTimeAdd = endAdd - startAdd;

        // 计算平均时间
        double averageTimeMultiply = (double) totalTimeMultiply / iterations;
        double averageTimeAdd = (double) totalTimeAdd / iterations;

        // 输出结果
        System.out.println("Average time for point multiplication: " + averageTimeMultiply + " milliseconds");
        System.out.println("Average time for point addition: " + averageTimeAdd + " milliseconds");


    }
}