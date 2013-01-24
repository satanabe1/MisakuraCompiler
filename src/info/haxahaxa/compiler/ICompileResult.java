package info.haxahaxa.compiler;

import java.io.OutputStream;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

public interface ICompileResult {

	boolean isSuccess();

	List<Diagnostic<? extends JavaFileObject>> getDiagnostics();
	
	StandardJavaFileManager getFileManager();

	void dump(OutputStream out);
}
