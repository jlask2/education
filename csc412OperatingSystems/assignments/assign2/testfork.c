/*--------------------------------------------------------------------
* testfork.c                                                         
* Author@ Jason Laske
* Professor Anand
* CSC 412 Operating Systems
* Assignment 2
* 3/5/2015
* Description: Demonstrate the fork() system call function 
* by calling the getpid() function 
* an amount equal to the passing count argument and 
* distingush the parent and child processes.
--------------------------------------------------------------------*/

#include <stdio.h>     // printf, perror
#include <stdlib.h>    // exit
#include <sys/types.h> // pid_t
#include <unistd.h>    // _exit, fork

/*pass command line arguments from shell;
  argc.number of parameters and argv[].the arguments*/
int main(int argc, char *argv[]){
   
   const long MAX_COUNT = 2000;
   long count;
   pid_t pid;

   /*pass command line arguments from shell; argc.number of parameters and argv[].the arguments
	This command should only recieve exactly 2 arguments, a.out and the count*/
   if (argc != 2){
      /*run as - a.out count*/
      printf("Error - usage: %s count\n", argv[0]); 
      
      /*signal error (return value other than 0) to shell*/
      exit(EXIT_FAILURE);
   }else{ 
      int j;

      /*If the right amount of arguments are passed, check to make sure argument 2 is a integer 
        and is less than or equal to MAX_COUNT=2000(lets be nice to the system), otherwise exit*/
      for (j=1; j < argc; j++){
      char* end;
      count = strtol(argv[j], &end, 10); /*convert ascii value to long value using strtol*/
        if (argc == 2 && !end[0] && count >= 0 && count <= MAX_COUNT){
                printf("%s is valid, good to go!\n", argv[j]);
        } else {
                printf("%s is invalid input, argument 2 must be a integer and less than or equal to 2000, exiting program\n", argv[j]);
                exit(EXIT_FAILURE);
        }
      }
   }
   
   printf("This is testfork.c\n"); 
   
   pid =  fork();           // Fork the process
   
   /*Check the return of the fork() method to see which is the calling process or if a failure occured*/
   if(pid == -1){          // the child process was not created, most likely due to bad input
      printf("The child process was not created successfully. Exiting program");
      exit(EXIT_FAILURE);
   }else if(pid == 0){     // the child was created and fork() returned a 0 to the new child process
      printf("Child process started \n");
      int i;
      for(i = 1; i <= count; i++){ 
        printf("I am the child process and my process id is...(%d) count = %d\n", (int)getpid(), i);
      }
      printf("Child process ended \n");
      printf("This is testfork.c after child process end\n");
      exit(EXIT_SUCCESS);
   }else{           // the parent process recieves a positive value from fork(), the pid of the new child
      printf("Parent process started \n");
      int i;
      for(i = 1; i <= count; i++){
        printf("I am the parent process and my process id is...(%d) count = %d\n", (int)getpid(), i);
      }
      printf("Parent process ended\n");
      printf("This was testfork.c\n");
      exit(EXIT_SUCCESS);
   }
   return 0;
}
