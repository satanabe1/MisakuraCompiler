package info.haxahaxa.compiler;

import java.io.OutputStream;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

/**
 * {@link Diagnostic}のラッパのようなクラス
 * 
 * @author satanabe1
 * 
 */
public interface ICompileResult {

	/**
	 * コンパイル成否を調べる
	 * 
	 * @return コンパイル成否
	 */
	boolean isSuccess();

	/**
	 * コンパイル結果を取得する
	 * 
	 * @return コンパイル結果
	 */
	List<Diagnostic<? extends JavaFileObject>> getDiagnostics();

	/**
	 * コンパイルに使った{@link StandardJavaFileManager}を取得する
	 * 
	 * @return コンパイル環境
	 */
	StandardJavaFileManager getFileManager();

	/**
	 * 結果をどこかにdumpする
	 * 
	 * @param out
	 *            dump先 {@link System#out}とか {@link System#err}とか
	 */
	void dump(OutputStream out);
}
