memofib = {}

def fib(n):
    if n < 3:
        return 1
    else:
        memofib[n] = fib(n - 1) + fib(n - 2)
        return memofib[n]    
    
for i in range(1, 10):
    print(dict(Number = i, Fibonacci = fib(i)))
    
print("Memoization Fibonnaci =", memofib)