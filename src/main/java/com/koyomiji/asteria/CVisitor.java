package com.koyomiji.asteria;

import com.koyomiji.asteria.tree.*;

public interface CVisitor<R> {
  R visit(CProgram node);
  R visit(CFunction node);
  R visit(CBlock node);
  R visit(CExpressionStmt node);
  R visit(CCall node);
  R visit(CStringLiteral node);
}