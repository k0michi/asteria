package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.CVisitor;

public class CStringLiteral extends CNode {
  public final String value;

  public CStringLiteral(String value) {
    this.value = value;
  }

  @Override
  public <R> R accept(CVisitor<R> visitor) {
    return visitor.visit(this);
  }
}