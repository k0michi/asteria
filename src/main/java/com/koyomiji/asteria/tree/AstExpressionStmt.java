package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

public class AstExpressionStmt extends AstNode {
  public final AstNode expression;

  public AstExpressionStmt(AstNode expression) {
    this.expression = expression;
  }

  @Override
  public <R> R accept(AstVisitor<R> visitor) {
    return visitor.visit(this);
  }
}