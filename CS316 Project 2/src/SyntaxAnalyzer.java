import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
  Syntax analyzer for a subset of the Pascal language

  Methods used for the Syntax Analysis:
	Accept()
        MOREVAR()
	EXPR()
 	MAINEXPR()
        PROGRAM()
 */

public class SyntaxAnalyzer {
	
	static int i = 0; 
	static List<Token> tokens = null;
	
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
	    	    Accept(Type.IDENT);
	    	    Accept(Type.SEMICOLON);
	    		EXPR();
	    	}
	    	else {
	    		System.out.println("Incorrect Token: " +tokenString+"   Token: PROGRAM expected");
	    	    i++;
	    	    Accept(Type.IDENT);
	    	    Accept(Type.SEMICOLON);
	    		EXPR();
	    		
	    	}
	    }
	    
	 public static void Accept(Type token) {
	    	String tokenString = String.format("%-20s", tokens.get(i));
	       
	    	if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {
	 	    	   System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
	 	    	   i++;
	 	    	   
	 	    	   while(!tokens.get(i).t.equals(Type.ENDCOMMENT)) {		//ignore the comment until the ENDCOMMENT token appears
	 	    	       System.out.println("Ignored comment: "+tokens.get(i));  		      		   
	 	    		   i++;  
	 	    	   }
	 	    	}  
	    	else if (tokens.get(i).t.equals(Type.BEGIN)) {
		 		   System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
		 	    	i++;
		 	        MAINEXPR();		//analyze the expressions that will come after BEGIN
		    	}
	    	
	    	else if(tokens.get(i).t.equals(Type.END)) {
		    	    System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+tokens.get(i).getLexeme());
		    	  	 i++;
		    	 	if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {		// see if any comments exist after END.
			 	    	Accept(Type.BEGCOMMENT);
			 	    	Accept(Type.ENDCOMMENT);
			 	     }   //comments 
			    	else {
			    	   System.out.println("ERROR ONLY COMMENTS CAN EXIST!");
			    	   System.exit(0);		//if no comments, end the syntax analysis
			    	}
		      }
	    	
	    	else if(tokens.get(i).t.equals(token)) {
	    		System.out.println("Accepted Token: " +tokenString+"     Current Lexeme:   "+ tokens.get(i).getLexeme());
	    		i++;
	    	  }
	    	     	    	    	
	    	 else {
	    	     System.out.println("Incorrect Token: " +tokenString+"   Token: "+ token+ " expected");
	   			 i++; 
	    	 }
	    }
	    
	    	
	   
	public static void EXPR() {
	     	 
	        if(tokens.get(i).t.equals(Type.VAR)) {		//variable declarations
	           Accept(Type.VAR);
	    	   Accept(Type.IDENT);
	    	   Accept(Type.COLON);
	    	   Accept(Type.INTEGER);
	    	   Accept(Type.SEMICOLON);
	    	   MOREVAR();
	         }
	     
	        if(tokens.get(i).t.equals(Type.CONST)) {		//constant declarations
	          Accept(Type.CONST);
	          Accept(Type.IDENT);
	          Accept(Type.EQL);
	          Accept(Type.NUMLIT);
	          Accept(Type.SEMICOLON);
	   	    }
	     
	      if(tokens.get(i).t.equals(Type.BEGIN)) {		//branch out 
	    	   Accept(Type.BEGIN);
	        }
	      
	      if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {		//handle comments 
		    	Accept(Type.BEGCOMMENT);
		    	Accept(Type.ENDCOMMENT);
		    }
	      else {
	      System.out.println("INCORRECT TOKEN, VAR, CONST BEGIN, OR BEGCOMMENT EXPECTED,        CURRENT TOKEN: "+tokens.get(i));
	      	 i++;
	      }       
	       EXPR();    //continuously call the method until "begin" appears 
	    }
	    
	public static void MOREVAR() {			//if there are more variable declarations 
	    	
	    	if(tokens.get(i).t.equals(Type.IDENT) && tokens.get(i+1).t.equals(Type.COLON)) {	
	    	  Accept(Type.IDENT);
	    	  Accept(Type.COLON);
	    	  Accept(Type.INTEGER);
	    	  Accept(Type.SEMICOLON);
	       	  MOREVAR();
	    	}
	    	else {
	    	   EXPR();		//if there are no more variables left to declare, go back to expressions
	    	}
	    	
	    }
	    	
	   public static void MAINEXPR() {
	        String tokenString = String.format("%-20s", tokens.get(i));
	    	 
	        if(tokens.get(i).t.equals(Type.WRITE)) {
	 	    	  
	        	 Accept(Type.WRITE);
	        	 Accept(Type.LPAREN);
	        	 Accept(Type.SINQUO);
	 	    	  
	 	    	 while(!tokens.get(i).t.equals(Type.SINQUO))  {
	 	    	   System.out.println(tokens.get(i).getLexeme());	//print string that is located in write function
	 	    	   i++;
	 	    	 }
	 	    	 
	 	    	 Accept(Type.SINQUO);
	 	    	 Accept(Type.RPAREN);
	 	    	 Accept(Type.SEMICOLON);
	 	       } //end write if
	 	      
	 	      if(tokens.get(i).t.equals(Type.READ)) {
	 	    	 Accept(Type.READ);
	 	    	 Accept(Type.LPAREN);
	 	    	 Accept(Type.IDENT);
	 	    	 Accept(Type.RPAREN);
	 	    	 Accept(Type.SEMICOLON);
	 	   	  }
	 	      
	 	     if(tokens.get(i).t.equals(Type.FOR)) {
	 	    	 Accept(Type.FOR);
	 	    	 Accept(Type.IDENT);
	 	    	 Accept(Type.ASSIGN_OP);
	 	    	 Accept(Type.NUMLIT);
	 	    	 Accept(Type.TO);
	 	    	 Accept(Type.NUMLIT);
		   	  }
	 	   
	 	     if(tokens.get(i).t.equals(Type.WHILE)) {
	 	    	 Accept(Type.WHILE);
	 	    	 Accept(Type.IDENT);
	 	   
		   	     if(tokens.get(i).t.equals(Type.GTR)) {
		   	    	 Accept(Type.GTR);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.LSS)) {
		   	    	 Accept(Type.LSS);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.GEQ)) {
		   	    	 Accept(Type.GEQ);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.LEQ)) {
		   	    	 Accept(Type.LEQ);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.EQL)) {
		   	    	 Accept(Type.EQL);
		   	      }
		   	      else {
		   	    	 System.out.println("COMPARISON OPERATOR EXPECTED");
		   	    	 i++;
		   	     }
		   	     Accept(Type.NUMLIT);
		   	     Accept(Type.DO);
		   	  }
	 	     
	 	    
	 	     if(tokens.get(i).t.equals(Type.IFSYM)) {
	 	    	 Accept(Type.IFSYM);
	 	    	 Accept(Type.LPAREN);
	 	    	 
	 	        if(tokens.get(i).t.equals(Type.NUMLIT)) {
	 	        	 Accept(Type.NUMLIT);
		 	   	 }
	 	        else if(tokens.get(i).t.equals(Type.IDENT)) {
	 	        	 Accept(Type.IDENT);
	 	   	     }
	 	        else {
	 	        	System.out.println("NUMLIT OR IDENT EXPECTED");
	 	        	i++;
	 	         }
	 	          if(tokens.get(i).t.equals(Type.GTR)) {
	 	        	 Accept(Type.GTR);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.LSS)) {
		   	    	 Accept(Type.LSS);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.GEQ)) {
		   	    	 Accept(Type.GEQ);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.LEQ)) {
		   	    	 Accept(Type.LEQ);
		   	      }
		   	     else if(tokens.get(i).t.equals(Type.EQL)) {
		   	    	 Accept(Type.EQL);
		   	      }
		   	     else {
		   	    	System.out.println("COMPARISON OPERATOR EXPECTED");
		   	    	i++;
		   	     }
	 	         if(tokens.get(i).t.equals(Type.NUMLIT)) {
	 	        	     Accept(Type.NUMLIT);
			 	   	 }
		 	        else if(tokens.get(i).t.equals(Type.IDENT)) {
		 	        	 Accept(Type.IDENT);
		 	        	
		 	   	     }
		 	        else {
		 	        	System.out.println("NUMLIT OR IDENT EXPECTED");
		 	        	i++;
		 	         }
	 	             Accept(Type.RPAREN);
	 	             Accept(Type.THENSYM);	
	 	     } 
	 	   
	 	     if(tokens.get(i).t.equals(Type.IDENT)) {		//IDENT IF STATEMENT START
	 	    	    Accept(Type.IDENT);
	 	    	    Accept(Type.ASSIGN_OP);
	 	    	    Accept(Type.IDENT);
	 	    	   
	 	        if(tokens.get(i).t.equals(Type.PLUS)) {
	 	        	 Accept(Type.PLUS);
	 	        	 Accept(Type.NUMLIT);
	 	        	 Accept(Type.SEMICOLON);
	 	        }
	 	        else if(tokens.get(i).t.equals(Type.MINUS)) {
	 	        	 Accept(Type.MINUS);
	 	        	 Accept(Type.NUMLIT);
	 	        	 Accept(Type.SEMICOLON);
	 	        }
	 	        else if(tokens.get(i).t.equals(Type.MOD)) {
	 	        	 Accept(Type.MOD);
	 	        	 Accept(Type.NUMLIT);
	 	        	 Accept(Type.SEMICOLON);
		        }
	 	        else if(tokens.get(i).t.equals(Type.DIV)) {
	 	        	 Accept(Type.DIV);
	 	        	 Accept(Type.NUMLIT);
	 	        	 Accept(Type.SEMICOLON);
		      }
	 	        else if(tokens.get(i).t.equals(Type.TIMES)) {
	 	        	 Accept(Type.TIMES);
	 	        	 Accept(Type.NUMLIT);
	 	        	 Accept(Type.SEMICOLON);
		      }
	 	         else {
	 	    	 System.out.println("ARITHMETIC OPERATOR EXPECTED");
	 	    	 i++;
	 	    	 MAINEXPR();		
	 	      }
	 	     
	 	    }							//end IDENT if statement 
	 	    
	 	   else if(tokens.get(i).t.equals(Type.BEGCOMMENT)) {
	 	    	Accept(Type.BEGCOMMENT);
	 	    	Accept(Type.ENDCOMMENT);
	 	    	MAINEXPR();	
	 	    }   //comments 
	 	   
	 	   else if(tokens.get(i).t.equals(Type.END)) {
	 		  Accept(Type.END);
		    }   
	 	    else {
	 	    	System.out.println("Incorrect Token: " +tokenString);
	 			i++;
	 			MAINEXPR();		 
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
