package com.koyomiji.asteria;

import com.koyomiji.asteria.tree.*;

public interface AstVisitor<R> {
  R visit(AstProgram node);
  R visit(AstFunctionDecl node);
  R visit(AstBlock node);
  R visit(AstExpressionStmt node);
  R visit(AstCall node);
  R visit(AstStringLiteral node);
  R visit(AstImport node);
}