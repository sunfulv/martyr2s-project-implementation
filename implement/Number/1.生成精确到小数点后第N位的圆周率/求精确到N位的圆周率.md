## Martyr2项目实现——Number部分的问题求解 (1) Find Pi to Nth Digit

### Find Pi to Nth Digit

#### 问题描述：

Find PI to the Nth Digit – Enter a number and have the program generate PI up to that many decimal places. Keep a limit to how far the program will go.

#### 翻译：

给定一个整数N，让程序生成精确到小数点后N为的圆周率$\pi$

要保证程序运行的时间在一定限度下

#### 计算原理:

常用的圆周率的数值计算方法有**级数法，迭代法，随机算法**

> 级数法：使用圆周率$\pi$的级数表示来计算

 1. 高斯提出的用于平方倒数和公式
    $$
    \frac{\pi^2}{6}=\frac{1}{1^2}+\frac{1}{2^2}+\frac{1}{3^2}...+\frac{1}{(n-1)^2}+\frac{1}{n^2}+...
    $$



2. 莱布尼兹公式
   $$
   \frac{\pi}{4}=\frac{1}{1}-\frac{1}{3}+\frac{1}{5}-\frac{1}{7}+...+(-1)^{n-1}\frac{1}{2n-1}+...
   $$
   不过莱布尼兹公式的收敛速度很慢

3. 拉马努金提出的公式
   $$
   \frac{1}{\pi}=\frac{2\sqrt{2}}{9801}\sum_{k=0}^{\infin}\frac{(4k)!(1103+26390k)}{k!^4(396^{4k})}
   $$
   

使用级数法计算圆周率的收敛速度还是太慢

> 迭代算法：适合计算机程序实现的计算圆周率的方法

​	迭代算法的收敛速度要比无穷级数快很多

​	比较出名的算法是**高斯-勒让德算法**

​	**高斯-勒让德算法：**

​	引入四个数列 $\{a_n\},\{b_n\},\{t_n\},\{p_n\}$ 

​		他们的初值为：
$$
a_0=1\qquad b_0=\frac{1}{\sqrt{2}}\qquad t_0=\frac{1}{4}\qquad p_0=1
$$
​		递推公式为：
$$
a_{n+1}=\frac{a_n+b_n}{2}\;,b_{n+1}=\sqrt{a_nb_n}\;,t_{n+1}=t_n-p_n(a_n-a_{n+1})^2\;,p_{n+1}=2p_n.
$$


​		计算圆周率$\pi$近似值的方法：
$$
\pi \approx\frac{(a_{n+1}+b_{n+1})^2}{4t_{n+1}}
$$

​	**该算法每执行一次迭代，计算出的圆周率的正确位数就会增加一倍多。**





#### 具体的实现：

我们准备将圆周率计算到小数点后1000位（N<=1000)

**开方运算**

考虑到java的浮点数最高只支持64位double双精度浮点数，为了能够计算的更精确，考虑使用java的大数类·`java.Math.BigDecimal`来进行计算。

注意到在使用高斯勒让德算法计算圆周率时，需要用到开平方运算，`BigDecimal`并没有实现对大数对象的开方运算，我们需要自己实现。这里使用牛顿迭代法来计算大数的开平方。

具体的计算方法参考博客：[java BigDecimal开平方](https://blog.csdn.net/RickyIT/article/details/78051334)

**大数除法的精度问题**

在进行大数运算时，对于大数除法`BigDeciaml.divide()`，需要设定响应的计算精度和舍入方法(如何截断数值)

这里我们需要使用到`java.Math.MathContext`类，这个类描述了数字运算符的某些规则

我们可以使用默认的规则（比如`MathContext.DECIMAL128`)

也可以指定精度和舍入模式，定义自己的MathContext对象，构造方法为

`MathContext(int setPrecision, RoundingMode setRoundingMode)`

具体用法参考博客：[java_math_MathContext](https://www.cnblogs.com/zjushuiping/archive/2012/05/31/2528212.html)

为了能够实现我们的计算要求（1000位的圆周率），我们设定大数除法的计算精度为1002位（有效数字，自定义舍入方法）

`MathContext mc = new MathContext(1002, RoundingMode.HALF_EVEN);`

对于开平方运算，我们设置它的计算精度为500位（精确到小数点后100位）

下面是我们计算的每次迭代可以到达的计算精度

对于给定的参数N（要求计算小数位数）,我们通过查表来确定迭代次数，然后对得到的数值进行截断。

| 迭代次数 | 精度（小数点后精确到的位数） |
| :------: | :--------------------------: |
|    0     |              0               |
|    1     |              2               |
|    2     |              7               |
|    3     |              18              |
|    4     |              40              |
|    5     |              83              |
|    6     |             170              |
|    7     |             344              |
|    8     |             693              |
|    9     |             1000             |



#### 程序实现：

##### 主程序

```java
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
```

##### 计算平方根程序：

```java
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
```





​	