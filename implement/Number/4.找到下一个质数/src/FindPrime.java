import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class FindPrime {

    public static ArrayList<Integer> MakePrime(int N){
        int sqrt_i;
        ArrayList<Integer> prime_list = new ArrayList<>();
        for(int i=2;i<=N;i++){
            sqrt_i = (int)Math.sqrt(i);
            if(!prime_list.isEmpty()) {
                int prime_num = prime_list.size();
                for (int j=0;j<prime_num;j++) {
                    int prime = prime_list.get(j);
                    if (prime <= sqrt_i) {
                        if (i % prime == 0)//如果i能被整除，就不是素数，直接跳出当前循环
                            break;
                    }else{
                        prime_list.add(i);
                        break;
                    }
                }

                int last_prime = prime_list.get(prime_list.size()-1);
                if(i%last_prime!=0&&last_prime<sqrt_i) {//检查完当前的素数表，还未检查完
                    int j = last_prime + 1;
                    while (j <= sqrt_i && i % j != 0) j++;
                    if (j > sqrt_i) prime_list.add(i);
                }
            }else{
                prime_list.add(i); //素数表为空，直接将素数2加入
            }
        }
        return prime_list;
    }

    public static ArrayList<Integer> MakePrime2(int N){
        boolean[] isPrimes = new boolean[N+1];
        int sqrt_N = (int)Math.sqrt(N);
        for(int i=0;i<isPrimes.length;i++){
            isPrimes[i] = true;
        }
        for(int i=2;i<=sqrt_N;i++){
            if(isPrimes[i]) {//如果i是一个素数，就将i的倍数置为False（合数）
                for (int j = i * 2; j <= N; j += i) {
                    isPrimes[j] = false;
                }
            }
        }
        ArrayList<Integer> prime_list = new ArrayList<>();
        for(int i=2;i<=N;i++)
            if(isPrimes[i]) prime_list.add(i);
        return prime_list;


    }
    public static void main(String[] args) {

        int N = 100000000;
        long start = System.currentTimeMillis();
        ArrayList<Integer> prime_list = MakePrime(N);
        long end = System.currentTimeMillis();
        //System.out.println(prime_list);
        System.out.println("前"+N+"个整数中素数的个数:"+prime_list.size());
        System.out.println("使用试除法求取素数表的程序运行时间:"+(end-start)+"ms");
        start = System.currentTimeMillis();
        prime_list = MakePrime2(N);
        end = System.currentTimeMillis();
        //System.out.println(prime_list);
        System.out.println("前"+N+"个整数中素数的个数:"+prime_list.size());
        System.out.println("使用筛法求取素数表的程序运行时间:"+(end-start)+"ms");


    }
}
