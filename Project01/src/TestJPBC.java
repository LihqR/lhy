import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class TestJPBC {
    public static void main(String[] args) {
        Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
        System.out.println("JPBC 初始化成功！配对类型: " + pairing.getDegree());
    }
}