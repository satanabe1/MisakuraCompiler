package info.haxahaxa.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * EntoryPoint<br>
 * 内部でjavacを呼び，結果をみさくら語で表示する
 * 
 * @author satanabe1
 * 
 */

public class MisakuraCompiler {

	/**
	 * EntryPoint<br>
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		Argument argument = new Argument(args);
		if (argument.getSources().size() == 0) {
			help();
		} else {
			compile(argument);
		}
	}

	/**
	 * 与えられた引数に基づいてコンパイルを行う
	 * 
	 * @param argument
	 *            コンパイル引数
	 */
	private static void compile(Argument argument) {
		try {
			ICompiler compiler = new Compiler(argument);
			for (String sourceFilePath : argument.getSources()) {
				ICompileResult result = compiler.compile(new File(
						sourceFilePath));
				if (result.isSuccess()) {
					printSuccess(result);
				} else {
					printCompileError(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("---------------------------------");
			help();
		}
	}

	/**
	 * 成功メッセージを表示する
	 * 
	 * @param result
	 *            コンパイル結果<br>
	 *            {@link ICompiler#compile(File)}から取得する
	 */
	private static void printSuccess(ICompileResult result) {
		IMessageBuilder misakura = MisakuraMessageBuilder.getInstance();
		System.out.println(misakura.getCompleteMessage(result));
	}

	/**
	 * コンパイルエラーを表示する
	 * 
	 * @param result
	 *            コンパイル結果<br>
	 *            {@link ICompiler#compile(File)}から取得する
	 */
	private static void printCompileError(ICompileResult result) {
		IMessageBuilder misakura = MisakuraMessageBuilder.getInstance();
		int errorcount = 0;
		for (Diagnostic<? extends JavaFileObject> diagnostic : result
				.getDiagnostics()) {
			errorcount++;
			try {
				System.err.println(misakura.getErrorMessage(diagnostic));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.err.println("えらぁあああ あぉは " + errorcount + " これしゅぅぅぅ");
	}

	/**
	 * ヘルプを表示する
	 */
	private static void help() {
		try {
			Process javacProc = Runtime.getRuntime().exec(
					new String[] { "javac", "-J-Duser.language=en", "-help" });

			BufferedReader br = new BufferedReader(new InputStreamReader(
					javacProc.getErrorStream()));
			String str = null;
			while ((str = br.readLine()) != null) {
				System.err.println(str.replaceAll("javac", "misakura"));
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
