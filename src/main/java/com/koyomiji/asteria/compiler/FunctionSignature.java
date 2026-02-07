package com.koyomiji.asteria.compiler;

import java.util.List;

public record FunctionSignature(
    String sourceName,
    String cName,
    String returnType,
    List<String> paramTypes
) {}