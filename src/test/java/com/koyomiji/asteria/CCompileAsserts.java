package com.koyomiji.asteria;

import org.junit.jupiter.api.Assertions;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public final class CCompileAsserts {
  private static final String TEMP_DIR_PREFIX = "asteria-";
  private static final String C_FILE_NAME = "test.c";

  private CCompileAsserts() {}

  public static void assertCompiles(String cSource) {
    String cc = findCompiler();
    try {
      Path dir = Files.createTempDirectory(TEMP_DIR_PREFIX);
      Path c = dir.resolve(C_FILE_NAME);
      Files.writeString(c, cSource, StandardCharsets.UTF_8);

      List<String> cmd = List.of(
              cc, "-std=c11", "-Wall", "-Wextra",
              "-fsyntax-only",
              c.toAbsolutePath().toString()
      );

      ExecResult r = exec(cmd, dir);
      if (r.exitCode != 0) fail(cc, cmd, r);

    } catch (Exception e) {
      Assertions.fail("Compile check error: " + e);
    }
  }

  public static RunResult run(String cSource, String... programArgs) {
    String cc = findCompiler();
    try {
      Path dir = Files.createTempDirectory(TEMP_DIR_PREFIX);
      Path c = dir.resolve(C_FILE_NAME);
      Files.writeString(c, cSource, StandardCharsets.UTF_8);

      Path exe = dir.resolve(isWindows() ? "a.exe" : "a.out");

      List<String> compile = new ArrayList<>();
      compile.add(cc);
      compile.addAll(List.of("-std=c11", "-Wall", "-Wextra"));
      compile.add(c.toAbsolutePath().toString());
      compile.add("-o");
      compile.add(exe.toAbsolutePath().toString());

      ExecResult cr = exec(compile, dir);
      if (cr.exitCode != 0) fail(cc, compile, cr);

      List<String> runCmd = new ArrayList<>();
      runCmd.add(exe.toAbsolutePath().toString());
      runCmd.addAll(Arrays.asList(programArgs));

      ExecResult rr = exec(runCmd, dir);
      return new RunResult(rr.exitCode, rr.stdout, rr.stderr);

    } catch (Exception e) {
      Assertions.fail("Run error: " + e);
      return null;
    }
  }

  public static void assertStdoutEquals(String expected, String cSource, String... programArgs) {
    RunResult r = run(cSource, programArgs);
    if (r.exitCode != 0) {
      Assertions.fail("Program exited non-zero: " + r.exitCode + "\n[stderr]\n" + r.stderr);
    }
    Assertions.assertEquals(expected, r.stdout);
  }

  public record RunResult(int exitCode, String stdout, String stderr) {}

  private record ExecResult(int exitCode, String stdout, String stderr) {}

  private static ExecResult exec(List<String> cmd, Path dir) throws Exception {
    Process p = new ProcessBuilder(cmd).directory(dir.toFile()).start();
    String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    String err = new String(p.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
    int code = p.waitFor();
    return new ExecResult(code, out, err);
  }

  private static void fail(String cc, List<String> cmd, ExecResult r) {
    Assertions.fail("Failed with " + cc + "\ncmd: " + String.join(" ", cmd)
            + "\n[stdout]\n" + r.stdout + "\n[stderr]\n" + r.stderr);
  }

  private static String findCompiler() {
    if (exists("clang")) return "clang";
    if (exists("gcc")) return "gcc";
    Assertions.fail("No C compiler found on PATH (clang/gcc).");
    return null;
  }

  private static boolean exists(String exe) {
    try { return new ProcessBuilder(exe, "--version").start().waitFor() == 0; }
    catch (Exception e) { return false; }
  }

  private static boolean isWindows() {
    return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
  }
}
