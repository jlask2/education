/******************************************************************************
* Author@ Jason Laske
* Professor Anand
* Date Assigned: 4/23/2015
* Date Due: 5/5/2015
* Date Submitted: 5/5/2015
* Description:Simple program to
* output "Hello Mars!" n times.
*****************************************************************************/

#include <stdio.h> /*typically in /usr/include*/
#include <stdlib.h>

int main (int argc, char* argv [ ])
/*pass command line arguments from shell;
 argc-numberof parameters and argv[]—the arguments*/
{
   int count, i;
   if (argc != 2)
   {
      fprintf(stderr, "usage: %s count\n", argv[0]);
/* run as- hello world count*/
      exit (-1);
/*signal error (return other than 0) to shell*/
   }
   else
      count = atoi (argv[1]);

   for ( i = 0; i < count; i++ )
      printf("Hello Mars!\n");

   return 0;
}

