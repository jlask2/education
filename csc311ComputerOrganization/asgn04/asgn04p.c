//**************************
//Jason Laske
//Daniel Rogers
//CSC311  Assignment04 *BONUS with pointers
//Date Due: 5/06/2014
//Date Submitted: 5/15/2014
//**************************

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

//***************************************************************
// Prototype Declaration
//***************************************************************

 /**transformInfile*/
 void transformInfile(char * s, char * t, FILE *fin, FILE *fout);

//***************************************************************
// Main
//***************************************************************

int main(int argc, char *argv[])
{
    FILE *fin;
    FILE *fout;// = fopen (s, "w");

//***************************************************************
// Command line arguments: there should be exactly 3 arguments
//***************************************************************
    
    if ( argc != 3 )
    {
        printf("Usage: a.out outputFileName inputFileName \n");
        return -1;
    }

//***************************************************************
// Open input file
//***************************************************************

    fin = fopen(argv[2], "r");
    if (fin==NULL)
    {
        printf("\nUnable to open file: %s!\n", argv[2]);
        return -1;
    }

//***************************************************************
// Open output file
//***************************************************************
    
    fout = fopen(argv[1], "w");
    if (fout==NULL)
    {
        printf("\nUnable to open output file: %s!\n", argv[1]);
        return -1;
    }

//***************************************************************
// Call the method to perform the calculations
//***************************************************************
    
    transformInfile(argv[1], argv[2], fin, fout);

//***************************************************************
// Close the files and exit
//***************************************************************

    fclose(fin);
    fclose(fout);

    return 0;
}

//***************************************************************
// Function Declaration
//***************************************************************

 /**transformInfile*/
 void transformInfile (char * s, char * t, FILE *fin, FILE *fout){
     char * data;
     char * newdata;
     int num[20148];
     char temp[256];
     fout = fopen (s, "w");
     data = (char*)malloc(sizeof(s) * 1000);
     newdata = (char*)malloc(sizeof(s) * 1000);
     int index;
     int total = 0;

     while(fgets(data, 80, fin))
     {
         index = 0;
         while(isdigit(*data) != 0)
	 {
            num[index] = *data - '0';
            data++;
            index++;
         }
         int i;
         int j;
         int k = index;
   	 int ex = 1;
         int number = 0;
         for(i = 0; i != k; i++)
	 {
             for(j = index; j != 1; j--)
	     {
                ex *= 10;
             }
             number += num[i] * ex;
             index--;
             ex = 1;
         }
	 /*DEBUG*///printf("number: %d\n", number);	 
         total += number;
	 fputs(data, fout);
     }
     /*DEBUG*///printf("total: %d\n", total);
     sprintf(temp, "%d\n", total);
     fputs(temp, fout);
 }
