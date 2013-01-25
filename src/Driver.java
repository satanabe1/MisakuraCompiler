import info.haxahaxa.compiler.misakura.Misakura;

import java.io.File;

public class Driver {

	public static void main(String[] args) {
		Misakura.main(getTestargs());

	}

	public static String[] getTestargs() {
		String srcFilePath = "hoge" + File.separator + "HogeF.java";
		String[] testargs = new String[] {
				// "-g:{lines,vars,source}",//
				"-nowarn",//
				// "-verbose",//
				"-deprecation",//
				// "-cp", "bin",
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
}
