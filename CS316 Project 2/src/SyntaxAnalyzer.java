import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
  Syntax analyzer for a subset of the Pascal language
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
    
        public String getLexeme() {			//get the input attached to the token
        	return c;
        }
       
	}
	   public static List<Token> lexicallyAnalyze(String input) {
	        List<Token> result = new ArrayList<Token>();
	        String[] lexemes = input.split("\\s+"); // Split input into tokens using whitespace as separator
	        	
	        for (String lexeme : lexemes) {			//traverse array
	            String currentToken = "";				
	            boolean insideString = false;

	            for (int i = 0; i < lexeme.length(); i++) {		//traverse each character of the string
	                char currentChar = lexeme.charAt(i);

	                if (currentChar == '(' || currentChar == '*' || currentChar == '>' || currentChar == '<'
	                    || currentChar == ';' || currentChar == ')' || currentChar == '\''  || currentChar == ':' ) {
	                    if (!insideString) {//if its one of these 
	                        // Add the current token to the result if it's not empty
	                        if (!currentToken.isEmpty()) {
	                            result.add(new Token(assign(currentToken), currentToken));
	                            currentToken = "";
	                        }

	                        // Check for multi-character tokens
	                        if (i < lexeme.length() - 1) {		//if the the next char is one of these
	                            String nextTwoChars = lexeme.substring(i, i + 2);
	                            if (nextTwoChars.equals("(*") || nextTwoChars.equals("*)")		
	                                    || nextTwoChars.equals(">=")  || nextTwoChars.equals(":=")|| nextTwoChars.equals("<=")) {
	                                // Add multi-character token and increment the index
	                                result.add(new Token(assign(nextTwoChars), nextTwoChars));  //add the two chars to the list 
	                                i++;
	                                continue;
	                            }
	                        }

	                        // Add the current character as a separate token
	                        result.add(new Token(assign(String.valueOf(currentChar)), String.valueOf(currentChar)));
	                    } else {
	                        // Inside a string, just add the character to the current token
	                        currentToken += currentChar;
	                    }
	                } else if (currentChar == '\'') {
	                    insideString = !insideString;
	                    currentToken += currentChar;
	                } else {
	                    // Add the character to the current token
	                    currentToken += currentChar;
	                }
	            }

	            // Add the remaining token if it's not empty
	            if (!currentToken.isEmpty()) {
	                result.add(new Token(assign(currentToken), currentToken));
	            }
	        }

	        return result;
	    }



	    
	    public static Type assign(String input) {
	    	
	         
	        if(input.equalsIgnoreCase("WRITE")) {
	    		return Type.WRITE;
	    	}
	    	else if(input.equals("=")) {
	    		return Type.EQL;
	    	}
	    	else if(input.equals(":=")) {
	    		return Type.ASSIGN_OP;
	    	}
	    	else if(input.equals(";")) {
	    		return Type.SEMICOLON;
	    	}
	    	else if(input.equals("'")) {
	    		return Type.SINQUO;
	    	}
	    	else if(input.equalsIgnoreCase("READ")) {
	    		return Type.READ;
	    	}
	    	else if(input.equalsIgnoreCase("VAR")) {
	    		return Type.VAR;
	    	}
	        else if(input.equalsIgnoreCase("FOR")) {
	    		return Type.FOR;
	    	}
	    	else if(input.equalsIgnoreCase("CONST")) {
	    		return Type.CONST;
	    	}
	    	else if(input.equalsIgnoreCase("PROGRAM")) {
	    		return Type.PROGRAM;
	    	}
	    	else if(input.equalsIgnoreCase("INTEGER")) {
	    		return Type.INTEGER;
	    	}
	    	else if(input.equalsIgnoreCase("DIV")) {
	    		return Type.DIV;
	    	}
	    	else if(input.equalsIgnoreCase("MOD")) {
	    		return Type.MOD;
	    	}
	    	else if(input.equalsIgnoreCase("IF")) {
	    		return Type.IFSYM;
	    	}
	    	else if(input.equalsIgnoreCase("WHILE")) {
	    		return Type.WHILE;
	    	}
	        else if(input.equalsIgnoreCase("TO")) {
	    		return Type.TO;
	    	}
	    	else if(input.equalsIgnoreCase("DO")) {
	    		return Type.DO;
	    	}
	    	else if(input.equals(")")) {
	    		return Type.RPAREN;
	    	}
	    	else if(input.equals("(")) {
	    		return Type.LPAREN;
	    	}
	    	else if(input.equals("+")) {
	    		return Type.PLUS;
	    	}
	    	else if(input.equals(",")) {
	    		return Type.COMMA;
	    	}
	    	else if(input.equals(">=")) {
	    		return Type.GEQ;
	    	}
	    	else if(input.equals("<=")) {
	    		return Type.LEQ;
	    	}
	    	else if(input.equals(">")) {
	    		return Type.GTR;
	    	}
	    	else if(input.equals("<")) {
	    		return Type.LSS;
	    	}
	    	else if(input.equals(":")) {
	    		return Type.COLON;
	    	}
	    	else if(input.equals("-")) {
	    		return Type.MINUS;
	    	}
	    	else if(input.equals("*")) {
	    		return Type.TIMES;
	    	}
	    	else if(input.equals("(*")) {
	    		return Type.BEGCOMMENT;
	    	}
	    	else if(input.equalsIgnoreCase("ELSE")) {
	    		return Type.ELSESYM;
	    	}
	    	else if(input.equalsIgnoreCase("TRUE")) {
	    		return Type.TRUESYM;
	    	}
	    	else if(input.equalsIgnoreCase("FALSE")) {
	    		return Type.FALSESYM;
	    	}
	    	else if(input.equalsIgnoreCase("END.")) {
	    		return Type.END;
	    	}
	    	else if(input.equalsIgnoreCase("THEN")) {
	    		return Type.THENSYM;
	    	}
	    	else if(input.equalsIgnoreCase("BEGIN")) {
	    		return Type.BEGIN;
	    	}
	    	else if(input.equals("*)")) {
	    		return Type.ENDCOMMENT;
	    	}
	    	else if(input.matches("^[a-zA-Z_].*$")) {
	    		return Type.IDENT;
	    	}
	    	else if(input.matches("-?\\d+")) {
	    		return Type.NUMLIT;
	    	}
	    	else {
	    	return Type.UNKNOWN;
	        }
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
		   	     else if(tokens.get(i).t.equals(Type.GEQ)) {
			   	    GEQ();
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.LEQ)) {
			   	    LEQ();
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.EQL)) {
			   	    EQL();
		   	      }
		   	      else {
		   	    	 System.out.println("COMPARISON OPERATOR EXPECTED");
		   	    	 i++;
		   	     }
		   	      NUMLIT();
		   	      DO();
		   	  }
	 	     
	 	    
	 	     if(tokens.get(i).t.equals(Type.IFSYM)) {
	 	    	    IF();
	 	    	    LPAREN();
	 	        if(tokens.get(i).t.equals(Type.NUMLIT)) {
		 	   		NUMLIT();
		 	   	 }
	 	        else if(tokens.get(i).t.equals(Type.IDENT)) {
	 	    	     IDENT();
	 	   	     }
	 	        else {
	 	        	System.out.println("NUMLIT OR IDENT EXPECTED");
	 	        	i++;
	 	         }
	 	          if(tokens.get(i).t.equals(Type.GTR)) {
		   	        GTR();
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.LSS)) {
			   	    LSS();
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.GEQ)) {
			   	    GEQ();
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.LEQ)) {
			   	    LEQ();
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.EQL)) {
			   	    EQL();
		   	      }
		   	     else {
		   	    	System.out.println("COMPARISON OPERATOR EXPECTED");
		   	    	i++;
		   	     }
	 	         if(tokens.get(i).t.equals(Type.NUMLIT)) {
			 	   		NUMLIT();
			 	   	 }
		 	        else if(tokens.get(i).t.equals(Type.IDENT)) {
		 	    	     IDENT();
		 	   	     }
		 	        else {
		 	        	System.out.println("NUMLIT OR IDENT EXPECTED");
		 	        	i++;
		 	         }
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
	 	    	 i++;
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
	 	    	System.out.println("Incorrect Token: " +tokenString);
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

	public static void GEQ() {
		  String tokenString = String.format("%-20s", tokens.get(i));
	       if(tokens.get(i).t.equals(Type.GEQ)) {
	    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	  	i++;
	    	 }
	    	   else {
	    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: GEQ expected");
	    	  	 i++;
	    	 } 
	   }
	public static void LEQ() {
		  String tokenString = String.format("%-20s", tokens.get(i));
	       if(tokens.get(i).t.equals(Type.LEQ)) {
	    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	    	  	i++;
	    	 }
	    	   else {
	    	  	 System.out.println("Incorrect Token: " +tokenString+"   Token: LEQ expected");
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
	            Scanner s = new Scanner(new File("/Users/sotirisemmanouil/git/repository5/316 project 1/src/test1.pas"));
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
