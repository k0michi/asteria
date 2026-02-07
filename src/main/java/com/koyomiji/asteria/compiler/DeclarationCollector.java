package com.koyomiji.asteria.compiler;

import com.koyomiji.asteria.AstVisitor;
import com.koyomiji.asteria.tree.*;

import java.util.Collections;

public class DeclarationCollector implements AstVisitor<Void> {
    private final ProjectContext context;
    private ModuleDef currentModule;

    public DeclarationCollector(ProjectContext context) {
        this.context = context;
    }

    public void process(CompilationUnit unit) {
        currentModule = new ModuleDef(unit.moduleName, null);
        unit.ast.accept(this);
        context.registerModule(currentModule);
    }

    @Override
    public Void visit(AstProgram node) {
        for (AstFunctionDecl func : node.functions) {
            func.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(AstFunctionDecl node) {String cName;

        if (currentModule.moduleName.equals("main") && node.name.equals("main")) {
            cName = "_user_main";
        } else {
            cName = currentModule.moduleName + "_" + node.name;
        }

        FunctionSignature sig = new FunctionSignature(node.name, cName, "void", Collections.emptyList());
        currentModule.addExport(sig);
        return null;
    }

    @Override public Void visit(AstBlock node) { return null; }
    @Override public Void visit(AstExpressionStmt node) { return null; }
    @Override public Void visit(AstCall node) { return null; }
    @Override public Void visit(AstStringLiteral node) { return null; }
    @Override public Void visit(AstImport node) { return null; }
}