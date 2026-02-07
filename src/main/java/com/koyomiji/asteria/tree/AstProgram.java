package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

import java.util.ArrayList;
import java.util.List;

public class AstProgram extends AstNode {
  public final List<AstFunctionDecl> functions = new ArrayList<>();

  public void addFunction(AstFunctionDecl func) {
    functions.add(func);
  }

  @Override
  public <R> R accept(AstVisitor<R> visitor) {
    return visitor.visit(this);
  }
}