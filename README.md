# Syntax-Analyzer
## Syntactically Analyzes a given Pascal source code file


This project utilizes the Lexical Analyzer I previously created to syntactically analyze a given Pascal source code file. 
The way it does this is by following the grammar for a subset of the Pascal language and ensuring the tokens are appearing in a syntactically correct manner. 
For example, if a `VAR` declaration appears, it must follow the following grammar rule: `VAR IDENT COLON TYPE SEMICOLON`
or else the expected tokens will be printed onto the console. 

The syntax analyzer features:
<ul>
<li>Comment neglecting</li>
<li>Parenthesis matching</li>
<li>Quote matching</li>
<li>If statements analysis</li>
<li>For loop analysis</li>
<li>While loop analysis</li>
<li>Variable assignment analysis </li>
<li>Variable declaration analysis </li>
<li>Constant declarations and assignment analysis </li>
</ul>
And more. 

## How it works:


The program reads the given Pascal source code file, lexically analyzes it by assigning each lexeme an appropriate token, and adds all the Token:Lexeme 
pairs into an ArrayList. Once this is done, the Syntax analyzer will begin to traverse the ArrayList and ensure the order of the tokens appear in a syntactically appropriate manner,which maintains grammar rules. Roughly each token has its own “accept method” which will either “accept” the token or print the expected token, according to what statement or operation is occurring. 

This project allowed me to learn more about the second stage of compilation in programming languages. 
