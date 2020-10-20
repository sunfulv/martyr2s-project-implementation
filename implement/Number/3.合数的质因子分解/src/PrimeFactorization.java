import java.util.ArrayList;

public class PrimeFactorization {

    public static ArrayList<Long> PrimeFactor(long N){ //使用短除法来找到一个合数的全部质因子
        long k = (long)Math.sqrt(N);
        ArrayList<Long> list = new ArrayList<>();
        for(long i=2;i<=k;i++) {
            while (N % i == 0) {
                list.add(i);
                N /= i;
            }
        }
        if(N!=1) list.add(N);
        return list; //返回质因子构成的列表
    }
    public static void main(String[] args) {
        //long N = 2424379123L;
        long N = 23;
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        ArrayList<Long> list = PrimeFactor(N);
        System.out.println("输出合数:"+N+"的全部质因子构成的列表:"+list);
        System.out.println("运行时间为:"+(end-start)+"ms");
    }
}
