//**************************
//Jason Laske
//Daniel Rogers
//CSC311          lab9
//Date Due: 4/24/2014
//Date Submitted: 4/24/2014
//**************************

#include <stdlib.h>
#include <stdio.h>

//FUNCTION PROTOTYPES
//**************************

//my_strlen
//*************************
int my_strlen(char s[]);

//my_strcpy
//*************************
int my_strcpy(char s[], char t[]);

//my_strcat
//*************************
char* my_strcat(char s[], char t[]);

//my_strreverse
//*************************
void my_strreverse(char s[]);


//MAIN
//*************************
int main(){

int length1 = 0;
int length2 = 0;

int copy = 0;

int a;

char dummy;

char inputString1[255];
char inputString2[255];
char* stringCat;

printf("\nEnter the 1st string: ");
scanf("%s", inputString1);
scanf("%c", &dummy);

printf("\nEnter the 2nd string: ");
scanf("%s", inputString2);
scanf("%c", &dummy);


length1 = my_strlen(inputString1);
printf("\nThe length of string 1 is: %d\n", length1);

length2 = my_strlen(inputString2);
printf("\nThe length of string 2 is: %d\n", length2);

//char *iOS = (char*)malloc(sizeof(length1));
//char* inputOriginalString1 = iOS;

//iOS = &inputString1;

copy = my_strcpy(inputString1, inputString2);
  if(copy == 1){	
	printf("\nThe length of string %s is >= to string %s: %d\n", inputString1, inputString2, copy);
  }else if(copy == -1){	
	printf("\nThe length of string %s is < string %s: %d\n", inputString1, inputString2, copy );
  }else{
  	printf("\nError\n");
  }

stringCat = my_strcat(inputString1, inputString2);
printf("\nThe string: %s , is the concatenation of string1: %s , and string2: %s \n", stringCat, inputString1, 
inputString2);

my_strreverse(inputString1);

return 0;
}

//FUNCTION DECLARATION
//************************

//my_strlen
//*************************
int my_strlen(char s[]){	
	int length = 0;
	int i;
	for(i = 0; s[i] != '\0'; i++){
		/*DEBUG*///printf("i: %d, %c\n", i, s[i]);
		length++;
	}
	return length;
}

//my_strcpy
//*************************
int my_strcpy(char s[], char t[]){

   int valid = -1;
   int lengthOfS = my_strlen(s); 
   int lengthOfT = my_strlen(t);
   int i;

   if(lengthOfS >= lengthOfT){
	for(i = 0; s[i] != '\0'; i++){
		s[i] = NULL;
	}
        for(i = 0; t[i] != '\0'; i++){
		s[i] = t[i];
		/*DEBUG*///printf("%c ", s[i]);
	}
	/*DEBUG*///printf("%s \n", s);
	valid = 1;
   }else{
	valid = -1;
   }
   return valid;
}

//my_strcat
//*************************
char* my_strcat(char s[], char t[]){
   int lengthOfS = my_strlen(s);
   int lengthOfT = my_strlen(t);
   int lengthOfSAndT = (lengthOfS + lengthOfT);
   
   /*DEBUG*///printf("Length of S and T:%d\n",lengthOfSAndT);
  
   char *target = (char*)malloc(sizeof(lengthOfSAndT)); 
   /*DEBUG*///printf("Address of pointer P: %p\n", &target);

   char* u = target;
   /*DEBUG*///printf("The string u b4: %s\n", u);

   int i;
   for(i = 0; s[i] != '\0'; i++ ){
       *target = s[i];
	/*DEBUG*///printf("The char value of *target at s[%d]: %c\n", i, *target);

       target++;
   }
   //int j;
   for(i = 0; t[i] != '\0'; i++){
       *target = t[i];
	/*DEBUG*///printf("The char value of *target at t[%d]: %c\n", i, *target);

       target++;
   } 
   /*DEBUG*///printf("The string u after: %s\n", u);

   return u;
}

//my_strreverse
//*************************
void my_strreverse(char s[]){
    char *reversePTR = (char*)malloc(sizeof(s));
    char* stringReverse = reversePTR;
    
    int i;
    for(i = (my_strlen(s) - 1); i != -1; i--){
		*reversePTR = s[i];
		/*DEBUG*///printf("The char value of *reversePTR at s[%d]: %c\n", i, *reversePTR );
		reversePTR++; 
    }
    /*DEBUG*///printf("This is the string, stringReverse after: %s\n", stringReverse);
    s = stringReverse;
    printf("\nThis is the reverse of string1: %s\n", s);
}

