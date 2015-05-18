/***************************************************************************
*Author@ Jason Laske
*Professor Anand
*Date Assigned: 4/2/2015
*Date Due: 4/14/2015
*Date Submitted: 4/14/201
***************************************************************************/
/*Description: reader.c:
*1b.) (6 points) The second program is invoked as follows: reader 
*The program reads characters until EOF from its standard input and 
*displays that information on screen, with at most 50 characters per line.  
*That is, for every 50 characters read from the input, a '\n' is added to 
*output.  For the last line, even if fewer than 50 characters are 
*printed, add a '\n' character.  (But you must ensure that you do not have an 
*unnecessary blank line if the number of characters in the last line is 
*exactly 50.) The program uses getchar and putchar for this purpose.
****************************************************************************/
/**Header Decalarations*/
#include <stdio.h> /*typically in /usr/include*/
#include <stdlib.h>
#include <string.h>

/**Constant Declarations*/
const int MAXLINELENGTH = 50;

/**Function Prototypes*/
void filecopy(FILE *, FILE *);

/**Main Function*/
int main(int argc, char *argv[]){

FILE *fp;

if(argc == 1){ //if no file specified read from std input
    filecopy(stdin, stdout);
}else{       //else attempt to read from the given file
  while(--argc > 0){
    if((fp = fopen(*++argv, "r")) == NULL){//file cannot be read
      printf("cat: cannot open %s\n", *argv);
      exit(EXIT_FAILURE);
    }else{
      filecopy(fp, stdout); //pass the file pinter to filecopy
      fclose(fp);
    }
  }
}
  return 0;
}
   
/** filecopy Function recieves the file pointer and 
* redirects to std output inserting a newline after 
* every 50 characters except for the final line if
* it contains exactly 50 characters
*/
void filecopy(FILE *ifp, FILE *ofp){
  
  int mod, c, index=0;
  
  while((c = getc(ifp)) != EOF){
     mod = index%MAXLINELENGTH; //used to determine the 50th cahracter
     if(mod == 0 && c != EOF){ //50th character add a '\n'
        putc(12, ofp);
        putc(c, ofp);
     }else{              //else put the cahracter 
        putc(c, ofp);
     }
     index++; //keeps track of the num of characters processed so far
  }
  putc(12, ofp);  //handle the case of exactly 50 charters in last line
}
