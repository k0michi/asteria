package com.koyomiji.asteria;

import com.koyomiji.asteria.tree.*;

import java.util.stream.Collectors;

public class CGenerator implements CVisitor<String> {
  @Override
  public String visit(CProgram node) {
    StringBuilder sb = new StringBuilder();

    for (String inc : node.includes) {
      sb.append("#include <").append(inc).append(">\n");
    }
    sb.append("\n");

    for (CFunction func : node.functions) {
      sb.append(func.accept(this)).append("\n");
    }

    return sb.toString();
  }

  @Override
  public String visit(CFunction node) {
    StringBuilder sb = new StringBuilder();
    sb.append(node.returnType).append(" ").append(node.name).append("()");
    // TODO: Arguments

    sb.append(" ");
    sb.append(node.body.accept(this));
    return sb.toString();
  }

  @Override
  public String visit(CBlock node) {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");

    for (CNode stmt : node.statements) {
      sb.append("    ").append(stmt.accept(this)).append("\n");
    }

    sb.append("}\n");
    return sb.toString();
  }

  @Override
  public String visit(CExpressionStmt node) {
    return node.expression.accept(this) + ";";
  }

  @Override
  public String visit(CCall node) {
    String args = node.args.stream()
            .map(arg -> arg.accept(this))
            .collect(Collectors.joining(", "));

    return node.name + "(" + args + ")";
  }

  @Override
  public String visit(CStringLiteral node) {
    // TODO: Escape special characters
    return "\"" + node.value + "\"";
  }
}