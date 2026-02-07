package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

public abstract class AstNode {
  public abstract <R> R accept(AstVisitor<R> visitor);
}