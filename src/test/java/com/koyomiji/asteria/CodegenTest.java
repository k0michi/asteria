package com.koyomiji.asteria;

import com.koyomiji.asteria.compiler.AsteriaCompiler;
import com.koyomiji.asteria.compiler.CompilationUnit;
import com.koyomiji.asteria.parser.ParserDriver;
import com.koyomiji.asteria.tree.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class CodegenTest {
  @Test void helloWorld() {
    AstProgram ast = new AstProgram();
    ast.addImport(new AstImport("stdio"));
    AstBlock mainBody = new AstBlock();
    mainBody.addStatement(new AstExpressionStmt(
            new AstCall("printf", List.of(new AstStringLiteral("Hello, World!")))
    ));
    ast.addFunction(new AstFunctionDecl("main", mainBody));
    CompilationUnit unit = new CompilationUnit("main", ast);

    AsteriaCompiler compiler = new AsteriaCompiler();
    var artifact = compiler.compile(List.of(unit));
    CCompileAsserts.assertStdoutEquals("Hello, World!", artifact);
  }

  @Test void helloWorldFromSource() {
    String source = """
            import stdio;
            
            function main() {
                printf("Hello, World!");
            }
            """;
    CompilationUnit unit = new CompilationUnit("main", ParserDriver.parse(source));

    AsteriaCompiler compiler = new AsteriaCompiler();
    var artifact = compiler.compile(List.of(unit));
    CCompileAsserts.assertStdoutEquals("Hello, World!", artifact);
  }

  @Test void multipleStatements() {
    AstProgram ast = new AstProgram();
    ast.addImport(new AstImport("stdio"));
    AstBlock mainBody = new AstBlock();
    mainBody.addStatement(new AstExpressionStmt(
            new AstCall("printf", List.of(new AstStringLiteral("Hello, World!")))
    ));
    mainBody.addStatement(new AstExpressionStmt(
            new AstCall("printf", List.of(new AstStringLiteral("Hello, World!")))
    ));
    ast.addFunction(new AstFunctionDecl("main", mainBody));
    CompilationUnit unit = new CompilationUnit("main", ast);

    AsteriaCompiler compiler = new AsteriaCompiler();
    var artifact = compiler.compile(List.of(unit));
    CCompileAsserts.assertStdoutEquals("Hello, World!Hello, World!", artifact);
  }

  @Test void external() {
    AstProgram utilsAst = new AstProgram();
    utilsAst.addImport(new AstImport("stdio"));
    AstBlock greetBody = new AstBlock();
    greetBody.addStatement(new AstExpressionStmt(
            new AstCall("printf", List.of(new AstStringLiteral("Hello from Utils!")))
    ));
    utilsAst.addFunction(new AstFunctionDecl("greet", greetBody));
    CompilationUnit utilsUnit = new CompilationUnit("utils", utilsAst);

    AstProgram mainAst = new AstProgram();
    mainAst.addImport(new AstImport("utils"));
    AstBlock mainBody = new AstBlock();
    mainBody.addStatement(new AstExpressionStmt(
            new AstCall("greet", Collections.emptyList())
    ));
    mainAst.addFunction(new AstFunctionDecl("main", mainBody));
    CompilationUnit mainUnit = new CompilationUnit("main", mainAst);

    AsteriaCompiler compiler = new AsteriaCompiler();
    var artifact = compiler.compile(List.of(utilsUnit, mainUnit));
    CCompileAsserts.assertStdoutEquals("Hello from Utils!", artifact);
  }
}
