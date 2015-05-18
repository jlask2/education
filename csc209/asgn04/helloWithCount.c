#include <stdio.h>
#include <stdlib.h>

int main (int argc, char* argv[ ]) 
{
   int count, i;
   if (argc != 3) {
       fprintf(stderr, "usage: %s firstname count\n", argv[0]);
       return -1; 
   }  
   else 
       count = atoi (argv[2]);

   for (i = 0; i < count; i++)
       printf("Hello, %s!\n", argv[1]); 

   return 0; 
}
