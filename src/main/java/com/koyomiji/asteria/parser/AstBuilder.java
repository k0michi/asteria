package com.koyomiji.asteria.parser;

import com.koyomiji.asteria.tree.*;

import java.util.ArrayList;
import java.util.List;

public class AstBuilder extends AsteriaBaseVisitor<AstNode> {

  @Override
  public AstNode visitProgram(AsteriaParser.ProgramContext ctx) {
    AstProgram program = new AstProgram();

    for (AsteriaParser.ImportStmtContext importCtx : ctx.importStmt()) {
      program.addImport((AstImport) visit(importCtx));
    }

    for (AsteriaParser.FunctionDeclContext funcCtx : ctx.functionDecl()) {
      program.addFunction((AstFunctionDecl) visit(funcCtx));
    }

    return program;
  }

  @Override
  public AstNode visitImportStmt(AsteriaParser.ImportStmtContext ctx) {
    String moduleName = ctx.ID().getText();
    return new AstImport(moduleName);
  }

  @Override
  public AstNode visitFunctionDecl(AsteriaParser.FunctionDeclContext ctx) {
    String name = ctx.ID().getText();

    // TODO: Arguments

    AstBlock body = (AstBlock) visit(ctx.block());
    return new AstFunctionDecl(name, body);
  }

  @Override
  public AstNode visitBlock(AsteriaParser.BlockContext ctx) {
    AstBlock block = new AstBlock();
    for (AsteriaParser.StatementContext stmtCtx : ctx.statement()) {
      block.addStatement(visit(stmtCtx));
    }
    return block;
  }

  @Override
  public AstNode visitExprStmt(AsteriaParser.ExprStmtContext ctx) {
    AstNode expr = visit(ctx.expression());
    return new AstExpressionStmt(expr);
  }

  @Override
  public AstNode visitBlockStmt(AsteriaParser.BlockStmtContext ctx) {
    return visit(ctx.block());
  }

  @Override
  public AstNode visitCallExpr(AsteriaParser.CallExprContext ctx) {
    String targetName = ctx.ID().getText();
    List<AstNode> args = new ArrayList<>();

    if (ctx.argList() != null) {
      for (AsteriaParser.ExpressionContext exprCtx : ctx.argList().expression()) {
        args.add(visit(exprCtx));
      }
    }

    return new AstCall(targetName, args);
  }

  @Override
  public AstNode visitStringExpr(AsteriaParser.StringExprContext ctx) {
    String rawText = ctx.STRING().getText();
    String value = rawText.substring(1, rawText.length() - 1);
    return new AstStringLiteral(value);
  }
}