package com.koyomiji.asteria.parser;

import com.koyomiji.asteria.tree.AstProgram;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Path;

public class ParserDriver {
  public static AstProgram parse(Path sourceFile) throws IOException {
  var input = CharStreams.fromPath(sourceFile);
  AsteriaLexer lexer = new AsteriaLexer(input);
  CommonTokenStream tokens = new CommonTokenStream(lexer);

  AsteriaParser parser = new AsteriaParser(tokens);
  AsteriaParser.ProgramContext tree = parser.program();

  if (parser.getNumberOfSyntaxErrors() > 0) {
    throw new RuntimeException("Syntax Error in " + sourceFile);
  }

  AstBuilder builder = new AstBuilder();
  return (AstProgram) builder.visit(tree);
}

  public static AstProgram parse(String sourceCode) {
    var input = CharStreams.fromString(sourceCode);
    AsteriaLexer lexer = new AsteriaLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AsteriaParser parser = new AsteriaParser(tokens);

    parser.removeErrorListeners();
    parser.addErrorListener(new BaseErrorListener() {
      @Override
      public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                              int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new RuntimeException("Syntax Error at line " + line + ":" + charPositionInLine + " " + msg);
      }
    });

    AsteriaParser.ProgramContext tree = parser.program();

    AstBuilder builder = new AstBuilder();
    return (AstProgram) builder.visit(tree);
  }
}
