package info.haxahaxa.compiler;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public interface IErrorMessage {

	String getMessage(Diagnostic<? extends JavaFileObject> diagnostic)
			throws Exception;
}
