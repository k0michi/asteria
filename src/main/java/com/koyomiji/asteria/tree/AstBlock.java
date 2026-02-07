package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

import java.util.ArrayList;
import java.util.List;

public class AstBlock extends AstNode {
  public final List<AstNode> statements = new ArrayList<>();

  public void addStatement(AstNode stmt) {
    statements.add(stmt);
  }

  @Override
  public <R> R accept(AstVisitor<R> visitor) {
    return visitor.visit(this);
  }
}