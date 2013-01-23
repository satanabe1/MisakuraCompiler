package info.haxahaxa.compiler;

import java.io.OutputStream;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public interface ICompileResult {

	boolean isSuccess();

	List<Diagnostic<? extends JavaFileObject>> getDiagnostics();

	void dump(OutputStream out);
}
