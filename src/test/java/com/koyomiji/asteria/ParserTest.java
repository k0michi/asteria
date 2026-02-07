package com.koyomiji.asteria;

import com.koyomiji.asteria.parser.ParserDriver;
import com.koyomiji.asteria.tree.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
  @Test
  void emptyFunction() {
    String source = "function main() { }";

    AstProgram prog = ParserDriver.parse(source);

    assertEquals(1, prog.functions.size());
    AstFunctionDecl func = prog.functions.get(0);
    assertEquals("main", func.name);
    assertTrue(func.body.statements.isEmpty());
  }

  @Test
  void importAndCall() {
    String source = """
            import stdio;
            
            function main() {
                printf("Hello");
            }
        """;

    AstProgram prog = ParserDriver.parse(source);

    assertEquals(1, prog.imports.size());
    assertEquals("stdio", prog.imports.get(0).moduleName);

    AstFunctionDecl mainFunc = prog.functions.get(0);
    assertEquals("main", mainFunc.name);

    assertEquals(1, mainFunc.body.statements.size());

    assertTrue(mainFunc.body.statements.get(0) instanceof AstExpressionStmt);
    AstExpressionStmt stmt = (AstExpressionStmt) mainFunc.body.statements.get(0);

    assertTrue(stmt.expression instanceof AstCall);
    AstCall call = (AstCall) stmt.expression;
    assertEquals("printf", call.targetName);

    assertEquals(1, call.arguments.size());
    assertTrue(call.arguments.get(0) instanceof AstStringLiteral);
    assertEquals("Hello", ((AstStringLiteral) call.arguments.get(0)).value);
  }
}
