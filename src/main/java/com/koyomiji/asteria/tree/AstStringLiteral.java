package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

public class AstStringLiteral extends AstNode {
  public final String value;

  public AstStringLiteral(String value) {
    this.value = value;
  }

  @Override
  public <R> R accept(AstVisitor<R> visitor) {
    return visitor.visit(this);
  }
}