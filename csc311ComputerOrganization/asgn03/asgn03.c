//***********************************************
//Jason Laske 
//Daniel Rogers
//CSC 311
//Assignment 3 
//Due Date: 4/10/2014
//Submitted: 4/10/2014
//***********************************************

#include <stdlib.h>
#include <stdio.h>
#include <string.h>


//************************************************
//PROTOTYPE DECLARATION
//************************************************

//gcd 
//************************************************  
int gcd(int e, int f);


//main
//************************************************

int main(){

//************************************************
//GLOBAL VARIABLE DECLARATION
//************************************************

char d;   //dummy
int k = 0;

//************************************************
//1.) LOOP FOR THE 2 INPUTS 
//************************************************

while(k == 0){

//Local variables | re-initialize our variables
//************************************************

	int a = 0;	
	int b = 0;
	int c = 0;

//Input
//************************************************

	printf("Please enter a positve integer. Enter 0 to quit: ");
	
	scanf("%d", &a);
	scanf("%c", &d);


//Control structure
//*************************************************

	if(a == 0){
		k = 1;
	}else if(a < 0){
		printf("Error! This is not a positive integer.\nPlease try again.\n");
	}else if(a > 0){
		printf("Please enter the second positive integer: ");
		scanf("%d", &b);
		scanf("%c", &d);
		while(b < 1){
			printf("Error! This is not a positive integer.\n");
			printf("Please enter the second positive integer: ");
			scanf("%d", &b);
			scanf("%c", &d);
		}
		if(b > 0){
			printf("The gcd of %d and %d is %d.\n", a, b, gcd(a, b));
		}
	}else{
		printf("Error\n");
	}
 }//end while

//**********************************************************
//2.) EXIT
//**********************************************************

printf("Have a nice day.\n");
return 0;
}


//**********************************************************
//FUNCTION DECLARATION
//**********************************************************


//gcd function
//**********************************************************
int gcd(int e, int f){
	int g;
	while(e != 0){
		g = e;
		e = f%e;
		f = g;
	}	
	return f;
}

