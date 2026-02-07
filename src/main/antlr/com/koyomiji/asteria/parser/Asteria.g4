grammar Asteria;

program
    : importStmt* functionDecl* EOF
    ;

importStmt
    : 'import' ID ';'
    ;

functionDecl
    : 'function' ID '(' paramList? ')' block
    ;

paramList
    : ID (',' ID)*
    ;

block
    : '{' statement* '}'
    ;

statement
    : block              # BlockStmt
    | expression ';'     # ExprStmt
    ;

expression
    : ID '(' argList? ')'  # CallExpr
    | STRING               # StringExpr
    ;

argList
    : expression (',' expression)*
    ;

IMPORT   : 'import';
FUNCTION : 'function';

ID       : [a-zA-Z_] [a-zA-Z0-9_]* ;
STRING   : '"' (~["\r\n] | '\\"')* '"' ;
WS       : [ \t\r\n]+ -> skip ;
COMMENT  : '//' ~[\r\n]* -> skip ;