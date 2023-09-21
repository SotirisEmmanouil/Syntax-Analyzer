import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
  Syntax analyzer for a subset of the Pascal language
  
  LIST OF METHODS USED TO PERFORM THE SYNTAX ANALYSIS:
  	
  	PROGRAM()
  	IDENT()
  	SEMICOLON()
  	EXPR()
  	MOREVAR()
  	BEGIN()
  	MAINEXPR()
  	BEGINCOMMENT()
  	ENDCOMMENT()
  	END()
  	ENDRESULT()
  	DIV()
  	TIMES()
  	MOD()
  	MINUS()
  	PLUS()
  	ASSIGN_OP()
  	IF()
  	THEN()
  	VAR()
  	COLON()
  	INTEGER()
  	CONST()
  	EQL()
  	NUMLIT()
  	WRITE()
  	LPAREN()
  	SINGQUO()
  	RPAREN()
  	READ() 	 
  	FOR()
  	TO()
  	WHILE()  
  	GTR()
  	LSS() 
  	DO()  
 */

public class SyntaxAnalyzer {
	
	static int i = 0; 
	static List<Token> tokens = null;
	String tokenString = String.format("%-20s", tokens.get(i));
	
	public static class Token {
       
    	private Type t;
        private String c; 

        public Token(Type t, String c) {		//creating a token using OOP
            this.t = t;
            this.c = c;
        }
        
        public String toString() {			//toString method so tokens can be printed
            return t.toString();
        }
    
        public String getLexeme() {			//get the lexeme attached to the token
        	return c;
        }
       
    }

       public static List<Token> lexicallyAnalyze(String input) {
       
       List<Token> result = new ArrayList<Token>();
        
        String[] lexemes = input.split("\\s+"); // Split input into tokens using whitespace as separator
        
        for (String lexeme : lexemes) {
           
         
               if(lexeme.startsWith("(")){
            	   char secondLetter = lexeme.charAt(1);
            	  
            	   if(Character.isLetter(secondLetter) && lexeme.endsWith(";") && lexeme.contains(")")) {		
            		 int index = lexeme.indexOf(')'), index2 = lexeme.indexOf(';');
            		 result.add(new Token(Type.LPAREN, lexeme.substring(0,1)));
            		 result.add(new Token(Type.IDENT, lexeme.substring(1,index)));
            		 result.add(new Token(Type.RPAREN, lexeme.substring(index,index2)));
            		 result.add(new Token(Type.SEMICOLON, lexeme.substring(index2)));
            	   }
            	   else if (Character.isLetter(secondLetter)) {
              		 result.add(new Token(Type.LPAREN, lexeme.substring(0,1)));
              		 result.add(new Token(Type.IDENT, lexeme.substring(1)));
              	   }
            	   else if(lexeme.charAt(1) == '*') {
                    result.add(new Token(Type.BEGCOMMENT, lexeme));
            	   }
            	   
            	   else if(lexeme.charAt(1) == '“') {
            		   result.add(new Token(Type.LPAREN, lexeme.substring(0,1)));
                       result.add(new Token(Type.QUOTE, lexeme.substring(1,2)));
                       
                       if(lexeme.substring(1).matches("^[A-I].*$")){
                    	 result.add(new Token(Type.QUOTE, lexeme));
                    	 
                    	 int index = lexeme.indexOf('“'), index2 = lexeme.indexOf(')');
                    	 
                    	 result.add(new Token(Type.IDENT, lexeme.substring(1,index)));
                    	 result.add(new Token(Type.QUOTE, lexeme.substring(index,index2)));
                    	 result.add(new Token(Type.RPAREN, lexeme.substring(index2)));
                    	 
                       }
               	   }
                	else 
                         result.add(new Token(Type.LPAREN, lexeme));
                }
               else if(lexeme.endsWith("');") && lexeme.matches("^[A-I].*$")){
        	    	int i = lexeme.indexOf(")"), j = lexeme.indexOf(";"), k = lexeme.indexOf("'");
       	    	  result.add(new Token(Type.IDENT, lexeme.substring(0,k)));
       	    	  result.add(new Token(Type.SINQUO, lexeme.substring(k,i)));
       	    	  result.add(new Token(Type.RPAREN, lexeme.substring(i,j)));
       	    	  result.add(new Token(Type.SEMICOLON, lexeme.substring(j)));
              }
               
               else if(lexeme.endsWith(";")) {
            	  
                 if(lexeme.substring(0,lexeme.length()-1).matches("-?\\d+")) {		//using regex check if its a number before the semicolon
            		   result.add(new Token(Type.NUMLIT, lexeme.substring(0,lexeme.length()-1)));
            		   result.add(new Token(Type.SEMICOLON, lexeme.substring(lexeme.length()-1)));
                 }
                 else if(lexeme.contains("INTEGER") || lexeme.contains("integer")) {
        			   result.add(new Token(Type.INTEGER, lexeme.substring(0,lexeme.length()-1)));
           		       result.add(new Token(Type.SEMICOLON, lexeme.substring(lexeme.length()-1)));
        		  }
                 
                 else if(lexeme.substring(0,lexeme.length()-1).matches("^[A-I].*$")) {		//using regex check if the string 
          		       result.add(new Token(Type.IDENT, lexeme.substring(0,lexeme.length()-1)));
          		       result.add(new Token(Type.SEMICOLON, lexeme.substring(lexeme.length()-1)));
                 }
                                 
          		  else if(lexeme.startsWith("'")) {
          			 result.add(new Token(Type.SINQUO, lexeme.substring(0,1)));
          			 result.add(new Token(Type.RPAREN, lexeme.substring(1,2)));
          			 result.add(new Token(Type.SEMICOLON, lexeme.substring(2,3)));
                 }
                 
          		  else if(lexeme.startsWith("Read(")) {
                 	   result.add(new Token(Type.READ, lexeme.substring(0,4)));
                 	   result.add(new Token(Type.LPAREN, lexeme.substring(4,5)));
                 	 
                 	   if(lexeme.substring(5).matches("^[A-I].*$") && lexeme.substring(5).endsWith(");")) {			//if it contains an IDENT
                 		                   	
                 	     int index = lexeme.indexOf(')'), index2 = lexeme.indexOf(";");
                 		 result.add(new Token(Type.IDENT, lexeme.substring(5,index)));
                 		 result.add(new Token(Type.LPAREN, lexeme.substring(index,index2)));
                 		 result.add(new Token(Type.SEMICOLON, lexeme.substring(index2)));
                 		   
                 	   }
                 	     else {
                 			   result.add(new Token(Type.UNKNOWN, lexeme.substring(5)));
                 			   
                 		   }
                 	  }  
          		
               }
              else if(lexeme.endsWith(")")) {
            	  int index = lexeme.indexOf(')');
            	  
            	  if(lexeme.startsWith("*")) {
            		  result.add(new Token(Type.ENDCOMMENT, lexeme));
            	  }
            	  else if(lexeme.substring(0,lexeme.length()-1).matches("-?\\d+")) {
            		  result.add(new Token(Type.NUMLIT, lexeme.substring(0,lexeme.length()-1)));
             		   result.add(new Token(Type.RPAREN, lexeme.substring(lexeme.length()-1)));
            	  }
            	  else if (lexeme.length() >= 2 && Character.isLetter(lexeme.charAt(lexeme.length() - 2))) {
            		    result.add(new Token(Type.IDENT, lexeme.substring(0, lexeme.length() - 1)));
            		    result.add(new Token(Type.RPAREN, lexeme.substring(lexeme.length() - 1)));
            		}
            	  else 
            		  result.add(new Token(Type.RPAREN,lexeme));           	              	    
                  } 
               
               else if(lexeme.endsWith(":")) {
              		   result.add(new Token(Type.IDENT, lexeme.substring(0,lexeme.length()-1)));
              		   result.add(new Token(Type.COLON, lexeme.substring(lexeme.length()-1)));
              	
                 }
               
               else if(lexeme.startsWith("Write('")) {
            	   result.add(new Token(Type.WRITE, lexeme.substring(0,5)));
            	   result.add(new Token(Type.LPAREN, lexeme.substring(5,6)));
            	   result.add(new Token(Type.SINQUO, lexeme.substring(6,7)));
                   
            	   if(lexeme.substring(7).length() > 0) {
            	  
            		 if(lexeme.substring(7).matches("^[A-I].*$")) {
            			 
            		    if(lexeme.substring(7).endsWith("');")){
                	    	int i = lexeme.indexOf(")"), j = lexeme.indexOf(";"), k = lexeme.indexOf("'");
                	    	  result.add(new Token(Type.IDENT, lexeme.substring(7,k)));
                	    	  result.add(new Token(Type.SINQUO, lexeme.substring(k,i)));
                	    	  result.add(new Token(Type.RPAREN, lexeme.substring(i,j)));
                	    	  result.add(new Token(Type.SEMICOLON, lexeme.substring(j)));
            			  }
            		    else
            	          result.add(new Token(Type.IDENT, lexeme.substring(7)));
            		   }
            	     
                    else if(lexeme.substring(7).matches("-?\\d+")) {
                    	  
                    	if(lexeme.substring(7).endsWith("');")){
                  	    	int i = lexeme.indexOf(")"), j = lexeme.indexOf(";"), k = lexeme.indexOf("'");
                  	    	  result.add(new Token(Type.IDENT, lexeme.substring(7,k)));
                  	    	  result.add(new Token(Type.SINQUO, lexeme.substring(k,i)));
                  	    	  result.add(new Token(Type.RPAREN, lexeme.substring(i,j)));
                  	    	  result.add(new Token(Type.SEMICOLON, lexeme.substring(j)));
              			  }
                    	
            	       result.add(new Token(Type.IDENT, lexeme.substring(7)));
                    }
                    else {
                       result.add(new Token(Type.UNKNOWN, lexeme.substring(7)));
                     }
                  
                     }
            	   else {
            		   //do nothing
            	   }
                }             
               else if(lexeme.equalsIgnoreCase("DIV")) {
            	   result.add(new Token(Type.DIV, lexeme)); 
               }
               else if(lexeme.equalsIgnoreCase("THEN")) {
            	   result.add(new Token(Type.THENSYM, lexeme)); 
               }
               else if(lexeme.equals("*")) {
                    result.add(new Token(Type.TIMES, lexeme));
               }     
               else if(lexeme.equals("+")) {
            	   result.add(new Token(Type.PLUS, lexeme)); 
               } 
                else if(lexeme.equalsIgnoreCase("END.")) {
                    result.add(new Token(Type.END, lexeme));
                }
                else if(lexeme.equalsIgnoreCase("MOD")) {
                    result.add(new Token(Type.MOD, lexeme));
                }
                else if(lexeme.equals("=")) {
                    result.add(new Token(Type.EQL, lexeme));
                }
                else if(lexeme.equalsIgnoreCase("READ")) {
                    result.add(new Token(Type.READ, lexeme));
                }
                else if(lexeme.equalsIgnoreCase("VAR")) {
                    result.add(new Token(Type.VAR, lexeme));
                }
                else if(lexeme.equalsIgnoreCase("CONST")) {
                    result.add(new Token(Type.CONST, lexeme));
                } 
                else if(lexeme.equalsIgnoreCase("-")) {
                    result.add(new Token(Type.MINUS, lexeme));
                }
                else if (lexeme.equalsIgnoreCase("PROGRAM")) {
                    result.add(new Token(Type.PROGRAM, lexeme));
                }
                else if (lexeme.equalsIgnoreCase("IF")){
                    result.add(new Token(Type.IFSYM, lexeme));
                }
                else if(lexeme.equalsIgnoreCase("ELSE")) {
                    result.add(new Token(Type.ELSESYM, lexeme));
                 }
                else if(lexeme.equalsIgnoreCase("INTEGER")) {
                    result.add(new Token(Type.INTEGER, lexeme));
                 }
                else if(lexeme.equalsIgnoreCase("BEGIN")) {
                    result.add(new Token(Type.BEGIN, lexeme));
                 }
                else if(lexeme.equals(":=")) {
                    result.add(new Token(Type.ASSIGN_OP, lexeme));          
                 }
                else if(lexeme.equals(":")) {
                    result.add(new Token(Type.COLON, lexeme));
                 }
                else if(lexeme.matches("-?\\d+")) {
                    result.add(new Token(Type.NUMLIT, lexeme));		//check if its a digit using regular expression
                 }
                else if(lexeme.matches("^[a-zA-Z_].*$")) {
                    result.add(new Token(Type.IDENT, lexeme));		
                  //check if its starts with a letter of the alphabet or underscore using regular expression
                 }
                else {
                	 result.add(new Token(Type.UNKNOWN, lexeme));
            }
        }

               return result;
    }
    
    
    public static void PROGRAM(Token t) {		 //Token PROGRAM must first appear
    	String tokenString = String.format("%-20s", t);
    	
    	if (t.t.equals(Type.PROGRAM)) {
    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+t.getLexeme());
    	    i++;
    		IDENT();
    		SEMICOLON();
    		EXPR();
    	}
    	else {
    		System.out.println("Incorrect Token: " +tokenString+"   Token: PROGRAM expected");
    	    i++;
    		IDENT();
    		SEMICOLON();
    		EXPR();
    		
    	}
    }
    
    public static void IDENT() { 			    //accept IDENT
      String tokenString = String.format("%-20s", tokens.get(i));
      if (tokens.get(i).t.equals(Type.IDENT)) {
    	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	 i++;
    }
    else {
    	System.out.println("Incorrect Token: " +tokenString+"   Token: IDENT expected");
		 i++;
       }    	       	
    }
    
    
    public static void SEMICOLON() {					//accept SEMICOLON
    	 String tokenString = String.format("%-20s", tokens.get(i));
    	 
    	  if (tokens.get(i).t.equals(Type.SEMICOLON)) {
    	    	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	    	i++;
    	    }
    	    else {
    	    	System.out.println("Incorrect Token: " +tokenString+"   Token: SEMICOLON expected");
    			i++;
    	   }    	       	
       }
    	
   
    public static void EXPR() {
     	 
        if(tokens.get(i).t.equals(Type.VAR)) {		//variable declarations
    	   VAR();
    	   IDENT();  
    	   COLON();
    	   INTEGER();
    	   SEMICOLON();
    	   MOREVAR();
         }
     
        if(tokens.get(i).t.equals(Type.CONST)) {		//constant declarations
   	       CONST();
   	       IDENT();  
   	       EQL();
   	       NUMLIT();
   	       SEMICOLON();
   	    }
     
      if(tokens.get(i).t.equals(Type.BEGIN)) {		//branch out 
    	    BEGIN();
        }
      
      if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {		//handle comments 
	    	BEGCOMMENT();
	    	ENDCOMMENT();	
	    }
      else {
      System.out.println("INCORRECT TOKEN, VAR, CONST BEGIN, OR BEGCOMMENT EXPECTED,    CURRENT TOKEN: "+tokens.get(i));
      	 i++;
      }       
       EXPR();    //continuously call the method until "begin" appears 
    }
    
    public static void MOREVAR() {			//if there are more variable declarations 
    	
    	if(tokens.get(i).t.equals(Type.IDENT) && tokens.get(i+1).t.equals(Type.COLON)) {	
    	   IDENT();  
       	   COLON();
       	   INTEGER();
       	   SEMICOLON();
       	   MOREVAR();
    	}
    	else {
    	   EXPR();		//if there are no more variables left to declare, go back to expressions
    	}
    	
    }
    
   public static void BEGIN() {
	   String tokenString = String.format("%-20s", tokens.get(i));
    	if (tokens.get(i).t.equals(Type.BEGIN)) {
 		   System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
 	    	i++;
 	        MAINEXPR();		//analyze the expressions that will come after BEGIN
    	}
   }
    	
   public static void MAINEXPR() {
        String tokenString = String.format("%-20s", tokens.get(i));
    	 
        if(tokens.get(i).t.equals(Type.WRITE)) {
 	    	   WRITE();
 	    	   LPAREN();
 	    	   SINGQUO();				//matches quotes
 	    	  
 	    	   while(!tokens.get(i).t.equals(Type.SINQUO))  {
 	    	   System.out.println(tokens.get(i).getLexeme());	//print string that is located in write function
 	    	   i++;
 	    	   }
 	    	   
 	    	   SINGQUO();			   //matches quotes
 	    	   RPAREN();			  //matches parenthesis 
 	    	   SEMICOLON();
 	       } //end write if
 	      
 	      if(tokens.get(i).t.equals(Type.READ)) {
 	   	       READ();
 	   	       LPAREN();  
 	   	       IDENT();
 	   	       RPAREN();
 	   	       SEMICOLON();
 	   	  }
 	      
 	      
 	     if(tokens.get(i).t.equals(Type.FOR)) {
	   	       FOR(); 
	   	       IDENT();
	   	       ASSIGN_OP();
	   	       NUMLIT();
	   	       TO();
	   	       NUMLIT();
	   	  }
 	   
 	     if(tokens.get(i).t.equals(Type.WHILE)) {
	   	       WHILE(); 
	   	       IDENT();
	   	     if(tokens.get(i).t.equals(Type.GTR)) {
	   	        GTR();
	   	       }
	   	     else if(tokens.get(i).t.equals(Type.LSS)) {
		   	    LSS();
	   	      }
	   	      else {
	   	    	 System.out.println("COMPARISON OPERATOR EXPECTED");
	   	     }
	   	      NUMLIT();
	   	      DO();
	   	  }
 	     
 	    
 	     if(tokens.get(i).t.equals(Type.IFSYM)) {
 	    	    IF();
 	    	    LPAREN();
 	    	    IDENT();
 	    	    EQL();
 	    	    NUMLIT();
 	    	    RPAREN();
 	    	    THEN();   	
 	     } 
 	   
 	     if(tokens.get(i).t.equals(Type.IDENT)) {		//IDENT IF STATEMENT START
 	    	   IDENT();
 	    	   ASSIGN_OP();
 	    	   IDENT();
 	    	   
 	        if(tokens.get(i).t.equals(Type.PLUS)) {
 	      	    PLUS();
 	      	    NUMLIT();
 	      	    SEMICOLON();
 	        }
 	        else if(tokens.get(i).t.equals(Type.MINUS)) {
 	    	    MINUS();
 	    	    NUMLIT();
	      	    SEMICOLON();
 	        }
 	        else if(tokens.get(i).t.equals(Type.MOD)) {
	    	    MOD();
	    	    NUMLIT();
 	      	    SEMICOLON();
	        }
 	        else if(tokens.get(i).t.equals(Type.DIV)) {
	    	    DIV();
	    	    NUMLIT();
 	      	    SEMICOLON();
	      }
 	        else if(tokens.get(i).t.equals(Type.TIMES)) {
	    	    TIMES();
	    	    NUMLIT();
 	      	    SEMICOLON();
	      }
 	         else {
 	    	 System.out.println("ARITHMETIC OPERATOR EXPECTED");
 	    	 MAINEXPR();		
 	      }
 	     
 	    }							//end IDENT if statement 
 	    
 	   else if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {
 	    	BEGCOMMENT();
 	    	ENDCOMMENT();	
 	    	MAINEXPR();	
 	    }   //comments 
 	   
 	   else if(tokens.get(i).t.equals(Type.END)) {
	    	END();
	    }   
 	    else {
 	    	System.out.println("Incorrect Token: " +tokenString+"   Token: expected");
 			i++;
 			MAINEXPR();		 
 	     } 
    	
    }
   
    
   public static void BEGCOMMENT() {		//accept the beginning of a comment 
	    String tokenString = String.format("%-20s", tokens.get(i));
       if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {
    	   System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	   i++;
    	   
    	   while(!tokens.get(i).t.equals(Type.ENDCOMMENT)) {		//ignore the comment until the ENDCOMMENT token appears
    	       System.out.println("Ignored comment: "+tokens.get(i));  		      		   
    		   i++;  
    	   }
    	}       	    	    	
    	 else {
    	     System.out.println("Incorrect Token: "+tokenString+"   Token: BEGCOMMENT expected");
    	       i++;
    	   } 
     } 	
   public static void DO() {
	  String tokenString = String.format("%-20s", tokens.get(i));
       if(tokens.get(i).t.equals(Type.DO)) {
    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	  	i++;
    	 }
    	   else {
    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: DO expected");
    	  	 i++;
    	 } 
   }
   
   public static void FOR() {
	  String tokenString = String.format("%-20s", tokens.get(i));
       if(tokens.get(i).t.equals(Type.FOR)) {
    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	  	i++;
    	 }
    	   else {
    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: FOR expected");
    	  	 i++;
    	 } 
   }
   
   public static void GTR() {
	  String tokenString = String.format("%-20s", tokens.get(i));
       if(tokens.get(i).t.equals(Type.GTR)) {
    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	  	i++;
    	 }
    	   else {
    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: GTR expected");
    	  	 i++;
    	 } 
   }
   
   public static void LSS() {
		  String tokenString = String.format("%-20s", tokens.get(i));
	       if(tokens.get(i).t.equals(Type.LSS)) {
	    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	  	i++;
	    	 }
	    	   else {
	    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: LSS expected");
	    	  	 i++;
	    	 } 
	   }
   
   public static void WHILE() {
		String tokenString = String.format("%-20s", tokens.get(i));
	       if(tokens.get(i).t.equals(Type.WHILE)) {
	    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	  	i++;
	    	 }
	    	   else {
	    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: WHILE expected");
	    	  	 i++;
	    	 } 
	   }
   
   
   
   public static void ENDCOMMENT() {			                     //accept the end of the comment 
	   String tokenString = String.format("%-20s", tokens.get(i));
       if(tokens.get(i).t.equals(Type.ENDCOMMENT)) {
    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	  	i++;
    	 }
    	   else {
    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: ENDCOMMENT expected");
    	  	 i++;
    	 } 
    } 	
   
   
   public static void END() {
	   String tokenString = String.format("%-20s", tokens.get(i));
       
	   if(tokens.get(i).t.equals(Type.END)) {
    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	  	 i++;
    	  	 ENDRESULT();
    	  }
    	  else {
    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: END expected");
    	  	 i++;
    	  	} 
     } 
   
   public static void TO() {
		  String tokenString = String.format("%-20s", tokens.get(i));
	       if(tokens.get(i).t.equals(Type.TO)) {
	    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	  	i++;
	    	 }
	    	   else {
	    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: TO expected");
	    	  	 i++;
	    	 } 
	   }
    	
    public static void ENDRESULT() {
      
    	if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {		// see if any comments exist after END.
 	    	BEGCOMMENT();
 	    	ENDCOMMENT();	
 	     }   //comments 
    	else {
    	   System.out.println("ERROR ONLY COMMENTS CAN EXIST!");
    	   System.exit(0);		//if no comments, end the syntax analysis
    	}
    }
    
    public static void DIV() {
    	String tokenString = String.format("%-20s", tokens.get(i));
       if(tokens.get(i).t.equals(Type.DIV)) {
    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	  	   i++;
    	  	 }
    	  else {
    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: DIV expected");
    	  	 i++;
    	  	 } 
    	 } 	
    
    public static void TIMES() {
    	String tokenString = String.format("%-20s", tokens.get(i));
        if(tokens.get(i).t.equals(Type.TIMES)) {
     	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
     	  	   i++;
     	  	 }
     	  else {
     	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: TIMES expected");
     	  	 i++;
     	    } 
     	 } 	 
    	
    public static void MOD() {
    	String tokenString = String.format("%-20s", tokens.get(i));
        if(tokens.get(i).t.equals(Type.MOD)) {
  		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
  	    	i++;
  	    }
  	    else {
  	    	System.out.println("Incorrect Token: " +tokenString+"   Token: MOD expected");
  			i++;
  	     } 
    }
    	
    	
   public static void MINUS() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if(tokens.get(i).t.equals(Type.MINUS)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	    else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: MINUS expected");
			i++;
	     } 
   }
    
   public static void PLUS() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.PLUS)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	    else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: PLUS expected");
			i++;
	     } 
   }
   public static void ASSIGN_OP() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.ASSIGN_OP)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	    else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: ASSIGN_OP expected");
			i++;
	     } 
   }
    public static void IF() {
    	 String tokenString = String.format("%-20s", tokens.get(i));
    	 if (tokens.get(i).t.equals(Type.IFSYM)) {
 		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
 	    	i++;
 	    }
 	    else {
 	    	System.out.println("Incorrect Token: " +tokenString+"   Token: IFSYM expected");
 			i++;
 	     } 
    }
   
    public static void THEN() {
       String tokenString = String.format("%-20s", tokens.get(i));
   	   if (tokens.get(i).t.equals(Type.THENSYM)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	    else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: THENSYM expected");
			i++;
	     } 
    }
    
    public static void VAR() { 
    	 String tokenString = String.format("%-20s", tokens.get(i));
    	 if (tokens.get(i).t.equals(Type.VAR)) {
    		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
    	    	i++;
    	    }
    	    else {
    	    	System.out.println("Incorrect Token: " +tokenString+"   Token: SEMICOLON expected");
    			i++;
    	     } 
     }
    
    public static void COLON() {
       String tokenString = String.format("%-20s", tokens.get(i));
       if (tokens.get(i).t.equals(Type.COLON)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	    else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: COLON expected");
			i++;
	     } 
     }
   	
    
   public static void INTEGER() {
	  String tokenString = String.format("%-20s", tokens.get(i));
      if (tokens.get(i).t.equals(Type.INTEGER)) {
 		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
 	    	i++;
 	    }
 	  else {
 	    	System.out.println("Incorrect Token: " +tokenString+"   Token: INTEGER expected");
 			i++;
 	     }	 	
    }
   
   public static void CONST() {	   
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.CONST)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	   else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: CONST expected");
			i++;
	     }	
	   
   }
   
   public static void EQL() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.EQL)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	  else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: EQL expected");
			i++;
	     }	
   }
   
   
   public static void NUMLIT() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.NUMLIT)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	  else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: NUMLIT expected");
			i++;
	     }	
     }
    
   public static void WRITE() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.WRITE)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	  else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: WRITE expected");
			i++;
	     }	
     }
  
   public static void LPAREN() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.LPAREN)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	  else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: LPAREN expected");
			i++;
	     }	
     }
   
   public static void SINGQUO() {
	   String tokenString = String.format("%-20s", tokens.get(i));
	   if (tokens.get(i).t.equals(Type.SINQUO)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	   else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: SINQUO expected");
			i++;
	    } 
     }
   
  public static void RPAREN() {
	  String tokenString = String.format("%-20s", tokens.get(i));
	  if (tokens.get(i).t.equals(Type.RPAREN)) {
		  	System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	   else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: RPAREN expected");
			i++;
	    } 
   }
    
  public static void READ() {
	  String tokenString = String.format("%-20s", tokens.get(i));
	  if (tokens.get(i).t.equals(Type.READ)) {
		   System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	i++;
	    }
	   else {
	    	System.out.println("Incorrect Token: " +tokenString+"   Token: READ expected");
			i++;
	    } 
  }
    
    public static void main(String[] args) throws FileNotFoundException {
       
        try {
            Scanner s = new Scanner(new File("/Users/sotirisemmanouil/eclipse-workspace/CS316 Project 2/src/test1.pas"));
            StringBuilder inputBuilder = new StringBuilder();		

            while (s.hasNext()) {
                inputBuilder.append(s.next()).append(" ");	
            }

            String input = inputBuilder.toString().trim();
            tokens = lexicallyAnalyze(input);					//append the lexically analyzed tokens into the arraylist 
            
        } 
        catch (IOException e) {
            System.out.println("Error accessing input file!");
        }
        
        PROGRAM(tokens.get(i));	//begin syntax analysis
        
          }
}
