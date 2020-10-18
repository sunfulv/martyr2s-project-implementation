import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatePi {
    private static int[] map_array = {0,2,7,18,40,83,170,344,693,1000};

    public static String getPiValue(int N){ //获取精确到小数点后N位的圆周率近似值
        if(N<0||N>1000) return "error:给定参数超出范围！(默认参数范围为[1,1000])";
        int index = 0;
        for(int i=map_array.length-1;i>=1;i--){
            if(N>map_array[i]) {
                index = i+1;
                break; //给定的参数N位于map_array[i,i+1]之间
            }
        }
        String value = calculate(N,index);
        return value;
    }

    private static String calculate(int N,int index) {
        //利用高斯-勒让德迭代算法来计算圆周率的近似值，index为迭代的次数
        if (index == 0) return "3";
        //设置初值
        BigDecimal a0 = new BigDecimal(1);
        BigDecimal a1 = new BigDecimal(1);
        BigDecimal b = CalculateSqrt.sqrt(new BigDecimal("0.5"));
        BigDecimal t = new BigDecimal("0.25");
        BigDecimal p = new BigDecimal(1);
        BigDecimal pi = new BigDecimal(3);
        MathContext mc = CalculateSqrt.mc;
        //进行迭代
        for (int i = 0; i < index; i++) {
            a1 = a0.add(b);
            a1 = a1.divide(new BigDecimal(2), mc);
            b = b.multiply(a0);
            b = CalculateSqrt.sqrt(b);
            BigDecimal temp = new BigDecimal(1);
            temp = a0.subtract(a1);
            temp = temp.multiply(temp);
            temp = temp.multiply(p);
            t = t.subtract(temp);
            p = p.multiply(new BigDecimal(2));
            temp = a1.add(b);
            temp = temp.multiply(temp);
            temp = temp.divide(new BigDecimal(4), mc);
            pi = temp.divide(t, mc);
            a0 = a1;
        }
        return pi.toString().substring(0, N + 2);
    }



    public static void main(String[] args) {
        int N = 10;
        String pi = getPiValue(1001);
        System.out.println(pi);
    }
}
