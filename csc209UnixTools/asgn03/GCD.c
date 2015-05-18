#include <stdio.h>

int main()
{
   /*Function prototype*/  
   int gcd (int, int); 

   int a, b; 
   printf("Please enter first positive integer : ");
   scanf("%d", &a); 
   printf("Please enter second positive integer: ");
   scanf("%d", &b); 
   printf("The required gcd is %d\n", gcd(a,b)); 
   return 0; 
}

int gcd(int x, int y)
{
   while (x != y) 
      if (x > y) 
         x = x - y; 
      else 
         y = y - x; 
   return x; 
}
