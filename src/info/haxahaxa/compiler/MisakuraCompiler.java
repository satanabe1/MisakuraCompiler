package info.haxahaxa.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * 　メモ． まだ，デフォルトのsourcepathとclasspathの読み込みがうまくいかない
 * 
 * @author satanabe1
 * 
 */

public class MisakuraCompiler {

	/**
	 * EntryPoint<br>
	 * 開発用．しばらくデバッグモード．
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
//		args = getTestargs();
		try {
			Argument argument = new Argument(args);
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

	public static String[] getTestargs() {
		String srcFilePath = "hoge" + File.separator + "HogeF.java";
		String[] testargs = new String[] {
				"-g:{lines,vars,source}",//
				"-nowarn",//
				// "-verbose",//
				"-deprecation",//
				// "-cp","bin",
				// "-classpath",
				// "classes" + File.pathSeparator + "hoge",//
				"-sourcepath",
				".",//
				"-bootclasspath",
				"/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Classes/classes.jar",//
				"-extdirs", "ext",//
				"-endorseddirs", "endor",//
				// "-proc:{none,only}",//
				// "-processor", "class2",//
				"-processorpath", "procpath",//
				"-d", "tmp",//
				"-s", "src",//
				// "-implicit:{none,class}",//
				"-encoding", "utf-8",//
				"-source", "1.6",//
				"-target", "1.6",//
				// "-version",
				// "help",
				// "-Akey[=value]",//
				// "-X",//
				// "-JD",//
				srcFilePath };
		return testargs;
	}

	private static void printSuccess(ICompileResult result) {
		IMessageBuilder misakura = MisakuraMessageBuilder.getInstance();
		System.out.println(misakura.getCompleteMessage(result));
	}

	private static void printCompileError(ICompileResult result) {
		IMessageBuilder misakura = MisakuraMessageBuilder.getInstance();
		int errorcount = 0;
		for (Diagnostic<? extends JavaFileObject> diagnostic : result
				.getDiagnostics()) {
			errorcount++;
			try {
				String mword = misakura.getErrorMessage(diagnostic);
				System.err.println(mword);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("えらぁあああ あぉは " + errorcount + " これしゅぅぅぅ");
	}

	private static void help() {
		try {
			Process javacProc = Runtime.getRuntime().exec(
					new String[] { "javac", "-J-Duser.language=en", "-help" });

			BufferedReader br = new BufferedReader(new InputStreamReader(
					javacProc.getErrorStream()));
			String str = null;
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
