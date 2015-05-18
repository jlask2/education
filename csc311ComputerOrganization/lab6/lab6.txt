//*****************************************
//Jason Laske 
//Daniel Rogers
//CSC 311 
//Due Date: 4/3/2014
//Submitted: 4/3/2014
//*****************************************

#include <stdlib.h>
#include <stdio.h>

int main(){

//*****************************************
//VARIABLE DECLARATION
//*****************************************

int a;
int b;
int c;
int d;

int fiveNumberInput;

int num1 = 0;
int num2 = 0;
int num3 = 0;
int num4 = 0;
int num5 = 0;

//******************************************
//1.) PROMPT FOR 3 INTEGERS 
//******************************************

printf("Please input three integers: \n");

//Input
//*******************

scanf("%d", &a);
scanf("%d", &b);
scanf("%d", &c);

//Calculation
//*******************

d = ((a*b)-c);

//Output
//*******************

printf("\n");
printf("%d", d);
printf("\n");

//*******************************************
//2.) OUTPUT STATEMENTS
//*******************************************

printf("\n");
printf("This is a C program  \n");
printf("\n");
printf("This is\na C program \n");
printf("\n");
printf("This\nis\na\nC\nProgram\n");
printf("\n");
printf("This\tis\ta\tC\tProgram\n");
printf("\n");
printf("*****\n****\n***\n**\n*\n");
printf("\n");

//*********************************************
//3.) PROMPT FOR 5 DIGIT NUM
//*********************************************

printf("Please input a five digit character: \n");

// Input 5 digit number
//**************************

scanf("%d", &fiveNumberInput);

// Perform the calculations mod by 10 div by 10
//*********************************************

   num5 = fiveNumberInput%10;
   fiveNumberInput = fiveNumberInput/10;
   num4 = fiveNumberInput%10;
   fiveNumberInput = fiveNumberInput/10;
   num3 = fiveNumberInput%10;
   fiveNumberInput = fiveNumberInput/10;
   num2 = fiveNumberInput%10;
   fiveNumberInput = fiveNumberInput/10;
   num1 = fiveNumberInput%10;

// Output
//**************************

printf("\n");
printf("%d   %d   %d   %d   %d", num1, num2, num3, num4, num5);
printf("\n");
printf("\n");

//***********************************************
// EXIT
//***********************************************

return 0;
}
