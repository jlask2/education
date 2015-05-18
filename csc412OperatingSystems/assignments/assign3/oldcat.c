/******************************************************************************
* Author@ Jason Laske
* Professor Anand
* Date Assigned: 4/2/2015
* Date Due: 4/12/2015
* Date Submitted: 4/12/2015
* Description: oldcat simulates the Unix cat command using c library functions
* Receives input from either standard input or a given file and ouputs to 
* standard output
******************************************************************************/
/**Header Declarations*/
#include <stdio.h>
#include <stdlib.h>

/**Function Prototypes*/
void filecopy(FILE *ifp, FILE *ofp);

/**Main Function*/
int main(int argc, char *argv[]){
FILE *fp;
char *prog = argv[0];

if(argc == 1){   //if no filename specified read from std input
  filecopy(stdin, stdout);
}else{        // else open the file coressponding to the given arguments
  while(--argc > 0){
     if((fp = fopen(*++argv, "r")) == NULL){//File cannot be opened 
         fprintf(stderr, "%s: can't open %s\n", prog, *argv);
         exit(EXIT_FAILURE);
     }else{
         filecopy(fp, stdout);  // pass the pointer to filecopy 
         fclose(fp);
     }
  }
}
return 0;
}

/** filecopy Function receives the pointer to the specified file and 
* outputs to standard output*/
void filecopy(FILE *ifp, FILE *ofp){
  int c;
  while((c = getc(ifp)) != EOF){
    putc(c, ofp);
  }
}
