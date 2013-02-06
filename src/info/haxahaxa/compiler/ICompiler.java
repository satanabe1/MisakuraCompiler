package info.haxahaxa.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public interface ICompiler {

	List<Diagnostic<? extends JavaFileObject>> getDiagnostics();

	ICompileResult compile(File sourceFile) throws IOException;

}
