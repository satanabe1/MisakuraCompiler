package info.haxahaxa.compiler;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * コンパイル結果からメッセージを生成するクラス
 * 
 * @author satanabe1
 * 
 */
public interface IMessageBuilder {

	/**
	 * 成功メッセージを取得する
	 * 
	 * @return 成功メッセージ
	 */
	String getCompleteMessage(ICompileResult result);

	/**
	 * 失敗メッセージを取得する
	 * 
	 * @param diagnostic
	 *            エラー情報
	 * @return 失敗メッセージ
	 * @throws Exception
	 *             メッセージの整形に失敗したとか
	 */
	String getErrorMessage(Diagnostic<? extends JavaFileObject> diagnostic)
			throws Exception;
}
