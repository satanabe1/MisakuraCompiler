package info.haxahaxa.compiler;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public interface IMessageBuilder {

	String getCompleteMessage(ICompileResult result);
	
	String getErrorMessage(Diagnostic<? extends JavaFileObject> diagnostic)
			throws Exception;
}
