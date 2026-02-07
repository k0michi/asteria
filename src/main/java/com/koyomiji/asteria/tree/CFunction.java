package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.CVisitor;

public class CFunction extends CNode {
  public final String returnType;
  public final String name;
  public final CBlock body;

  public CFunction(String returnType, String name, CBlock body) {
    this.returnType = returnType;
    this.name = name;
    this.body = body;
  }

  @Override
  public <R> R accept(CVisitor<R> visitor) {
    return visitor.visit(this);
  }
}
