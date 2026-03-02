//package test;
//
//import org.bouncycastle.asn1.sec.SECNamedCurves;
//import org.bouncycastle.asn1.x9.X9ECParameters;
//import org.bouncycastle.crypto.params.ECDomainParameters;
//import org.bouncycastle.math.ec.ECPoint;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
//public class Ecc {
//
//    private static final SecureRandom secureRandom = new SecureRandom();
//    private static final int TEST_COUNT = 100000;
//
//    public static void main(String[] args) {
//        X9ECParameters curveParams = SECNamedCurves.getByName("secp160r1");
//        ECDomainParameters params = new ECDomainParameters(curveParams.getCurve(), curveParams.getG(), curveParams.getN(), curveParams.getH());
//        ECPoint basePoint = params.getG();
//
//        long addTimeTotal = 0;
//        long multiplyTimeTotal = 0;
//
//        for (int i = 0; i < TEST_COUNT; i++) {
//            BigInteger k1 = new BigInteger(160, secureRandom);
//            BigInteger k2 = new BigInteger(160, secureRandom);
//            ECPoint point1 = basePoint.multiply(k1).normalize();
//
//            // Time the point addition
//            long startTime = System.nanoTime();
//            ECPoint point2 = point1.add(basePoint.multiply(k2).normalize());
//            addTimeTotal += System.nanoTime() - startTime;
//
//            // Time the point multiplication
//            startTime = System.nanoTime();
//            ECPoint result = basePoint.multiply(k1);
//            multiplyTimeTotal += System.nanoTime() - startTime;
//        }
//
//        System.out.println("Average time for point addition: " + (addTimeTotal / TEST_COUNT / 1000) + " microseconds");
//        System.out.println("Average time for point multiplication: " + (multiplyTimeTotal / TEST_COUNT / 1000) + " microseconds");
//    }
//}
