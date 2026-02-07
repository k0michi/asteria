package com.koyomiji.asteria.compiler;

import java.util.*;

public class AsteriaCompiler {
    public String compile(List<CompilationUnit> units) {
        ProjectContext context = new ProjectContext();

        DeclarationCollector collector = new DeclarationCollector(context);
        for (CompilationUnit unit : units) {
            collector.process(unit);
        }

        StringBuilder sbPrototypes = new StringBuilder();
        StringBuilder sbBodies = new StringBuilder();

        Set<String> allHeaders = new LinkedHashSet<>();

        for (CompilationUnit unit : units) {
            CGenerator generator = new CGenerator(context, unit.moduleName);

            String bodyCode = unit.ast.accept(generator);

            allHeaders.addAll(generator.getRequiredHeaders());

            ModuleDef mod = context.getModule(unit.moduleName);
            for (FunctionSignature sig : mod.exports.values()) {
                sbPrototypes.append("void ")
                        .append(sig.cName())
                        .append("();\n");
            }

            sbBodies.append("// Module: ").append(unit.moduleName).append("\n");
            sbBodies.append(bodyCode).append("\n\n");
        }

        StringBuilder finalOutput = new StringBuilder();

        finalOutput.append("/* --- Includes --- */\n");
        finalOutput.append("#include <stdio.h>\n");
        for (String h : allHeaders) {
            finalOutput.append("#include <").append(h).append(">\n");
        }

        finalOutput.append("\n/* --- Runtime Definitions --- */\n");

        finalOutput.append("\n/* --- Prototypes --- */\n");
        finalOutput.append(sbPrototypes);

        finalOutput.append("\n/* --- Implementations --- */\n");
        finalOutput.append(sbBodies);

        if (context.hasMainFunction()) {
            finalOutput.append("\n/* --- Entry Point --- */\n");
            finalOutput.append("int main(int argc, char** argv) {\n");

            String userMainInfo = context.getMainFunctionCName();
            finalOutput.append("    ").append(userMainInfo).append("();\n");

            finalOutput.append("    return 0;\n");
            finalOutput.append("}\n");
        }

        return finalOutput.toString();
    }
}