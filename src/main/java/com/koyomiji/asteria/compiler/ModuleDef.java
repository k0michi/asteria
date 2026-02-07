package com.koyomiji.asteria.compiler;

import java.util.*;

public class ModuleDef {public final String moduleName;
    public final String headerFile;
    public final Map<String, FunctionSignature> exports = new HashMap<>();

    public ModuleDef(String moduleName, String headerFile) {
        this.moduleName = moduleName;
        this.headerFile = headerFile;
    }

    public void addExport(FunctionSignature sig) {
        exports.put(sig.sourceName(), sig);
    }
}