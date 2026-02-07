package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

import java.util.List;

public class AstCall extends AstNode {
  public final String targetName;
  public final List<AstNode> arguments;

  public AstCall(String targetName, List<AstNode> arguments) {
    this.targetName = targetName;
    this.arguments = arguments;
  }

  @Override
  public <R> R accept(AstVisitor<R> visitor) {
    return visitor.visit(this);
  }
}