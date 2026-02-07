package com.koyomiji.asteria.tree;

import com.koyomiji.asteria.AstVisitor;

public class AstImport extends AstNode {
    public final String moduleName;

    public AstImport(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public <R> R accept(AstVisitor<R> visitor) {
        return visitor.visit(this);
    }
}