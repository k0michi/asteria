package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.CVisitor;

import java.util.List;

public class CCall extends CNode {
  public final String name;
  public final List<CNode> args;

  public CCall(String name, List<CNode> args) {
    this.name = name;
    this.args = args;
  }

  @Override
  public <R> R accept(CVisitor<R> visitor) {
    return visitor.visit(this);
  }
}