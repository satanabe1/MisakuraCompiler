package info.haxahaxa.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class MisakuraCompiler {

	public static void main(String[] args) {
		String srcFilePath = "hoge" + File.separator + "Hoge.java";
		args = new String[] {
				"-g:{lines,vars,source}",//
				"-nowarn",//
				// "-verbose",//
				"-deprecation",//
				// "-cp","bin",
				"-classpath",
				"classes" + File.pathSeparator + "hoge",//
				"-sourcepath",
				".",//
				"-bootclasspath",
				"/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Classes/classes.jar",//
				"-extdirs", "ext",//
				"-endorseddirs", "endor",//
				// "-proc:{none,only}",//
				// "-processor", "<class2>,<class3>",//
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
				srcFilePath

		};

		try {
			Argument argument = new Argument(args);
			ICompiler compiler = new Compiler(argument);
			for (String sourceFilePath : argument.getSources()) {
				ICompileResult result = compiler.compile(new File(
						sourceFilePath));
				if (!result.isSuccess()) {
					printCompileError(result);
				}
				// result.dump(System.out);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("---------------------------------");
			help();
		}
	}

	private static void printCompileError(ICompileResult result) {
		IErrorMessage misakura = MisakuraMessage.getInstance();
		for (Diagnostic<? extends JavaFileObject> report : result
				.getDiagnostics()) {
			try {
				String mword = misakura.getMessage(report);
				System.err.println(mword);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
