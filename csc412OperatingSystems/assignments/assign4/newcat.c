/******************************************************************************
* Author@ Jason Laske
* Professor Anand
* Date Assigned: 4/2/2015
* Date Due: 4/12/2015
* Date Submitted: 4/12/2015
* Description: newcat program uses unix system calls to achieve the same
* results of oldcat which utilizes c library functions. Accepts input from
* either standard input or a given file and outputs to standard output.
* Can easily be extended to output to a given file as well.
******************************************************************************/
/**Header Declarations*/
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

/**Funtion Prototype*/
void filecopy(int ifd, int ofd);

/**Main Function*/
int main(int argc, char *argv[]){

int fd;
char *prog = argv[0];

if(argc == 1){   //if no file specified read from std input
  filecopy(STDIN_FILENO, STDOUT_FILENO);
}else{           // else for each file name given read from 
                 //the file and output to std output
  while(--argc > 0){
     if((fd = open(argv[1], O_RDONLY, 0)) == -1){//reading from the file failed
         fprintf(stderr, "%s: can't open %s\n", prog, argv[1]);
         exit(EXIT_FAILURE);
     }else{
         filecopy(fd, STDOUT_FILENO);
         close(fd);
     }
  }
}
return 0;
}

/** filecopy Function reads each character from the given file
* and outputs to standard output
*/
void filecopy(int ifd, int ofd){
  int n;
  char buf[512];
  while((n = read(ifd, buf, 512)) > 0){
    write(ofd, buf, n);
  }
}

