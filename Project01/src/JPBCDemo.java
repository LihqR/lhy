import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class JPBCDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        int rBits=160;
        int qBits=512;

        TypeACurveGenerator pg = new TypeACurveGenerator(rBits, qBits);
        PairingParameters pp = pg.generate();
        System.out.println(pp);
        Pairing bp = PairingFactory.getPairing(pp);
     //     Pairing bp = PairingFactory.getPairing("a.properties");

        Field G1 = bp.getG1();
        Field Zr = bp.getZr();
        Field GT = bp.getGT();

        //测试Zr有限域的加法和乘法
//        Element x = Zr.newOneElement().getImmutable();
//        Element y = Zr.newElement(4).getImmutable();
//        System.out.println(x);
//        System.out.println(y);
//        System.out.println(x.add(y));//加
//        System.out.println(x.mul(y));//乘
//        System.out.println(x.div(y));//除
//        System.out.println(x.sub(y));//减


        //测试ECC上的加法和乘法
//
//        Element g1 = G1.newRandomElement().getImmutable();
//        Element g2 = G1.newRandomElement().getImmutable();
//            //下面两种效果一样(当作加法群,乘法群类似)
//        System.out.println(g1.add(g1).add(g1));
//        Element a = Zr.newElement(3);
//        System.out.println(g1.mulZn(a));
//
//        System.out.println(g1.mul(new BigInteger("2")));
//        System.out.println(g1.pow(new BigInteger("2")));

        //测试群的阶
//        BigInteger r = G1.getOrder();
//        System.out.println(r);


        //执行时间问题
        Element g1 = G1.newRandomElement().getImmutable();
        Element g2 = G1.newRandomElement().getImmutable();
        Element g3 = G1.newRandomElement().getImmutable();
        Element a = Zr.newElement(4).getImmutable();
        Element b = Zr.newRandomElement().getImmutable();
        System.out.println(a.getLengthInBytes());
        System.out.println(g1);
        System.out.println(g2);



            //hash操作
        long hashStartTime = System.nanoTime();
        float repeatNum=100000;
        for(int i=0;i<repeatNum;i++){
            MessageDigest md = MessageDigest.getInstance("SHA-256");
             md.digest(g1.toBytes());
        }
        float AverageTime0= (System.nanoTime()-hashStartTime)/repeatNum;
       System.out.println("执行SHA-256操作"+repeatNum+"次平均时间"+AverageTime0+"ns");



            //点加操作
        long startSmTime=System.currentTimeMillis();
        float repeatSmNum=100000;
        for(int i=0;i<repeatSmNum;i++){
                g1.add(g2);
        }
        float AverageTime1= (System.currentTimeMillis()-startSmTime)/repeatSmNum;
        System.out.println("执行点加操作"+repeatSmNum+"次平均时间"+AverageTime1+"ms");


            //点乘操作
        float repeatSaNum=10000;
        BigInteger scalar = generateRandomBigInteger(160); // 生成160位的随机BigInteger
        long startSaTime=System.currentTimeMillis();
        for(int i=0;i<repeatSaNum;i++){
//            BigInteger scalar = generateRandomBigInteger(160); // 生成160位的随机BigInteger

            g3.mul(scalar);
          //  System.out.println(scalar.bitLength());
        }
        float AverageTime2= (System.currentTimeMillis()-startSaTime)/repeatSaNum;
        System.out.println("执行点乘操作"+repeatSaNum+"次平均时间"+AverageTime2+"ms");




            //配对操作
        float repeatPaNum=1000;
        long startPaTime=System.currentTimeMillis();
        for(int i=0;i<repeatPaNum;i++){

              bp.pairing(g1,g2);//配对运算
        }
        float AverageTime3= (System.currentTimeMillis()-startPaTime)/repeatPaNum;
        System.out.println("执行配对操作"+repeatPaNum+"次平均时间"+AverageTime3+"ms");




        //存储空间问题


    }
    public static BigInteger generateRandomBigInteger(int bitLength) {
        SecureRandom random = new SecureRandom();
        BigInteger randomBigInt;
        do {
            randomBigInt = new BigInteger(bitLength, random);
        } while (randomBigInt.bitLength() != bitLength);
        return randomBigInt;
    }
}
/*
        int iterations = 100000; // 迭代次数
        long totalExecutionTime= 0;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();

            // 执行点乘操作
            Element result1 = g1.duplicate().mul(g2);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            totalExecutionTime += executionTime;
        }

        double averageExecutionTime = (double) totalExecutionTime / iterations;
        System.out.println("Average Execution Time: " + averageExecutionTime + " ms");
*/

//        int iterations2 = 100000; // 迭代次数
//        long totalExecutionTime2 = 0;
//        for (int i = 0; i < iterations2; i++) {
//            long startTime = System.currentTimeMillis();
//
//            // 执行点加操作
//            Element result2= g1.duplicate().add(g2);
//
//            long endTime = System.currentTimeMillis();
//            long executionTime2 = endTime - startTime;
//            totalExecutionTime2 += executionTime2;
//        }
//
//        double averageExecutionTime2 = (double) totalExecutionTime2 / iterations2;
//        System.out.println("Average Execution Time: " + averageExecutionTime2 + " ms");
//    }
//}
