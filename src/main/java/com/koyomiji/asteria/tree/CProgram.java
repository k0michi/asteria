package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.CVisitor;

import java.util.ArrayList;
import java.util.List;

public class CProgram extends CNode {
  public final List<String> includes = new ArrayList<>();
  public final List<CFunction> functions = new ArrayList<>();

  @Override
  public <R> R accept(CVisitor<R> visitor) {
    return visitor.visit(this);
  }
}