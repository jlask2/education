/* ******************************************************
 * Author@ Jason Laske 
 * Date Due: 5/5/2015
 * Date Submitted: 5/5/2015
 * Description: implementation of wc command.
 *******************************************************/

     /* headers */
     #include <stdlib.h>
     #include <stdio.h>
     #include <sys/types.h>
     #include <unistd.h>
     #include <fcntl.h>
     #include <stdarg.h>
     #include <stdbool.h>
     
     /* Constants */      

     #define MAX_BUFF_SIZE 1024
     #define MAX_LINE_SIZE 2000

     /* Global variables */      

     /* Current file counters: chars, words, lines */
     int ccount;
     int wcount;
     int lcount;

     /* Totals counters: chars, words, lines */
     int total_ccount = 0;
     int total_wcount = 0;
     int total_lcount = 0;
     
     /* Prototypes for Functions */
     void report (char *file, int ccount, int wcount, int lcount);
     void counter (char *file, int fd);
     void read_in_stdin(FILE *ifp, FILE *ofp);

     /* main - run the main routine */
     int main (int argc, char **argv)
     {
       int i;
       int fd;

       if(argc == 1){
           read_in_stdin(stdin,stdout);
       }

       if((fd = open(argv[1], O_RDONLY, 0)) == -1){//reading from the file failed
         fprintf(stderr, "%s: can't open %s\n", argv[0], argv[1]);
         exit(EXIT_FAILURE);
       }

       for (i = 1; i < argc; i++)
	 counter (argv[i], fd);

       if (argc > 2)
	 report ("total", total_ccount, total_wcount, total_lcount);
       return 0;
     }

     /* Output counters for given file */
     void report (char *file, int ccount, int wcount, int lcount)
     {
       printf (" %d %d %d %s\n", lcount, wcount, ccount, file);
     }
           
     /* Process file. */
     void counter (char *file, int fd)
     {
       char buf[MAX_BUFF_SIZE];
       char wordbuff[MAX_BUFF_SIZE];
       char c, prec;  
       bool word = false;
       int i, count, correctlines, j;	
       i = j = count = correctlines = ccount = wcount = lcount = 0; 
       //count = read(fd,buf,sizeof(buf));	

       while((count = read(fd,buf,sizeof(buf))) > 0)//read bytes 	
       //while(count > 0)
       {
            while(i < count)
            {		
		if(buf[i] == '\0'){
			printf("null terminating? %c ", buf[i]);		
            		c = '\0';
		}else{
			c = buf[i];
		}
		ccount++;
		if(isalpha(c)){
			wordbuff[j] = c;
			word = true;		
		}
				
		if (c == ' ' || c == '\n' || c == '\t')
		{
			if(word == true){
		       		wcount++;
				word = false;
			}
	      	}        
           	if (c == '\n')
		{ 
             		lcount++;
			//correctlines++;
		}
	 	i++;
             }
	     count = read(fd,buf,sizeof(buf));
       }
       //wcount = wcount - correctlines;       

       close (fd);
       
       report (file, ccount, wcount, lcount);
       total_ccount += ccount;
       total_wcount += wcount;
       total_lcount += lcount;
     }

void read_in_stdin(FILE *ifp, FILE *ofp)
{
  int c;
     while((c = getc(ifp))!= EOF){
     }
}
