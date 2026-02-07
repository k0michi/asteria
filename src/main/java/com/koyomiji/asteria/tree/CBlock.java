package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.CVisitor;

import java.util.ArrayList;
import java.util.List;

public class CBlock extends CNode {
  public final List<CNode> statements = new ArrayList<>();

  public void addStatement(CNode stmt) {
    statements.add(stmt);
  }

  @Override
  public <R> R accept(CVisitor<R> visitor) {
    return visitor.visit(this);
  }
}
