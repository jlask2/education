/**************************************
*Author: Jason Laske
*Professor Raj
*CSC 401 Programming Languages
*Assignment: 3 Recursive Decent Parser
*Due Date: November 6, 2014
*Date Submitted: November 6, 2014
*Description: Recursive Descent Parser
*   (LL Parser | Top-Down Parser) Reads 
*   the next token in the input stream and
*   determines whether it is valid or not.
*   Added arithmetic and assignment semantics.
**************************************/

/**
  * CLASS USERDEFINEDEXCEPTION HANDLES USER DEFINEd 
  */
class UserDefinedException extends Exception{

   UserDefinedException(String emessage){
      super(emessage);
   }
}
