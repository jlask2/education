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

import java.util.*;
import java.io.*;
import java.io.File.*;

/**
  * CLASS GRAMMAR 
  * CONTAINS THE METHODS TO DETERMINE VALID PARSING 
  */
class Grammar{
   
/*PSEUDO CODE DESCRIPTION    
program           -> block  $
block             -> [ ‘const’  ident = number  { ,  ident = number }  ;]  
                     [ ‘var’  ident { , ident }  ; ]
                     { ‘procedure’  ident  ;  block }
                     { statement }
statement         -> assignmentStat | procedureCallStat  | compoundStat | selectionStat | iterativeStat
assignmentStat    -> ident  :=  expression
procedureCallStat -> ‘call’  ident
compoundStat      -> ‘begin’ statement  { ; statement }  ‘end’
selectionStat     -> ‘if’ condition  ‘then’  statement ‘else’ statement 
iterativeStat     -> ‘while’  condition  ‘do’  statement
condition         -> expression  relOp  expession
relOp             -> = | != | < | > | <= | >=
expression        -> [ + | - ] term  {  ( + | - ) term    }
term              -> factor  { ( * | / ) factor }
factor            -> ident  |  number | ‘(‘ expression ‘)’
ident             -> letter { ( letter | digit ) }
number            -> digit { digit }
digit             -> 0 | 1 | …. | 9
letter            -> A | … | Z | a | … | z 
*/
   
   // Data Members of class Grammar
   private StringTokenizer token;
   private BufferedWriter buffOut;
   private BufferedReader buffIn;
   private FileWriter fileOut;
   private FileReader fileIn;
   private Object enumType;
   private Object nextT;
   private Object nextToken;
   private HashMap<String, Integer> hm = new HashMap<String, Integer>();
   private Stack<Integer> sas = new Stack<Integer>();
   
   /**
     * CONSTRUCTOR FOR GRAMMAR
     */
   public Grammar() throws UserDefinedException{
      //local variables
      Scanner sc;
      Scanner sc1;
      String line;
       
      //****HANDLE THE CASE OF INVALID SPECIFIED FILE WITH A DEFAULT FILE AND FILE CREATION(createNewFile)****//
      try{
         System.out.println("Please specify the name of the file you would like to read from or create. i.e. 'inFileX'\n");
         sc1 = new Scanner(System.in);
         String fileInName = sc1.nextLine();
         
         File inFile = new File(fileInName+".txt");
         /**DEBUG*///System.out.println(fileInName);   
         try{
            if(!(inFile.exists())){
               if(inFile.createNewFile()){
                  System.out.println("Please enter in the contents of the file. Hit enter twice in a row when you are finished: \n");
                  buffOut = new BufferedWriter(fileOut = new FileWriter(inFile));
                  sc1 = new Scanner(System.in);
                  while(!(line = sc1.nextLine()).equals("")){
                     /**DEBUG*///System.out.println(line);
                     buffOut.write(line);
                     buffOut.write("\n");
                     sc1 = new Scanner(System.in);   
                  }
                  buffOut.close();
                  fileIn = new FileReader(inFile);
               }else{
                  System.out.println("Failed to create a valid File. \n"+
                         "Reading from File in current working directory: inFileD.txt");
                  fileIn = new FileReader("inFileD.txt");         
               }
            }else{
               fileIn = new FileReader(inFile);
            }
         }catch(FileNotFoundException f){
            System.out.println("Failed to create a valid File. "+
                               "Reading from File in current working directory: inFileD.txt");
                  fileIn = new FileReader("inFileD.txt");
         }
         try{
            buffIn = new BufferedReader(fileIn);
            token = new StringTokenizer(buffIn.readLine());   
         }catch(NullPointerException n){
            System.out.println("Failed to create a valid working File. "+
                               "Reading from File in current working directory: inFileD.txt");
            token = new StringTokenizer((buffIn = new BufferedReader(fileIn = new FileReader("inFileD.txt"))).readLine());
         }
            program();//call to start the parsing
            /**DEBUG*///System.out.println("Token: " + nextToken);
      }catch(IOException io){
         io.printStackTrace(); 
      }
   }
      
   /**
     * ENUMERATED CLASS TERMINALS
     * DEFINES THE TERMINALS OF THE GRAMMAR AS ENUMRATED TYPES
     */
   private enum Terminals {
      CONST("const"), VAR("var"), PROCEDURE("procedure"), CALL("call"), IF("if"), THEN("then"), ELSE("else"), WHILE("while"), DO("do"), 
      BEGIN("begin"), END("end"), OPEN_PAREN("("), CLOSING_PAREN(")"), EQUALS("="), NOT_EQUALS("!="), LESS_THAN("<"), 
      GREATER_THAN(">"), LESS_THAN_OR_EQUAL_TO("<="), GREATER_THAN_OR_EQUAL_TO(">="),
      IMPLY_EQUALS(":="), COMMA(","), SEMI_COLON(";"), PLUS("+"), MINUS("-"), TIMES("*"), DIVIDES("/"); 
 
      // DATA MEMBER 
      private final String associatedString; 
      
      /**
        * CONSTRUCTOR FOR THE ENUMERATED TYPES
        * SETS THE GIVEN STRING TO THE INSTANCE OF THIS ENUM TYPE
        */
      Terminals(String associatedString){
         this.associatedString = associatedString;
      }
      
      /**
        * ACCESSOR METHOD FOR THE ENUMERATED TYPES 
        */
      private String getString(){
         return associatedString;
      }
   }
   
   /**
     * program -> block  $
     */
   private boolean program() throws UserDefinedException{
      /**DEBUG*///System.out.println("Inside program Block: nextToken: " + nextToken);
      nextToken = getNextToken();
      displayHashMap();
      displaySas();
      if(block()){
         if(nextToken.toString().equals("$")){      
            /**DEBUG*///System.out.println("Inside program Block: true");
            displayHashMap();
            displaySas();
            System.out.println("Valid program: The program parsed successfully!");
            return true;
         }else{
            System.out.println("Invalid program: No end of program symbol '$'-> " + nextToken);
            return false;
         }
      }else{
         System.out.println("Invalid program: No valid block -> " + nextToken);
         return false;
      }
   }
   
   /**
     * block -> [ ‘const’  ident = number  { ,  ident = number }  ;]  
     *          [ ‘var’  ident { , ident }  ; ]
     *          { ‘procedure’  ident  ;  block }
     *          { statement }
     */
   private boolean block() throws UserDefinedException{
      /**DEBUG*///System.out.println("Inside block Block: nextToken: " + nextToken);
      Object varHolder = null;
      Object resultHolder = null;
      boolean validBlock = true;
      
      //[ ‘const’  ident = number  { ,  ident = number }  ;]
      if(nextToken == Terminals.CONST){
         do{
            nextToken = getNextToken();
            varHolder = nextToken;
            if(ident()){
               if(nextToken == Terminals.EQUALS){
                  nextToken = getNextToken();
                  resultHolder = nextToken;
                  if(number()){
                      hm.put(varHolder.toString(), new Integer(resultHolder.toString()));
                      System.out.println("Assigning "+hm.get(varHolder.toString())+" to "+varHolder.toString()+" as a CONSTANT\n");                          
                  }else{
                     return false;
                  }             
               }else{
                  return false;
               } 
            }else{
               return false;
            }
         }while(nextToken == Terminals.COMMA);
         if(nextToken == Terminals.SEMI_COLON){
            nextToken = getNextToken();
         }else{
            return false;
         }
      }
      
      //[ ‘var’  ident { , ident }  ; ]
      if(nextToken == Terminals.VAR){
         do{
            nextToken = getNextToken();
            varHolder = nextToken;
            if(ident()){
               //
            }else{
               return false;
            }               
         }while(nextToken == Terminals.COMMA);
         if(nextToken == Terminals.SEMI_COLON){
           nextToken = getNextToken();
         }else{
            return false;
         }
      }
      
      //{ ‘procedure’  ident  ;  block }
      while(nextToken == Terminals.PROCEDURE){     
            nextToken = getNextToken();
            varHolder = nextToken;
            if(ident()){
               if(nextToken == Terminals.SEMI_COLON){
                  nextToken = getNextToken();
                  if(block()){
                  }else{
                     return false;
                  }
               }else{
                  return false;
               }
            }else{
               return false;
            }
      }
         
      //{ statement }
      while((!(nextToken.toString().equals("$")))&&(validBlock == true)){
         if(statement()){
            /*DEBUG*///System.out.println("Inside block-statement-true\n");
            validBlock = true;
         }else{
            /*DEBUG*///System.out.println("Inside block-statement-false\n");
            validBlock = false;
            return false;
         }
      }
      //Note: In this implementation of my parser, a valid empty block is considered 
      //to have at least the terminating symbol '$' otherwise it is considered a 
      //invalid block   
      if(validBlock == true){ 
         /**DEBUG*///System.out.println("Inside block Block: statement() true");
         return true;
      }else{
         /**DEBUG*///System.out.println("Inside block Block: statement() false");
         System.out.println("Not a block: Not a valid input -> " + nextToken);
         return false;
      }
   }
   
   /**
     * statement -> assignmentStat | procedureCallStat  | compoundStat | selectionStat | iterativeStat
     */
   private boolean statement() throws UserDefinedException{
      /**DEBUG*///System.out.println("Inside statement Block: nextToken: " + nextToken);
      if(assignmentStat()){
         return true;
      }
      if(procedureCallStat()){
         return true;
      }
      if(compoundStat()){
         return true;
      }
      if(selectionStat()){
         return true;
      }
      if(iterativeStat()){
         return true;
      }
      if(nextToken.toString().equals("$")){
         /**DEBUG*///System.out.println("Inside statement Block: true");
         return true;
      }
      System.out.println("Not a statement: Not a valid assignmentStat, procedureCallStat, compoundStat, seletionStat, iterativeStat or $ -> " + nextToken);
      return false;
   }
   
   /**
     * assignmentStat -> ident  :=  expression
     */
   private boolean assignmentStat() throws UserDefinedException{
      /**DEBUG*///System.out.println("Inside assignmentStat Block: nextToken: " + nextToken);
      Object varHolder = null;
      Object resultHolder = null;
      varHolder = nextToken;      
      if(ident()){
         if(nextToken == Terminals.IMPLY_EQUALS){
            nextToken = getNextToken();
            if(expression()){
               /**DEBUG*///System.out.println("Inside assignmentStat Block: true");
               resultHolder = sas.pop();
               hm.put(varHolder.toString(), new Integer(resultHolder.toString()));
               System.out.println("AssignemntStat: Mapped "+varHolder+" to "+resultHolder+" in the HashMap\n");
               return true;
            }else{
               System.out.println("Not a assignmentStat: Not a valid expression -> " + nextToken);
               return false;
            }
         }else{
            System.out.println("Not a assignmentStat: No valid IMPLY_EQUALS -> " + nextToken);
            return false;
         }
      }else{
         System.out.println("Not a assignmentStat: No valid ident -> " + nextToken);
         return false;
      }
   }
   
   /**
     * procedureCallStat -> ‘call’  ident
     */
   private boolean procedureCallStat() throws UserDefinedException{
      /**DEBUG*///System.out.println("Inside procedureCallStat Block: nextToken: " + nextToken);
      if(nextToken == Terminals.CALL){
         nextToken = getNextToken();
         if(ident()){
            /**DEBUG*///System.out.println("Inside procedureCallStat Block: true");
            return true;
         }else{
            System.out.println("Not a procedureCallStat: No valid ident -> " + nextToken);
            return false;   
         }
      }else{
         System.out.println("Not a procedureCallStat: No valid CALL -> " + nextToken);
         return false;
      }
   }
   
   /**
     * compoundStat -> ‘begin’ statement  { ; statement }  ‘end’
     */
   private boolean compoundStat() throws UserDefinedException{
      /**DEBUG*///System.out.println("Inside compoundStat Block: nextToken: " + nextToken);
      if(nextToken == Terminals.BEGIN){
         nextToken = getNextToken();
         do{
            if(nextToken == Terminals.SEMI_COLON){
               /**DEBUG*///System.out.println("Inside compoundStat Block: nextToken: " + nextToken);
               nextToken = getNextToken();
               /**DEBUG*///System.out.println("Inside compoundStat Block: nextToken: " + nextToken);
            }
            if(statement()){
               /**DEBUG*///System.out.println("Inside compoundStat Block: nextToken: " + nextToken);
            }else{
               return false;
            }
         }while(nextToken == Terminals.SEMI_COLON);
         /**DEBUG*///System.out.println("Inside compoundStat Block: a7 while");
         if(nextToken == Terminals.END){
            /**DEBUG*///System.out.println("Inside compoundStat Block: inside if");
            nextToken = getNextToken();
            /**DEBUG*///System.out.println("Inside compoundStat Block: inside if next");
            return true;
         }else{
            System.out.println("Not a compoundStat: No valid END -> " + nextToken);
            return false;
         }
      }else{
         System.out.println("Not a compoundStat: No valid BEGIN -> " + nextToken);
         return false;
      }
   }
   
   /**
     *selectionStat -> ‘if’ condition  ‘then’  statement ‘else’ statement 
     */
   private boolean selectionStat() throws UserDefinedException{
   /**DEBUG*///System.out.println("Inside selectionStat Block: nextToken: " + nextToken);      
      if(nextToken == Terminals.IF){
         nextToken = getNextToken();
         if(condition()){
            if(nextToken == Terminals.THEN){
               nextToken = getNextToken();
               if(statement()){
                  if(nextToken == Terminals.ELSE){
                     nextToken = getNextToken();
                     if(statement()){
                        /**DEBUG*///System.out.println("Inside selectionStat Block: true");       
                        
                        return true;
                     }else{
                        System.out.println("Not a selectionStat: Not a valid statement -> " + nextToken);
                        return false;
                     }
                  }else{
                     System.out.println("Not a selectionStat: No valid ELSE -> " + nextToken);
                     return false;
                  }
               }else{
                  System.out.println("Not a selectionStat: Not a valid statement -> " + nextToken);
                  return false;
               }
            }else{
               System.out.println("Not a selectionStat: No valid THEN -> " + nextToken);
               return false;
            }
         }else{
            System.out.println("Not a selectionStat: Not a valid condition -> " + nextToken);
            return false;
         }
      }else{
         System.out.println("Not a selectionStat: No valid IF -> " + nextToken);
         return false;
      }
   }
   
   /**
     * iterativeStat -> ‘while’  condition  ‘do’  statement
     */
   private boolean iterativeStat() throws UserDefinedException{
   /**DEBUG*///System.out.println("Inside iterativeStat Block: nextToken: " + nextToken);
   boolean doStatement;   
      if(nextToken == Terminals.WHILE){
         nextToken = getNextToken();
         if(condition()){
            if(nextToken == Terminals.DO){
               nextToken = getNextToken();
               if(hm.get("condition") == 1){
                  doStatement = true;
                  /**DEBUG*///System.out.println("Inside iterativeStat Block: doStatement: true");
               }else{
                  doStatement = false;
                  /**DEBUG*///System.out.println("Inside iterativeStat Block: doStatement: true");
               }
               if(statement()){
                  
                  /**DEBUG*///System.out.println("Inside iterativeStat Block: true");
                  return true;
               }else{
                  System.out.println("Not a iterativeStat: Not a valid statement -> " + nextToken);
                  return false;   
               }
            }else{
               System.out.println("Not a iterativeStat: No valid DO -> " + nextToken);
               return false;
            }
         }else{
            System.out.println("Not a iterativeStat: Not a valid condition -> " + nextToken);
            return false;
         }
      }else{
         System.out.println("Not a iterativeStat: No valid WHILE -> " + nextToken);         
         return false;
      }
   }
   
   /**
     * condition -> expression  relOp  expession
     */
   private boolean condition() throws UserDefinedException{
   /**DEBUG*///System.out.println("Inside condition Block: nextToken: " + nextToken);
   Object varHolder = null;
   int exp1;
   int exp2;
      if(expression()){
      exp1 = sas.pop();
      varHolder = nextToken;
         if(relOp()){
            if(expression()){
               exp2 = sas.pop();
               /**DEBUG*///System.out.println("Inside condition Block: true");
               switch(varHolder.toString()){
                  case "EQUALS":
                     if(exp1 == exp2){
                        /**DEBUG*///System.out.println("Inside condition Block: EQUALS: 1");
                        hm.put("condition", 1);
                     }else{
                        /**DEBUG*///System.out.println("Inside condition Block: EQUALS: 0");
                        hm.put("condition", 0);
                     }
                     break;
                  case "NOT_EQUALS":
                     if(exp1 != exp2){
                        /**DEBUG*///System.out.println("Inside condition Block: NOT_EQUALS: 1");
                        hm.put("condition", 1);
                     }else{
                        /**DEBUG*///System.out.println("Inside condition Block: NOT_EQUALS: 0");
                        hm.put("condition", 0);
                     }
                     break;
                  case "LESS_THAN":
                     if(exp1 < exp2){
                        /**DEBUG*///System.out.println("Inside condition Block: LESS_THAN: 1");
                        hm.put("condition", 1);
                     }else{
                        /**DEBUG*///System.out.println("Inside condition Block: LESS_THAN: 0");
                        hm.put("condition", 0);
                     }
                     break;
                  case "GREATER_THAN":
                     if(exp1 > exp2){
                        /**DEBUG*///System.out.println("Inside condition Block: GREATER_THAN: 1");
                        hm.put("condition", 1);
                     }else{
                        /**DEBUG*///System.out.println("Inside condition Block: GREATER_THAN: 0");
                        hm.put("condition", 0);
                     }
                     break;
                  case "LESS_THAN_OR_EQUAL_TO":
                     if(exp1 <= exp2){
                        hm.put("condition", 1);
                        /**DEBUG*///System.out.println("Inside condition Block: LESS_THAN_OR_EQUAL_TO: 1");
                     }else{
                        hm.put("condition", 0);
                        /**DEBUG*///System.out.println("Inside condition Block: LESS_THAN_OR_EQUAL_TO: 0");
                     }
                     break;
                  case "GREATER_THAN_OR_EQUAL_TO":
                     if(exp1 >= exp2){
                        hm.put("condition", 1);
                        /**DEBUG*///System.out.println("Inside condition Block: GREATER_THAN_OR_EQUAL_TO: 1");
                     }else{
                        hm.put("condition", 0);
                        /**DEBUG*///System.out.println("Inside condition Block: GREATER_THAN_OR_EQUAL_TO: 0");
                     }
                     break;
                  default:
                     /**DEBUG*/System.out.println("Inside condition Block: default");
                     break; 
               }
               return true;
            }else{
               System.out.println("Not a condition: Not a valid expression -> " + nextToken);               
               return false;
            }
         }else{
            System.out.println("Not a condition: Not a valid relOp -> " + nextToken);            
            return false;
         }
      }else{
         System.out.println("Not a condition: Not a valid expression -> " + nextToken);      
         return false;
      }
   }
   
   /**
     * relOp -> = | != | < | > | <= | >=
     */
   private boolean relOp(){
   /**DEBUG*///System.out.println("Inside relOp Block: nextToken: " + nextToken);
      if((nextToken == Terminals.EQUALS)||(nextToken == Terminals.NOT_EQUALS)||
         (nextToken == Terminals.LESS_THAN)||(nextToken == Terminals.GREATER_THAN)||
         (nextToken == Terminals.LESS_THAN_OR_EQUAL_TO)||(nextToken == Terminals.GREATER_THAN_OR_EQUAL_TO)){
         nextToken = getNextToken();
         /**DEBUG*///System.out.println("Inside relOp Block: true");
         return true;
      }else{
         System.out.println("Not a relOp: No valid EQUALS '=', NOT_EQUALS '!=', LESS_THAN '<', GREATER_THAN '>', LESS_THAN_OR_EQUAL_TO '<=' or GREATER_THAN_OR_EQUAL_TO '>=' -> " + nextToken);
         return false;
      }
   }
   
   /**
     * expression -> [ + | - ] term  {  ( + | - ) term  }
     */
   private boolean expression() throws UserDefinedException{
   /**DEBUG*///System.out.println("Inside expression Block: nextToken: " + nextToken);
      Object varHolder = null;
      Object resultHolder = null;
      boolean unaryMinus = false;
      int operand1;
      int operand2;
      
      varHolder = nextToken;
      if(nextToken == Terminals.PLUS){
         nextToken = getNextToken();
      }
      if(nextToken == Terminals.MINUS){
         nextToken = getNextToken();
         resultHolder = nextToken;
         unaryMinus = true;         
      }
      if(term()){
         /*if((nextToken == Terminals.PLUS) || (nextToken == Terminals.MINUS))
          statement added here to handle the case of either a single term 
          with or without a unary operator or more than one term indicating a 
          single or multiple binary operation(s)*/ 
         if((nextToken == Terminals.PLUS) || (nextToken == Terminals.MINUS)){
            do{
               boolean plusMinus = true;//whether a unary + or no unary operation occurs  
               
               if(nextToken == Terminals.MINUS){
                  plusMinus = false;//check for a unary - operation for the first term only
               }
               nextToken = getNextToken();
               if(term()){
                  operand2 = sas.pop();
                  if(unaryMinus == true){ 
                     operand1 = -(sas.pop());//make negative the first term if it has a - unary operation on it
                     /*DEBUG*///System.out.println("-operand1: "+operand1);
                     unaryMinus = false;//check once for the duration of this methods life cycle
                  }else{
                     operand1 = sas.pop();
                  }
                  if(plusMinus == false){
                     //<TopOfStack> = operand1 - operand2;
                     sas.push(new Integer(operand1 - operand2));
                     System.out.println("boolean plusMinus = "+plusMinus+"| result of "+operand1+" - "+operand2+" = "+sas.peek()+"\n");
                  }else{
                     //<TopOfStack> = operand1 + operand2;
                     sas.push(new Integer(operand1 + operand2));
                     System.out.println("boolean plusMinus = "+plusMinus+"| result of "+operand1+" + "+operand2+" = "+sas.peek()+"\n");
                  }
               }else{
                  return false;
               }
            }while((nextToken == Terminals.PLUS) || (nextToken == Terminals.MINUS));
         }else{//this expression has only a single term 
            if(unaryMinus == true){ 
               operand1 = -(sas.pop());
               /*DEBUG*///System.out.println("-operand1: "+operand1);
               unaryMinus = false;
            }else{
               operand1 = sas.pop();
            }
            //<TopOfStack> = operand1 - operand2;
            sas.push(new Integer(operand1));
            System.out.println("Unary operation only: "+sas.peek()+"\n");
         }
         /**DEBUG*///System.out.println("Inside expression Block: true");
         return true;
      }else{
         System.out.println("Not a expression: Not a valid term -> " + nextToken);
         return false;
      }          
   }
   
   /**
     * term -> factor  { ( * | / ) factor }
     */
   private boolean term() throws UserDefinedException{
   /**DEBUG*///System.out.println("Inside term Block: nextToken: " + nextToken);
      Object varHolder = null;
      Object resultHolder = null;
      boolean timesDivide = true;
      int operand1;
      int operand2;
      varHolder = nextToken;
      do{
         if((nextToken == Terminals.TIMES) || (nextToken == Terminals.DIVIDES)){
            if(nextToken == Terminals.DIVIDES){
               timesDivide = false;
            }
            nextToken = getNextToken();
            resultHolder = nextToken;
            if(factor()){
               operand2 = sas.pop();
               operand1 = sas.pop();
               if(operand2 == 0){
                  /**DEBUG*///System.out.println("Division by zero is undefined\n");
                  throw new UserDefinedException("Division by zero is undefined\n");
                  //throw new DivisionByZeroException();      
               }
               if(timesDivide == false){
                  //<TopOfStack> = operand1 / operand2;
                  sas.push(new Integer(operand1 / operand2));
                  System.out.println("boolean timesDivide = "+timesDivide+"| result of "+operand1+" / "+operand2+" = "+sas.peek()+"\n");
               }else{
                  //<TopOfStack> = operand1 * operand2;
                  sas.push(new Integer(operand1 * operand2));
                  System.out.println("boolean timesDivide = "+timesDivide+"| result of "+operand1+" * "+operand2+" = "+sas.peek()+"\n");
               }                
            }else{
               System.out.println("Not a term: No valid factor -> " + nextToken);
               return false;
            }
         }else{
            if(factor()){
               
            }else{
               return false;
            }
         }
      }while((nextToken == Terminals.TIMES) || (nextToken == Terminals.DIVIDES));
      /**DEBUG*///System.out.println("Inside term Block: true");
      return true;
   }
   
   /**
     * factor -> ident  |  number | ‘(‘ expression ‘)’
     */
   private boolean factor() throws UserDefinedException{
   /**DEBUG*///System.out.println("Inside factor Block: nextToken: " + nextToken);      
      if(ident() || number()){
         /**DEBUG*///System.out.println("Inside factor Block: ident() || number() true");
         return true;
      }else if(nextToken == Terminals.OPEN_PAREN){
            nextToken = getNextToken();
            if(expression()){
               if(nextToken == Terminals.CLOSING_PAREN){
                  nextToken = getNextToken();
                  /**DEBUG*///System.out.println("Inside factor Block: expression() true");
                  return true;
               }else{
                  System.out.println("Not a factor: No valid CLOSING_PAREN ')' -> " + nextToken);
                  return false;
               }
            }else{
               System.out.println("Not a factor: Not a valid expression -> " + nextToken);
               return false;
            }
      }else{
         System.out.println("Not a factor: No valid ident or number or OPEN_PAREN '(' -> " + nextToken);
         return false;
      }
   }
   
   /**
     * ident -> letter { ( letter | digit ) }
     * letter -> A | … | Z | a | … | z
     */
   private boolean ident(){
   /**DEBUG*///System.out.println("Inside identletter Block: nextToken: " + nextToken + 
   /**DEBUG*///" | nextToken == setEnumType(nextToken) :" + (nextToken == Terminals.values()));
      for(Terminals t : Terminals.values()){
         if(nextToken == t){
            /**DEBUG*///System.out.println("1Inside identletter Block: true | nextToken: " + nextToken);
            return true;
         }
      }
      if(nextToken.toString().matches("^([a-zA-Z][a-zA-Z0-9]*)$")){
         /**DEBUG*///System.out.println("2b4Inside identletter Block: true | nextToken: " + nextToken);   
         if(hm.containsKey(nextToken.toString())){
            System.out.println("The key "+nextToken.toString()+" exists in the HashMap and has the value: "+hm.get(nextToken.toString())+"\n");
         }else{
            hm.put(nextToken.toString(), (Integer)(new Double(Math.random()*100).intValue()));
            System.out.println("Arbitrarily initialize ident " + nextToken.toString() + " as " + hm.get(nextToken.toString()) + "\n");
         }
         sas.push(hm.get(nextToken.toString()));
         nextToken = getNextToken();
         /**DEBUG*///System.out.println("3Inside identletter Block: true | nextToken: " + nextToken);
         return true;
      }else{
         /**DEBUG_SC*///System.out.println("Invalid identletter: Does not match regex ^([a-zA-Z][a-zA-Z0-9]*)$ -> " + nextToken);
         return false;                                        
      }
   }
   
   /**
     * number -> digit { digit }
     * digit -> 0 | 1 | …. | 9
     */
   private boolean number(){      
      /**DEBUG*///System.out.println("Inside numberdigit Block: nextToken: " + nextToken);
      if(nextToken.toString().matches("^([0-9]*)$")){
         sas.push(new Integer(nextToken.toString()));
         System.out.println("Number: " + nextToken.toString() + " pushed onto stack as " + sas.peek()+"\n");
         nextToken = getNextToken();
         /**DEBUG*///System.out.println("Inside numberdigit Block: true");
         return true;
      }else{
         /**DEBUG_SC*///System.out.println("Invalid number: Does not match regex ^([0-9]*)$ -> " + nextToken);
         return false;   
      }
   }
   
   /**
     * MUTATOR METHOD FOR SETTING THE ENUMTYPE TO NEXTTOKEN
     */
   protected Object setEnumType(Object token){
      for(Terminals t : Terminals.values()){
         if(token.toString().equals(t.getString())){
            /*DEBUG**///System.out.println("b4 token = t | Inside setEnumType(token): " + token + " t: " + t);
            token = t;
            /*DEBUG**///System.out.println("a7 token = t | Inside setEnumType(token): " + token + " t: " + t);
            return token;
         }
      }
      /*DEBUG**///System.out.println("Outside 4loop Inside setEnumType(token): " + token);
      return null;   
   }
   
   /**
     * MUTATOR METHOD FOR UPDATING AND SETTING NEXTTOKEN
     */
   protected void setNextToken(Object token){
      nextToken = token;
   }
   
   /**
     * DISPLAY METHOD OUTPUTING THE HASHMAP TO THE CONSOLE
     */
   private void displayHashMap(){
         Object[] hmk = hm.keySet().toArray();
         Object[] hmv = hm.values().toArray();
         int hmkLength = 0;
         int hmvLength = 0;
         String hmkString = "";
         String hmvString = "";
         System.out.println("-----------------------");
         System.out.println("| HashMap Size: "+hm.size()+"     |");
         System.out.println("-----------------------");
         System.out.println("| Key      | Value    |");
         for(int i = 0; i < hm.keySet().size(); i++){
            System.out.println("-----------------------");
            hmkLength = hmk[i].toString().length();
            hmvLength = hmv[i].toString().length();
            hmkString = hmk[i].toString();
            hmvString = hmv[i].toString();
            for(int k = 0; k < (8 - hmkLength); k++){
               hmkString += " ";
            }
            for(int v = 0; v < (8 - hmvLength); v++){
               hmvString += " ";
            } 
            System.out.println("| "+hmkString+" | "+hmvString+" |");
         }
         System.out.println("-----------------------\n");     
   }
   
   /**
     * DISPLAY METHOD OUTPUTING THE SAS STACK TO THE CONSOLE
     */
   private void displaySas(){
      Object[] sasa = sas.toArray();
      int sasLength = 0;
      String sasString = "";
      System.out.println("-----------------------");
      System.out.println("| Stack Size: "+sas.size()+"       |");
      System.out.println("-----------------------");
      System.out.println("| Top of SAS Stack    |");
      for(int i = (sas.size()-1); i >= 0; i--){
         System.out.println("-----------------------");
         sasLength = sasa[i].toString().length();
         sasString = sasa[i].toString();
         for(int v = 0; v < (19 - sasLength); v++){
            sasString += " ";
         } 
         System.out.println("| "+sasString+" |");
      }
      System.out.println("-----------------------\n");
   }
   
   /**
     * METHOD GETNEXTTOKEN 
     * RETURNS THE NEXT TOKEN IN TOKENIZER LINE IF NOT NULL ELSE RETURNS A 
     * "NOT VALID" STRING, SETS UP ENUM TYPE 
     */
   private Object getNextToken(){
     Object t = null;
     try{
         if((t = token.nextToken()) != null){
            enumType = setEnumType(t);
               if(enumType == null){
                  /**DEBUG*///System.out.println("Inside getNextToken: enumType: " + enumType);
                  enumType = "USER_DEFINED";
                  /**DEBUG*///System.out.println("Inside getNextToken: enumType: " + enumType);
               }
               setNextToken(t);
            if(!(enumType.equals("USER_DEFINED"))){
               /**DEBUG*///System.out.println("Inside getNextToken: enumType: " + enumType);
               setNextToken(enumType);
               t = nextToken;
            }
            /*DEBUG**///System.out.println("getNextToken: " + grammar.getNextToken() + " | enumType: " + enumType + " | enumType.equals(USER_DEFINED): " + enumType.equals("USER_DEFINED"));
            return t;
         }
      }catch(NoSuchElementException n){
        try{
         if((token = new StringTokenizer(buffIn.readLine())) != null){
            t = token.nextToken();
            enumType = setEnumType(t);
               if(enumType == null){
               /**DEBUG*///System.out.println("Inside nosuchelem getNextToken: enumType: " + enumType);
                  enumType = "USER_DEFINED";
               /**DEBUG*///System.out.println("Inside nosuchelem getNextToken: enumType: " + enumType);   
               }
               setNextToken(t);
            if(!(enumType.equals("USER_DEFINED"))){
               /**DEBUG*///System.out.println("Inside nosuchelem getNextToken: enumType: " + enumType);
               setNextToken(enumType);
               t = nextToken;
            }
            /*DEBUG**///System.out.println("getNextToken: "+t.toString() + "| enumType: " + enumType.toString());
            return t;  
         }
        }catch(IOException io){
            io.printStackTrace();
        }catch(NullPointerException np){
            /**DEBUG*///System.out.println("Inside NP");
            //np.printStackTrace();
        }
      }catch(NullPointerException npe){
            /**DEBUG*///System.out.println("Inside NPE");
            npe.printStackTrace();
      }
      t = "NOT_VALID";
      return t;
   }
}