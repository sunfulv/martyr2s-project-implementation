import java.util.Scanner;

public class MortgageCalculator{

    public static void mortgageCalcute(double P,double interest,int Y,int type){

        //输入参数贷款总额P，贷款利率interest，还款年限Y，还款类型type(0表示等额本息还款方式，1表示等额本金还款方式)
        switch(type){
            case 0:
                equalLoanPayment(P,interest,Y);
                break;
            case 1:
                equalPrincipalPayment(P,interest,Y);
                break;
        }
    }

    public static void equalLoanPayment(double P,double interest,int Y){ //等额本息还款计算函数
        int N = Y*12;
        double R = interest/12;
        double A = P*R*Math.pow(1+R,N)/(Math.pow(1+R,N)-1);
        System.out.printf("每月偿还的本息%7.2f\n",A*10000);
        double[] pi = new double[N];
        pi[0] = P*R;
        System.out.printf("第1个月需要偿还的利息:%8.2f  第1个月需要偿还的本金为:%7.2f\n",pi[0]*10000,(A-pi[0])*10000);
        for(int i=1;i<N;i++){
            pi[i] = pi[i-1]*(1+R)-A*R;
            System.out.printf("第%d个月需要偿还的利息:%7.2f  第%d个月需要偿还的本金为:%7.2f\n",i+1,pi[i]*10000,i+1,(A-pi[i])*10000);

        }

    }
    public static void equalPrincipalPayment(double P,double interest,int Y){ //等额本金还款计算函数

        int N = Y*12; //还款的总月份
        double R = interest/12; //还款的月利率
        double A = P*1.0/N; //每月需要还得本金
        System.out.printf("每月需要偿还的本金%7.2f\n",A*10000);
        double[] pi = new double[N+1];
        for(int i=1;i<=N;i++){
            pi[i] = -P*R*1.0/N*i+(P/N+P)*R;
            System.out.printf("第%d个月需要偿还的利息:%7.2f.第%d个月需要偿还的本息:%7.2f\n",i+1,pi[i]*10000,i+1,(pi[i]+A)*10000);
        }


    }
    public static void main(String[] args){
        //equalPrincipalPayment(45.4, 3.25/100, 15);
        Scanner sc = new Scanner(System.in);
        System.out.println("选择还款方式: 0 等额本息，1 等额本金");
        int PaymentType = sc.nextInt();
        System.out.println("输入还款总额(单位:万),还款年利率（百分数）与还款年限，用空格隔开");
        double Payment = sc.nextDouble();
        double interest = sc.nextDouble();
        int years = sc.nextInt();
        sc.close();
        System.out.println("还款总额:"+Payment+"还款年利率:"+interest+"% "+"还款年限:"+years+"年");
        mortgageCalcute(Payment,interest/100,years,PaymentType);
    }
}