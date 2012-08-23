/******************************
This file is a slight modification of the macros.flex file in the JastAddJ->Java1.4Frontend
software.
**********************************/


// Line Terminators                                                         
LineTerminator = \n|\r|\r\n                                                     
InputCharacter = [^\r\n]                                                        
                                                                                
// White Space                                                              
WhiteSpace = [ ] | \t | \f | {LineTerminator}                                   
                                                                                
// Comments                                                                 
Comment = {TraditionalComment}                                                  
        | {EndOfLineComment}                                                    
                                                                                
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/" | "/*" "*"+ [^/*] ~"*/"       
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?                     
                                                                                
// Identifiers                                                              
Identifier = [:jletter:][:jletterdigit:]*                                       
                                                                                
// Integer Literals                                                      
DecimalNumeral = 0 | [+-]? {NonZeroDigit} {Digits}? 
HexNumeral = [+-]? 0 [xX] [0-9a-fA-F]+ 
OctalNumeral = [+-]? 0 [0-7]+ 

IntegerNumeral = {DecimalNumeral}
		|{HexNumeral}
		|{OctalNumeral}                                                         
                                                                                
Digits = {Digit}+                                                               
Digit = 0 | {NonZeroDigit}                                                      
NonZeroDigit = [1-9]                                                            
                                                                                
// Floating-Point Literals                                               
FloatingPointLiteral = [+-]? {Digits} \. {Digits}? {ExponentPart}?                    
                     | [+-]? \. {Digits} {ExponentPart}?                              
                     | [+-]?  {Digits} {ExponentPart}                                  
ExponentPart = [eE] [+-]? [0-9]+                                                

//Boolean Literals
BooleanLiteral = "true" | "false"
                                                                                
// Character Literals                                                    
SingleCharacter = [^\r\n\'\\]                                                   
                                                                                
// String Literals                                                       
StringCharacter = [^\r\n\"\\]                                                   
                                                                                
// Escape Sequences for Character and String Literals                    
OctalEscape = \\ {OctalDigit}                                                   
            | \\ {OctalDigit} {OctalDigit}                                      
            | \\  {ZeroToThree} {OctalDigit} {OctalDigit}                       
OctalDigit = [0-7]                                                              
ZeroToThree = [0-3]                       
