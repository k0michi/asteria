package com.koyomiji.asteria.compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectContext {
    private final Map<String, ModuleDef> modules = new HashMap<>();

    public ProjectContext() {
        loadStdLib();
    }

    private void loadStdLib() {
        ModuleDef stdio = new ModuleDef("stdio", "stdio.h");
        stdio.addExport(new FunctionSignature("printf", "printf", "void", List.of("string")));
        modules.put("stdio", stdio);
    }

    public void registerModule(ModuleDef module) {
        modules.put(module.moduleName, module);
    }

    public ModuleDef getModule(String name) {
        return modules.get(name);
    }

    public boolean hasMainFunction() {
        ModuleDef mainModule = modules.get("main");
        return mainModule != null && mainModule.exports.containsKey("main");
    }

    public String getMainFunctionCName() {
        if (!hasMainFunction()) return null;
        return modules.get("main").exports.get("main").cName();
    }
}