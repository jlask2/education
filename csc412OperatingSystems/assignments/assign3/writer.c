/**********************************************************************
*Author@ Jason Laske
*Professor Anand
*Date Assigned: 4/2/2015
*Date Due: 4/14/2015
*Date Submitted: 4/14/2015
************************************************************************/
/*Description: writer.c:
*1a.) (4 points) The first of which is invoked as follows: writer n
*The program writes on its standard output Hello and hello alternately.  
*The two strings are printed n times each, where n is the command line 
*argument provided at the time of invoking the program.  The program uses 
*printf for this purpose. Note the absence of '\n' (the newline 
*character) in the two strings. 
*************************************************************************/
/**Header Declarations*/
#include <stdio.h> /*typically in /usr/include*/
#include <stdlib.h>

/**Main Function*/
int main (int argc, char* argv [])
/*pass command line arguments from shell; argc-number of parameters and 
argv[] the arguments*/
{
   int count, i; 
   if (argc != 2){
      fprintf(stderr, "usage: %s count(int)\n", argv[0]); 
      /* run as- writer (int) */
      exit (-1); 
      /*signal error (return other than 0) to shell*/
   }
   else{ 
      count = atoi (argv[1]); 
      count = count*2; //need to print for both upper and lower case
   }

   for ( i = 0; i < count; i++ ){
      if(i%2 == 0){
	printf("Hello"); //first print Hello 
      }else{
        printf("hello"); //Then print hello
      }
   } 
   return 0;
}
