package com.koyomiji.asteria.compiler;

import com.koyomiji.asteria.AstVisitor;
import com.koyomiji.asteria.tree.*;

import java.util.*;

public class CGenerator implements AstVisitor<String> {
    private final ProjectContext context;
    private final String currentModuleName;

    private final Map<String, FunctionSignature> visibleFunctions = new HashMap<>();
    private final Set<String> headers = new LinkedHashSet<>();

    public CGenerator(ProjectContext context, String currentModuleName) {
        this.context = context;
        this.currentModuleName = currentModuleName;
    }

    public Set<String> getRequiredHeaders() {
        return headers;
    }

    @Override
    public String visit(AstProgram node) {
        StringBuilder bodyBuffer = new StringBuilder();

        for (AstImport imp : node.imports) {
            imp.accept(this);
        }

        for (AstFunctionDecl func : node.functions) {
            bodyBuffer.append(func.accept(this)).append("\n");
        }

        return bodyBuffer.toString();
    }

    @Override
    public String visit(AstImport node) {
        ModuleDef target = context.getModule(node.moduleName);
        if (target == null) throw new RuntimeException("Unknown module: " + node.moduleName);

        visibleFunctions.putAll(target.exports);

        if (target.headerFile != null) {
            headers.add(target.headerFile);
        }
        return "";
    }

    @Override
    public String visit(AstFunctionDecl node) {
        var module = context.getModule(currentModuleName);
        var sig = module.exports.get(node.name);

        // TODO: Mangling
        return node.returnType + " " + sig.cName() + "() " + node.body.accept(this);
    }

    @Override
    public String visit(AstCall node) {
        FunctionSignature sig = visibleFunctions.get(node.targetName);
        if (sig == null) throw new RuntimeException("Undefined function: " + node.targetName);

        List<String> args = new ArrayList<>();
        for (AstNode arg : node.arguments) {
            args.add(arg.accept(this));
        }
        
        return sig.cName() + "(" + String.join(", ", args) + ")";
    }

    @Override
    public String visit(AstBlock node) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (AstNode stmt : node.statements) sb.append("    ").append(stmt.accept(this)).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
    @Override public String visit(AstExpressionStmt node) { return node.expression.accept(this) + ";"; }
    @Override public String visit(AstStringLiteral node) { return "\"" + node.value + "\""; }
}