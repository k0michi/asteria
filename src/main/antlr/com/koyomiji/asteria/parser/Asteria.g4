grammar Asteria;

program
    : importStmt* functionDecl* EOF
    ;

importStmt
    : 'import' ID ';'
    ;

functionDecl
    : type ID '(' paramList? ')' block
    ;

type
    : 'void'
    | 'int'
    | 'string'
    | ID
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
    | INT                  # IntExpr
    ;

argList
    : expression (',' expression)*
    ;

IMPORT   : 'import';
VOID   : 'void';
TYPE_INT : 'int';
TYPE_STRING : 'string';

ID     : [a-zA-Z_] [a-zA-Z0-9_]* ;
STRING : '"' (~["\r\n] | '\\"')* '"' ;
INT    : [0-9]+ ;
WS     : [ \t\r\n]+ -> skip ;
COMMENT: '//' ~[\r\n]* -> skip ;