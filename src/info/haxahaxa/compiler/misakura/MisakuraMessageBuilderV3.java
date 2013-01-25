package info.haxahaxa.compiler.misakura;

import info.haxahaxa.compiler.ICompileResult;
import info.haxahaxa.compiler.IMessageBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class MisakuraMessageBuilderV3 implements IMessageBuilder {

	public static void main(String[] args) {
		List<String> strs = new ArrayList<String>();
		strs.add("抽象{0}である{1}({2}内)に直接アクセスすることはできません");
		strs.add("シンボルを見つけられません<br>シンボル:   {0} {1}({3})<br>場所: {4}");
		strs.add("列挙型のswitch caseラベルは列挙型定数の非修飾名である必要があります");

		System.out.println(kata2hira(strs.get(0)));
		System.out.println(hira2kata(strs.get(0)));
	}

	@Override
	public String getCompleteMessage(ICompileResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage(
			Diagnostic<? extends JavaFileObject> diagnostic) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ひらがな→カタカナ変換
	 * 
	 * @param s
	 * @return
	 */
	public static String hira2kata(String str) {
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= 'ぁ' && c <= 'ん') {
				sb.setCharAt(i, (char) (c - 'ぁ' + 'ァ'));
			}
		}
		return sb.toString();
	}

	/**
	 * カタカナ→ひらがな変換
	 * 
	 * @param s
	 * @return
	 */
	public static String kata2hira(String str) {
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= 'ァ' && c <= 'ン') {
				sb.setCharAt(i, (char) (c - 'ァ' + 'ぁ'));
			} else if (c == 'ヵ') {
				sb.setCharAt(i, 'か');
			} else if (c == 'ヶ') {
				sb.setCharAt(i, 'け');
			} else if (c == 'ヴ') {
				sb.setCharAt(i, 'う');
				sb.insert(i + 1, '゛');
				i++;
			}
		}
		return sb.toString();
	}
}
