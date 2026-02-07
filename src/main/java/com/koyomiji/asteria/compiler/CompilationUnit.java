package com.koyomiji.asteria.compiler;

import com.koyomiji.asteria.tree.AstProgram;

public class CompilationUnit {
    public final String moduleName;
    public final AstProgram ast;
    
    public CompilationUnit(String moduleName, AstProgram ast) {
        this.moduleName = moduleName;
        this.ast = ast;
    }
}