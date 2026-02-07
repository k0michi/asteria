package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

public class AstFunctionDecl extends AstNode {
  public final String returnType;
  public final String name;
  public final AstBlock body;

  public AstFunctionDecl(String returnType, String name, AstBlock body) {
    this.returnType = returnType;
    this.name = name;
    this.body = body;
  }

  @Override
  public <R> R accept(AstVisitor<R> visitor) {
    return visitor.visit(this);
  }
}