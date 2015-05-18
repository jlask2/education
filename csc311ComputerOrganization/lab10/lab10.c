//**************************
//Jason Laske
//Daniel Rogers
//CSC311  Lab10
//Date Due: 5/08/2014
//Date Submitted: 5/08/2014
//**************************

#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>

//***************************************************************
// Main
//***************************************************************

int main() 
{  	
	char input[2048];
	char output[2048];
        char c;
	int outWhite = 0;
        int outDigits = 0;
        int outLower = 0;
        int outUpper = 0;
	int i = 0;

//***************************************************************
// Prompt user to input until z 
//***************************************************************

	printf("Enter any input from the keyboard. To end input z\n\n");
	printf("(This is what the user enters)\n");
	c = getchar();
    
//***************************************************************
// Perform the calculations in the while loop
//***************************************************************
	
	while(c != 'z'){ 
		
		/*DEBUG*///printf("Inside while: \n");
		
		if(isspace(c)!= 0)
		{
			outWhite += 1;
			
			/*DEBUG*///printf("outWhite: %d\n", outWhite);		
		
		}
		else if(isdigit(c) != 0)
		{
			outDigits += 1;
		
			/*DEBUG*///printf("outDigits: %d\n", outDigits);

		}
		else if(islower(c) != 0)
		{
                        outLower += 1; 
			c = toupper(c);
		
			/*DEBUG*///printf("outLower: %d  toupper: %c \n", outLower, c);
		
		}
		else if(isupper(c) != 0)
		{
                        outUpper += 1;
			c = tolower(c);
		
			/*DEBUG*///printf("outUpper: %d  tolower: %c \n", outUpper, c);
		
		}
		else
		{
		}
		output[i] = c;
		i++;
		c = getchar();
	}
	output[i] = '\0';

//***************************************************************
// Output 
//***************************************************************
	
	printf("\n(This is what the program outputs)\n%s", output);
	
	printf("\nThe number of whitespace characters is: %d\n", outWhite);
	printf("The number of digits is: %d\n", outDigits);
	printf("The number of lowercase characters is: %d\n", outLower);
        printf("The number of uppercase characters is: %d\n\n", outUpper);

//***************************************************************
// Exit
//***************************************************************
	
	printf("Goodbye\n");
	return 0;
}

