//**************************
//Jason Laske
//Daniel Rogers
//CSC311  Assignment04
//Date Due: 5/06/2014
//Date Submitted: 5/06/2014
//**************************

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

//***************************************************************
// Prototype Declaration
//***************************************************************
 
 /**transformInfile*/
 void transformInfile(FILE *fin, FILE *fout);

//***************************************************************
// Main
//***************************************************************

int main(int argc, char *argv[]) 
{ 
     FILE *fin;  
     FILE *fout; 	

//***************************************************************
// Command line arguments: there should be exactly 3 arguments 
//***************************************************************

     if (argc != 3) 
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

     transformInfile(fin, fout);

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
 void transformInfile(FILE *fin, FILE *fout){
     int num = 0;
     int total = 0;
     char data[2048]; 
     while(fgets(data, sizeof(data), fin)) 
     { 
	 char newdata[2048];
	 char numdata[2048];
	 int numlength = 0;
         int i = 0;
	 int j = 0;
         int k = 0;
 
         num = atoi(data);
         total += num;
	 
	 /*DEBUG*/fprintf(fout, "num: %d\n", num);
	 
         while(num != 0){
	 	numdata[j] = num%10;
		num = num/10;
                j++;
		
		/*DEBUG*///printf("numdata[j]: %d\n", numdata[j]);
	 }
	 numdata[j] = '\0';
	 
	 numlength = strlen(numdata);  
	 
	 /*DEBUG*///printf("numlength: %d\n", numlength);
	 	 
         for(k = numlength; data[k] != '\0'; k++){
		newdata[i] = data[k];
		
		/*DEBUG*///printf("newdata[i]: %c\n", newdata[i]);
		
		i++;
	 }
	 newdata[i] = '\0';
	
	 fprintf(fout, "%s", newdata); 
     } 
	 fprintf(fout, "%d\n", total);
 }
