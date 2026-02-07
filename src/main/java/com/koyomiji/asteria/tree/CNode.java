package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.CVisitor;

public abstract class CNode {
  public abstract <R> R accept(CVisitor<R> visitor);
}
