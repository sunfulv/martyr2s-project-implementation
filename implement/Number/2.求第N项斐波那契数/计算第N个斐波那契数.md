## Martyr2项目实现——Number部分的问题求解（2）Fibonacci Sequence 

### Fibonacci Sequence

#### 题目描述：

Enter a number and have the program generate the Fibonacci sequence to that number or to the Nth number

#### 翻译：

给定正整数N，编写程序生成斐波那契数列的前N项，或者第N项。

#### 计算原理：

斐波那契数列$\{F_n\}$使用递推定义：

初值：$F_0=0,\quad\ F_1=1$

递推公式：$F_n=F_{n-1}+F_{n-2},(n>=2)$

根据递推公式，使用不动点方程$x^2-x-1=0$可以求出斐波那契数列的通项公式

通项公式：$F_n=\frac{1}{\sqrt{5}}(\phi^n-\psi^n)$, 

其中$\phi,\quad\psi$为方程$x^2-x-1=0$的两个解。$\phi=\frac{1+\sqrt{5}}{2},\quad\psi=1-\phi=\frac{1-\sqrt{5}}{2}$



``**斐波那契数列的矩阵形式**
$$
\left(
\begin{array}{ccc}
F_{n+1}&F_{n}\\
F_{n}&F_{n-1}\\
\end{array}
\right)
=
\left(
\begin{array}{ccc}
F_{n}&F_{n-1}\\
F_{n-1}&F_{n-2}\\
\end{array}
\right)
\left(
\begin{array}{ccc}
1&1\\
1&0\\
\end{array}
\right)
$$
对应的递推公式为：
$$
\left(
\begin{array}{ccc}
F_{n+1}&&F_{n}\\
F_{n}&&F_{n-1}\\
\end{array}
\right)
=
\left(
\begin{array}{ccc}
1&&1\\
1&&0\\
\end{array}
\right)^n
$$


#### 算法：

1. 第一种实现方法是**递归**，因为给定了初值和递推公式，可以直接递归计算。

   但是当计算的项数N很大时，可能会导致递归层数过大导致栈溢出
   
   而且在计算时，有些中间的斐波那契项被计算了多次，重复计算。 
   
   **实现程序**
   
   ``
   
   ```java
   public static long calculateFibonacci(int N){
       if(N==0) return 0;
       if(N==1) return 1;
       return calculateFibonacci(N-1)+calculateFibonacci(N-2);
   }
   ```




2. 将递归算法改为非递归，同时使用临时变量来存储计算的中间值。

   **实现程序**

   ```java
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
   ```

3. 利用斐波那契数列的矩阵形式，借助快速幂算法来加快计算矩阵的幂(只有在需要计算的项数很大时才有优势)

   矩阵的幂快速计算参考博客：[基础算法--快速幂详解](https://www.cnblogs.com/sun-of-Ice/p/9330352.html)

```java
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
```

递归算法与非递归算法的运行时间比较:

`递归算法执行的时间:536
斐波那契数列第40项数值:102334155
非递归算法执行的时间:0
斐波那契数列第40项数值:102334155`

非递归与矩阵算法运行时间比较：

------------------------利用不同的方法计算第90项斐波那契数--------------------
非递归算法执行的时间:1 ms
斐波那契数列第90项数值:2880067194370816120

基于矩阵快速幂的斐波那契算法执行的时间:0 ms
斐波那契数列第90项数值:2880067194370816120