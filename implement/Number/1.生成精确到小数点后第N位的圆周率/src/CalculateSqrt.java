
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateSqrt {
    private static int N = 1002;
    public static MathContext mc = new MathContext(N, RoundingMode.HALF_EVEN);
    private static String eps = "0."+repeatString("0",N/2)+"1";
    public static void main(String[] args) {
        BigDecimal n = new BigDecimal("2");
        BigDecimal r = sqrt(n);
        System.out.println(r.toString());
    }

    public static BigDecimal sqrt(BigDecimal num) {
        if(num.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal x = num.divide(new BigDecimal("2"), mc);
        while(x.subtract(x = sqrtIteration(x, num)).abs().compareTo(new BigDecimal(eps)) > 0);
        return x;
    }

    private static BigDecimal sqrtIteration(BigDecimal x, BigDecimal n) {
        return x.add(n.divide(x, mc)).divide(new BigDecimal("2"), mc);
    }
    private static String repeatString(String str,int n){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<n;i++){
            sb.append(str);
        }
        return sb.substring(0,sb.length());
    }
}
