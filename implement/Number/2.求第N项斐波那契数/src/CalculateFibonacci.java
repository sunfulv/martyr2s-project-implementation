import java.math.BigDecimal;


public class CalculateFibonacci {

    public static String calc1(int N){ //非递归算法
        if(N<0||N>=10000) return "error: 给定参数N超出范围[0,10000]";
        if(N==0) return "0";
        if(N==1) return "1";
        BigDecimal f1 = new BigDecimal(0);
        BigDecimal f2 = new BigDecimal(1);
        BigDecimal f = new BigDecimal(1);
        for(int i=1;i<N;i++){
            f = f2.add(f1);
            f1 = f2;
            f2 = f;
        }

        return f.toString();
    }
    public static long calculateFibonacci(int N){
        if(N==0) return 0;
        if(N==1) return 1;
        return calculateFibonacci(N-1)+calculateFibonacci(N-2);
    }
    public static long calc2(int N){ //利用斐波那契的矩阵形式来计算
        if(N==0) return 0;
        if(N==1) return 1;
        int n = N-1;
        long[][] res = {{1,1},{1,0}};
        res = pow_matrix(res,N-1);
        return res[0][0];
    }

    private static long[][] pow_matrix(long[][] A,int N){ //定义的二阶矩阵快速幂算法
        int[] stack = new int[N];
        long[][] res = A;
        int top=-1;
        while(N>=1){
            stack[++top] = N;
            N/=2;
        }
        while(top!=-1){
            if(stack[top] == 1) res = A;
            else if(stack[top]%2==0) //取出的为偶数
                res = matrixMultiply(res,res);
            else{
                res = matrixMultiply(res,res);
                res = matrixMultiply(res,A);
            }
            top--;
        }
        return res;
    }
    private static long[][] matrixMultiply(long[][] A,long[][] B){ //二阶矩阵乘法
        long[][] result = new long[2][2];

        for(int i=0;i<2;i++)
            for(int j=0;j<2;j++)
                for(int k=0;k<2;k++)
                result[i][j] +=A[i][k]*B[k][j];

        return result;
    }
    public static void main(String[] args) {
        int N = 90;
        long start,end,value;
        System.out.printf("------------------------利用不同的方法计算第%d项斐波那契数---------------------\n",N);
        start = System.currentTimeMillis();

//        value = calculateFibonacci(N);
//        end = System.currentTimeMillis();
//        System.out.println(String.format("递归算法执行的时间:%d ms",end-start));
//        System.out.println(String.format("斐波那契数列第%d项数值:%s",N,value));
//        System.out.println("------------------------------------------------------");
        start = System.currentTimeMillis();
        String val = calc1(N);
        end = System.currentTimeMillis();
        System.out.println(String.format("非递归算法执行的时间:%d ms",end-start));
        System.out.println(String.format("斐波那契数列第%d项数值:%s",N,val));
        System.out.println("--------------------------------------------------------");
        start = System.currentTimeMillis();
        value = calc2(N);
        end = System.currentTimeMillis();
        System.out.println(String.format("基于矩阵快速幂的斐波那契算法执行的时间:%d ms",end-start));
        System.out.println(String.format("斐波那契数列第%d项数值:%s",N,value));
    }
}
