package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.CVisitor;

public class CExpressionStmt extends CNode {
  public final CNode expression;

  public CExpressionStmt(CNode expression) {
    this.expression = expression;
  }

  @Override
  public <R> R accept(CVisitor<R> visitor) {
    return visitor.visit(this);
  }
}
