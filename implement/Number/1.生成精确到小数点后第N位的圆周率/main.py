a0 = 1
a1 = a0
b = 1/2**(1/2)
t = 1/4
p = 1

for i in range(5):
	a1 = (a0+b)/2
	b = (a0*b)**(1/2)
	t = t-p*(a0-a1)**2
	p = 2*p
	
	pi = (a1+b)**2/(4*t)
	a0 = a1
	print("第{}次迭代计算出的圆周率近似值为:{}".format(i,pi))
