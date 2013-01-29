package info.haxahaxa.compiler.misakura;

/**
 * 日本語をみさくら語に変換するためのクラス
 * 
 * @author satanabe1
 * 
 */
public interface MisakuraConverter {
	/**
	 * 日本語をみさくら語に変換する
	 * 
	 * @param msg
	 *            日本語
	 * @return みさくら語
	 */
	String jap2misakura(String msg);

	/**
	 * 日本語をみさくら語に変換する
	 * 
	 * @param msg
	 *            日本語
	 * @param kana2hira
	 *            カタカナをひらがなに変換してからみさくら語に変換する場合true<br>
	 * @return みさくら語
	 */
	String jap2misakura(String msg, boolean kana2hira);
}
