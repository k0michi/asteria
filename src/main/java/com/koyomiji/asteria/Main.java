package com.koyomiji.asteria;

import com.koyomiji.asteria.tree.*;

import java.util.*;

public class Main {
  public static void main(String[] args) {
    CStringLiteral cStr = new CStringLiteral("Hello World");

    CCall cPrintf = new CCall("printf", Arrays.asList(cStr));

    CExpressionStmt cStmt = new CExpressionStmt(cPrintf);

    CBlock cBlock = new CBlock();
    cBlock.addStatement(cStmt);

    CFunction cMain = new CFunction("void", "main", cBlock);

    CProgram cProgram = new CProgram();
    cProgram.functions.add(cMain);
    cProgram.includes.add("stdio.h");

    CGenerator generator = new CGenerator();
    String cSourceCode = cProgram.accept(generator);

    System.out.println("=== Generated C Code ===");
    System.out.println(cSourceCode);
    System.out.println("========================");
  }
}
