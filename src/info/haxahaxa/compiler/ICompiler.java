package info.haxahaxa.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * CompilerAPIのラッパクラス
 * 
 * @author satanabe1
 * 
 */
public interface ICompiler {

	/**
	 * コンパイル結果の取得
	 * 
	 * @return コンパイル結果
	 */
	List<Diagnostic<? extends JavaFileObject>> getDiagnostics();

	/**
	 * コンパイルを実行する
	 * 
	 * @param sourceFile
	 *            コンパイルしたいファイル
	 * @return コンパイル結果<br>
	 * 
	 * @throws IOException
	 *             ファイルが無いとか
	 */
	ICompileResult compile(File sourceFile) throws IOException;

}
