### Martyr2项目实现——Number部分问题求解(3) Prime Factorization

### 质因子分解

#### 问题描述：

Prime Factorization – Have the user enter a number and find all Prime Factors (if there are any) and display them.

#### 翻译：

质因子分解：给定一个整数N，找到并输出他的全部质因子

#### 原理：

质因数分解，是将一个正整数写成几个约数的成绩，并且这些约数都是质数

给定一个[合数](https://www.wanweibaike.com/wiki-合数)*n*（这里，*n*是待分解的[正整数](https://www.wanweibaike.com/wiki-正整数)），试除法看成是用小于等于$\sqrt{n}$的每个[素数](https://www.wanweibaike.com/wiki-素数)去试除待分解的整数。如果找到一个数能够整除除尽，这个数就是待分解整数的因子。试除法一定能够找到*n*的因子。因为它检查*n*的所有可能的因子，所以如果这个算法“失败”，也就证明了*n*是个素数（参考wikipedia:[试除法](https://www.wanweibaike.com/wiki-%E8%A9%A6%E9%99%A4%E6%B3%95)）

算法实现：

```java
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
```

