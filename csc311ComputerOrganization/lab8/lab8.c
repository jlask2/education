//**********************
//Jason Laske 
//Daniel Rogers
//CSC311 
//Due Date: 4/17/2014
//Date Submitted: 4/17/2014
//**********************

#include <stdlib.h>
#include <stdio.h>

int main(){

//1.) 
//**********************

printf("Step 1\n");
printf("*************************\n");

int i1 = 1; 
int i2 = 2;

double d1 = 3.0;
double d2 = 4.0;

printf("The chosen initial value for i1 is: %d\n", i1);
printf("The chosen initial value for i2 is: %d\n", i2);
printf("The chosen initial value for d1 is: %f\n", d1);
printf("The chosen initial value for d2 is: %f\n\n", d2);


printf("The address of i1 is: %p\n", &i1);
printf("The address of i2 is: %p\n", &i2);
printf("The address of d1 is: %p\n", &d1);
printf("The address of d2 is: %p\n", &d2);

printf("\n");

//2.) 
//*********************

printf("Step 2\n");
printf("*************************\n");


int *intptr1;
int *intptr2;
double *dubptr1;
double *dubptr2;

printf("The address of intptr1 is: %p\n", &intptr1);
printf("The address of intptr2 is: %p\n", &intptr2);
printf("The address of dubptr1 is: %p\n", &dubptr1);
printf("The address of dubptr2 is: %p\n", &dubptr2);

printf("\n");

//3.)
//*********************

printf("Step 3\n");
printf("*************************\n");


intptr1 = &i1;
intptr2 = &i2;

dubptr1 = &d1;
dubptr2 = &d2;

printf("The value of intptr1 is: %p\n", intptr1);
printf("The value of intptr2 is: %p\n", intptr2);
printf("The value of dubptr1 is: %p\n", dubptr1);
printf("The value of dubptr2 is: %p\n", dubptr2);

printf("\n");

//4.)
//*********************

printf("Step 4\n");
printf("*************************\n");


intptr1 = intptr2;

printf("The value of intptr1 after (intptr1 = intptr2) is: %p\n", intptr1 );

//dubptr1 = intptr1;

//printf("The value of dubptr1 = intptr1 before typcasting is: %p\n", dubptr1);

printf("The assigment of dubptr1 = intptr1 without cast causes an incompatiable type warning.\n");

dubptr1 = (double*)intptr1;

printf("The value of dubptr1 = (double*)intptr1 after typcasting is: %p\n", dubptr1);

printf("\n");

//5.)
//*********************

printf("Step 5\n");
printf("*************************\n");


intptr1 = NULL;

printf("The value of intptr1 after the assignent of intptr1 = NULL is: %p\n", intptr1); 
printf("The assignment of intptr1 = NULL causes a segmaentation fault in step 6.\n");

printf("\n");

//6.)
//*********************

printf("Step 6\n");
printf("*************************\n");


printf("The value of *intptr2 is: %d\n", *intptr2);

//printf("The value of *intptr1 is: %d\n", *intptr1);
printf("The value of *intptr1 after the assignment to NULL causes a segmentation fault here.\n");

printf("\n");

//7.)
//*********************

printf("Step 7\n");
printf("*************************\n");

*intptr2 = 100;

printf("The value of i1 after *intptr2 = 100 is: %d\n", i1);

printf("The value of i2 after *intptr2 = 100 is: %d\n", i2);

printf("\n");

//8.)
//*********************

printf("Step8\n");
printf("*************************\n");

printf("The value of intptr2++ is: %p\n", (intptr2+1));
printf("The value of dubptr2++ is: %p\n", (dubptr2+1));

printf("The value of intptr2-- is: %p\n", (intptr2-1));
printf("The value of dubptr2-- is: %p\n", (dubptr2-1));

printf("The value of *intptr2++ is: %d\n", *(intptr2+1));
printf("The value of *dubptr2++ is: %f\n", *(dubptr2+1));

printf("The value of *intptr2-- is: %d\n", *(intptr2-1));
printf("The value of *dubptr2-- is: %f\n", *(dubptr2-1));

printf("\n");

//9.)
//*********************

printf("Step 9\n");
printf("*************************\n");

intptr1 = &i1;

printf("Does intptr1 == intptr2 after intptr1 = &i1?: %d, it is false.\n", (intptr1==intptr2));
printf("Does *intptr1 == *intptr2 after intptr1 = &i1?: %d, it is false.\n", (*intptr1==*intptr2));

//i2 = (int)(intptr1);
intptr1 = &i2;

printf("Does intptr1 == intptr2 after i2 = (int)(intptr1)?: %d, it is true.\n", (intptr1==intptr2));
printf("Does *intptr1 == *intptr2 after i2 = (int)(intptr1)?: %d, it is true.\n", (*intptr1==*intptr2));

printf("\n");

//10.)
//*********************

printf("Step 10\n");
printf("*************************\n");

double *ptr;
ptr = (double*)malloc(sizeof(100.0));
*ptr = 3.1416;

printf("The value of ptr is: %p\n", ptr);
printf("The value of *ptr is: %f\n", *ptr);

printf("\n");

//11.)
//*********************

printf("Step 11\n");
printf("*************************\n");

free((void*)ptr);
ptr = (double*)malloc(sizeof(100.0));

printf("Is the value of ptr the same as it was in qustion 10?: %p , Yes it is.\n", ptr);

printf("\n");

//EXIT
//*********************

return 0;

}

