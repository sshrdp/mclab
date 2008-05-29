package natlab;

import static natlab.NatlabParser.Terminals.*;

import beaver.Symbol;
import beaver.Scanner;

%%

%public
%final
%class NatlabScanner
%extends Scanner
%unicode
%function nextToken
%type Symbol
%yylexthrow Scanner.Exception
%line
%column

%{
  //// Returning symbols ///////////////////////////////////////////////////////

  //wrap a type (e.g. IDENTIFIER) in a symbol object with appropriate position info
  private Symbol symbol(short type) {
    //if we return anything while in FIELD_NAME, then switch back to initial
    //i.e. only the first token after the dot is parsed specially
    if(yystate() == FIELD_NAME) {
        yybegin(YYINITIAL);
    }
    return new Symbol(type, yyline + 1, yycolumn + 1, yylength());
  }
  
  //wrap a type (e.g. IDENTIFIER) and value (e.g. "x") in a symbol object with appropriate position info
  private Symbol symbol(short type, Object value) {
    //if we return anything while in FIELD_NAME, then switch back to initial
    //i.e. only the first token after the dot is parsed specially
    if(yystate() == FIELD_NAME) {
        yybegin(YYINITIAL);
    }
    return new Symbol(type, yyline + 1, yycolumn + 1, yylength(), value);
  }
  
  //// Errors //////////////////////////////////////////////////////////////////
  
  //throw an exceptions with appropriate position information
  private void error(String msg) throws Scanner.Exception {
    throw new Scanner.Exception(yyline + 1, yycolumn + 1, msg);
  }
  
  //throw an exceptions with appropriate position information
  //columnOffset is added to the column
  private void error(String msg, int columnOffset) throws Scanner.Exception {
    throw new Scanner.Exception(yyline + 1, yycolumn + 1 + columnOffset, msg);
  }
  
  //// Strings /////////////////////////////////////////////////////////////////
  
  //throws an exception if the string contains any invalid escape sequences
  private void validateEscapeSequences(String str) throws Scanner.Exception {
    boolean expectEscape = false;
    int offset = 0;
    for(char ch : str.toCharArray()) {
        if(expectEscape) {
            if("bfnrt\\\"".indexOf(ch) < 0) {
                //NB: offset - 1 so that the preceding \ is flagged as the error
                error("Invalid escape sequence '\\" + ch + "'", offset - 1);
            }
            expectEscape = false;
        } else if(ch == '\\') {
            expectEscape = true;
        }
        offset++;
    }
    if(expectEscape) {
        //TODO-AC: better column number
        error("Incomplete escape sequence '\\'", offset);
    }
  }
  
  //// Numbers /////////////////////////////////////////////////////////////////
  
  private DecIntNumericLiteralValue parseDecInt(String text, boolean imaginary) throws Scanner.Exception {
      try { 
          return new DecIntNumericLiteralValue(yytext(), imaginary);
      } catch(NumberFormatException e) {
          error("Invalid number: " + yytext() + " (" + e.getMessage() + ")");
          return null; //unreachable - error throws an exception
      }
  }
  
  private HexNumericLiteralValue parseHexInt(String text, boolean imaginary) throws Scanner.Exception {
      try { 
          return new HexNumericLiteralValue(yytext(), imaginary);
      } catch(NumberFormatException e) {
          error("Invalid number: " + yytext() + " (" + e.getMessage() + ")");
          return null; //unreachable - error throws an exception
      }
  }
  
  private FPNumericLiteralValue parseFP(String text, boolean imaginary) throws Scanner.Exception {
      try { 
          return new FPNumericLiteralValue(yytext(), imaginary);
      } catch(NumberFormatException e) {
          error("Invalid number: " + yytext() + " (" + e.getMessage() + ")");
          return null; //unreachable - error throws an exception
      }
  }
  
  //// Comment nesting /////////////////////////////////////////////////////////
  
  //number of '%}'s expected
  private int bracketCommentNestingDepth = 0;
  //bracket comment string consumed so far
  private StringBuffer bracketCommentBuf = null;
  
  //used to distinguish between bracket comments and bracket help comments
  private boolean isBracketHelpCommentType = false;
  
  //// Comment queue ///////////////////////////////////////////////////////////
  
  private final java.util.Queue<Symbol> commentQueue = new java.util.LinkedList<Symbol>();
  
  public Symbol peekComment() {
      return commentQueue.peek();
  }
  
  public Symbol pollComment() {
      return commentQueue.poll();
  }
  
  public java.util.List<Symbol> pollAllComments() {
      java.util.List<Symbol> allComments = new java.util.ArrayList<Symbol>();
      allComments.addAll(commentQueue);
      commentQueue.clear();
      return allComments;
  }
  
  public boolean hasComment() {
      return !commentQueue.isEmpty();
  }
%}

LineTerminator = \r|\n|\r\n
OtherWhiteSpace = [ \t\f]

Ellipsis = \.\.\.
//NB: require preceding whitespace to prevent '....' case.
EscapedLineTerminator = {OtherWhiteSpace}+{Ellipsis}.*{LineTerminator}

Letter = [a-zA-Z]
Digit = [0-9]
HexDigit = {Digit} | [a-fA-F]
Identifier = ([_$] | {Letter}) ([_$] | {Letter} | {Digit})*
SciExp = [Ee][+-]?{Digit}+
Imaginary = [iIjJ]
IntNumber = {Digit}+
FPNumber = (({Digit}+\.?{Digit}*) | (\.?{Digit}+)){SciExp}?
HexNumber = 0[xX]{HexDigit}+
ImaginaryIntNumber = {Digit}+{Imaginary}
ImaginaryFPNumber = (({Digit}+\.?{Digit}*) | (\.?{Digit}+)){SciExp}?{Imaginary}
ImaginaryHexNumber = 0[xX]{HexDigit}+{Imaginary}

HelpComment=%% | %%[^{].*
OpenBracketHelpComment = %%\{
Comment=% | %[^%{].*
OpenBracketComment = %\{
CloseBracketComment = %\}

ShellCommand=[!].*

String=[']([^'\r\n] | [']['])*[']

//parsing the bit after a DOT
%state FIELD_NAME
//within a bracket comment (i.e. %{)
%xstate COMMENT_NESTING

%%

{EscapedLineTerminator} { commentQueue.add(symbol(ELLIPSIS_COMMENT, yytext().substring(yytext().indexOf("..."), yylength() - 1))); }

{LineTerminator} { return symbol(LINE_TERMINATOR); }
{OtherWhiteSpace} { /* ignore */ }

{IntNumber} { return symbol(INT_NUMBER, parseDecInt(yytext(), false)); }
{FPNumber} { return symbol(FP_NUMBER, parseFP(yytext(), false)); }
{HexNumber} { return symbol(INT_NUMBER, parseHexInt(yytext(), false)); }
{ImaginaryIntNumber} { return symbol(IM_INT_NUMBER, parseDecInt(yytext(), true)); }
{ImaginaryFPNumber} { return symbol(IM_FP_NUMBER, parseFP(yytext(), true)); }
{ImaginaryHexNumber} { return symbol(IM_INT_NUMBER, parseHexInt(yytext(), true)); }

{String} { validateEscapeSequences(yytext()); return symbol(STRING, yytext().substring(1, yylength() - 1)); }

{HelpComment} { return symbol(HELP_COMMENT, yytext()); }
{Comment} { commentQueue.add(symbol(COMMENT, yytext())); }

{OpenBracketHelpComment} { 
    isBracketHelpCommentType = true;
    yybegin(COMMENT_NESTING);
    bracketCommentNestingDepth++;
    bracketCommentBuf = new StringBuffer(yytext());
}
{OpenBracketComment} { 
    isBracketHelpCommentType = false;
    yybegin(COMMENT_NESTING);
    bracketCommentNestingDepth++;
    bracketCommentBuf = new StringBuffer(yytext());
}

<COMMENT_NESTING> {
    [^%]+ { bracketCommentBuf.append(yytext()); }
    % { bracketCommentBuf.append(yytext()); }
    {OpenBracketComment} { bracketCommentNestingDepth++; bracketCommentBuf.append(yytext()); }
    {CloseBracketComment} { 
        bracketCommentNestingDepth--;
        bracketCommentBuf.append(yytext());
        if(bracketCommentNestingDepth == 0) {
            yybegin(YYINITIAL);
            if(isBracketHelpCommentType) {
                return symbol(BRACKET_HELP_COMMENT, bracketCommentBuf.toString());
            } else {
                commentQueue.add(symbol(BRACKET_COMMENT, bracketCommentBuf.toString()));
            }
        }
    }
}

{ShellCommand} { return symbol(SHELL_COMMAND, yytext()); }

\( { return symbol(LPAREN); }
\) { return symbol(RPAREN); }
\[ { return symbol(LSQUARE); }
\] { return symbol(RSQUARE); }
\{ { return symbol(LCURLY); }
\} { return symbol(RCURLY); }

, { return symbol(COMMA); }
; { return symbol(SEMICOLON); }

: { return symbol(COLON); }
@ { return symbol(AT); }

//from http://www.mathworks.com/access/helpdesk/help/techdoc/ref/arithmeticoperators.html
"+" { return symbol(PLUS); }
"-" { return symbol(MINUS); }
"*" { return symbol(MTIMES); }
".*" { return symbol(ETIMES); }
"/" { return symbol(MDIV); }
"./" { return symbol(EDIV); }
"\\" { return symbol(MLDIV); }
".\\" { return symbol(ELDIV); }
"^" { return symbol(MPOW); }
".^" { return symbol(EPOW); }
"'" { return symbol(MTRANSPOSE); }
".'" { return symbol(ARRAYTRANSPOSE); }

//from http://www.mathworks.com/access/helpdesk/help/techdoc/ref/relationaloperators.html
"<=" { return symbol(LE); }
">=" { return symbol(GE); }
"<" { return symbol(LT); }
">" { return symbol(GT); }
"==" { return symbol(EQ); }
"~=" { return symbol(NE); }

//from http://www.mathworks.com/access/helpdesk/help/techdoc/matlab_prog/f0-40063.html
"&" { return symbol(AND); }
"|" { return symbol(OR); }
"~" { return symbol(NOT); }
"&&" { return symbol(SHORTAND); }
"||" { return symbol(SHORTOR); }

"=" { return symbol(ASSIGN); }

<YYINITIAL> {
    //from matlab "iskeyword" function
    break { return symbol(BREAK); }
    case { return symbol(CASE); }
    catch { return symbol(CATCH); }
    classdef { return symbol(CLASSDEF); }
    continue { return symbol(CONTINUE); }
    else { return symbol(ELSE); }
    elseif { return symbol(ELSEIF); }
    end { return symbol(END); }
    for { return symbol(FOR); }
    function { return symbol(FUNCTION); }
    global { return symbol(GLOBAL); }
    if { return symbol(IF); }
    otherwise { return symbol(OTHERWISE); }
    parfor { return symbol(PARFOR); }
    persistent { return symbol(PERSISTENT); }
    return { return symbol(RETURN); }
    switch { return symbol(SWITCH); }
    try { return symbol(TRY); }
    while { return symbol(WHILE); }
    
    //NB: lower precedence than keywords
    {Identifier} { return symbol(IDENTIFIER, yytext()); }
    
    //NB: lower precedence than ellipsis
    \. {
            //NB: have to change the state AFTER calling symbol
            Symbol result = symbol(DOT);
            yybegin(FIELD_NAME);
            return result;
       }
}

<FIELD_NAME> {
    {Identifier} { return symbol(IDENTIFIER, yytext()); }
    
    //NB: lower precedence than ellipsis
    \. { return symbol(DOT); }
}

/* error fallback */
.|\n { error("Illegal character '" + yytext() + "'"); }

<<EOF>> {
            //don't finish scanning if there's an unclosed comment
            if(bracketCommentNestingDepth != 0) {
                error(bracketCommentNestingDepth + " levels of comments not closed");
            }
            return symbol(EOF);
        }