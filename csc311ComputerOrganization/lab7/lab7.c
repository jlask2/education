//*****************************************
//Jason Laske 
//Daniel Rogers
//CSC 311 
//Due Date: 4/10/2014
//Submitted: 4/10/2014
//*****************************************

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(){

//*****************************************
//VARIABLE DECLARATION
//*****************************************

char a;
char b;
char c;
char d;
char e;
char f;

int k = 0;

//******************************************
//1.) PROMPT FOR BASE OF INPUT 
//******************************************
while(k == 0){

	int i = 0;	
	
	char str[32];
	int bin = 0;
	int j = 0;

	printf("Base of input integer: Enter b 	for binary, d for decimal, h for hexadecimal and o for octal: \n");

//Input
//*******************
scanf("%c", &a);
scanf("%c", &d);


//Control structure
//*******************
printf("Enter the number: \n");

if(a == 'b'){
	scanf("%s", &str);
	scanf("%c", &d);
	while(str[j] != '\0')
        {
                bin = bin * 2 + str[j] - '0';
		j++;
        }
}else if(a == 'd'){
	scanf("%d", &i);
	scanf("%c", &d);
}else if(a == 'h'){
	scanf("%x", &i);
	scanf("%c", &d);
}else if(a == 'o'){
	scanf("%o", &i);
	scanf("%c", &d);
}else{
	printf("Error1\n");
}

//******************************************
//2.) PROMPT FOR BASE OF OUTPUT / OUTPUT
//******************************************

printf("Enter the base of the output(d, h or o): ");

scanf("%c", &e);
scanf("%c", &d);

if(a == 'b'){
	printf("The integer %s in binary is ", str);
}else if(a == 'd'){
	printf("The integer %d in decimal is ", i);
}else if(a == 'h'){
	printf("The integer %x in hexadecimal is ", i);
}else if(a == 'o'){
	printf("The integer %o in octal is ", i);
}else{
	printf("Error2\n");
}

if(e == 'd'){
	if(a == 'b'){
                printf("%d in decimal.\n", bin);
        }else{
	printf("%d in decimal.\n", i);
	}
}else if(e == 'h'){
 	if(a == 'b'){
                printf("%x in hexadecimal.\n", bin);
        }else{
		printf("%x in hexadecimal.\n", i);
	}
}else if(e == 'o'){
	if(a == 'b'){
                printf("%o in octal.\n", bin);
        }else{
		printf("%o in octal.\n", i);
	}
}else{
	printf("Error3\n");
}

//*******************************************
//3.) CONTINUE? 
//*******************************************

printf("Do you wish to do another? (Y or N): ");

scanf("%c", &f);
scanf("%c", &d);

if(f != 'Y'){
	k = 1;
}

}//end while

//*******************************************
//4.)EXIT
//*******************************************

printf("Thank you, have a nice day.\n\n");

return 0;
}
