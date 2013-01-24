package info.haxahaxa.compiler;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Argument implements IArgument {

	/**
	 * -processorpath path 注釈プロセッサを検索する位置を指定する
	 */
	private List<String> ANNOTATION_PROCESSOR_PATH = new ArrayList<String>();
	/**
	 * -d directory 生成されたクラスファイルを格納する位置を指定する
	 */
	private List<String> CLASS_OUTPUT = new ArrayList<String>();
	/**
	 * -classpath path ユーザークラスファイルおよび注釈プロセッサを検索する位置を指定する<br>
	 * -cp path ユーザークラスファイルおよび注釈プロセッサを検索する位置を指定する
	 */
	private List<String> CLASS_PATH = new ArrayList<String>();
	/**
	 * -bootclasspath path ブートストラップクラスファイルの位置を置き換える
	 */
	private List<String> PLATFORM_CLASS_PATH = new ArrayList<String>();
	/**
	 * -s directory 生成されたソースファイルを格納する場所を指定する
	 */
	private List<String> SOURCE_OUTPUT = new ArrayList<String>();
	/**
	 * -sourcepath path 入力ソースファイルを検索する位置を指定する
	 */
	private List<String> SOURCE_PATH = new ArrayList<String>();

	/**
	 * その他のオプション
	 */
	private List<String> options = new ArrayList<String>();

	/**
	 * ソースファイルたち
	 */
	private List<String> sources = new ArrayList<String>();

	/**
	 * Constructor
	 * 
	 * @param args
	 */
	public Argument(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String e = args[i];

			if (e.equals("-g")) {// ok
				options.add(e);
				continue;
			} else if (e.indexOf("-g:") == 0) {// ok
				options.add(e);
				continue;
			} else if (e.equals("-nowarn")) {// ok
				options.add(e);
				continue;
			} else if (e.equals("-verbose")) {// ok
				options.add(e);
				continue;
			} else if (e.equals("-deprecation")) {// ok
				options.add(e);
				continue;
			} else if (e.equals("-cp") || e.equals("-classpath")) {// ok
				for (String classpath : args[i + 1].split(File.pathSeparator)) {
					CLASS_PATH.add(classpath);
				}
				i++;
				continue;
			} else if (e.equals("-sourcepath")) {// ok
				for (String sourcepath : args[i + 1].split(File.pathSeparator)) {
					SOURCE_PATH.add(sourcepath);
				}
				i++;
				continue;
			} else if (e.equals("-bootclasspath")) {// ok
				for (String bootclasspath : args[i + 1]
						.split(File.pathSeparator)) {
					PLATFORM_CLASS_PATH.add(bootclasspath);
				}
				i++;
				continue;
			} else if (e.equals("-extdirs")) {// ok
				options.add(e);
				options.add(args[i + 1]);
				i++;
				continue;
			} else if (e.equals("-endorseddirs")) {// ok
				options.add(e);
				options.add(args[i + 1]);
				i++;
				continue;
			} else if (e.indexOf("-proc:") == 0) {// ok
				options.add(e);
				continue;
			} else if (e.equals("-processor")) {// ok
				options.add(e);
				options.add(args[i + 1]);
				i++;
				continue;
			} else if (e.equals("-processorpath")) {// ok
				for (String processorpath : args[i + 1]
						.split(File.pathSeparator)) {
					ANNOTATION_PROCESSOR_PATH.add(processorpath);
				}
				i++;
				continue;
			} else if (e.equals("-d")) {// ok
				CLASS_OUTPUT.add(args[i + 1]);
				i++;
				continue;
			} else if (e.equals("-s")) {// ok
				SOURCE_OUTPUT.add(args[i + 1]);
				i++;
				continue;
			} else if (e.indexOf("-implicit:") == 0) {// ok
				options.add(e);
				continue;
			} else if (e.equals("-encoding")) {// ok
				options.add(e);
				options.add(args[i + 1]);
				i++;
				continue;
			} else if (e.equals("-source")) {// ok
				options.add(e);
				options.add(args[i + 1]);
				i++;
				continue;
			} else if (e.equals("-target")) {// ok
				options.add(e);
				options.add(args[i + 1]);
				i++;
				continue;
			} else if (e.indexOf("-A") == 0) {// ok
				options.add(e);
				continue;
			} else if (e.indexOf("-X") == 0) {// ok
				options.add(e);
				continue;
			} else if (e.indexOf("-J") == 0) {// ok
				options.add(e);
				continue;
			} else if (e.indexOf("-") == 0) {// ok
				options.add(e);
			} else {// ok
				sources.add(e);
			}
		}
	}

	public List<String> getANNOTATION_PROCESSOR_PATH() {
		return ANNOTATION_PROCESSOR_PATH;
	}

	public List<String> getCLASS_OUTPUT() {
		if (CLASS_OUTPUT.size() == 0) {
			CLASS_OUTPUT.add(".");
		}
		return CLASS_OUTPUT;
	}

	public List<String> getCLASS_PATH() {
		return CLASS_PATH;
	}

	public List<String> getPLATFORM_CLASS_PATH() {
		return PLATFORM_CLASS_PATH;
	}

	public List<String> getSOURCE_OUTPUT() {
		return SOURCE_OUTPUT;
	}

	public List<String> getSOURCE_PATH() {
		return SOURCE_PATH;
	}

	public List<String> getOptions() {
		return options;
	}

	public List<String> getSources() {
		return sources;
	}

	public void dump(PrintStream out) {
		StringBuilder sb = new StringBuilder();
		sb.append("1\t" + ANNOTATION_PROCESSOR_PATH + "\n");
		sb.append("2\t" + CLASS_OUTPUT + "\n");
		sb.append("3\t" + CLASS_PATH + "\n");
		sb.append("4\t" + PLATFORM_CLASS_PATH + "\n");
		sb.append("5\t" + SOURCE_OUTPUT + "\n");
		sb.append("6\t" + SOURCE_PATH + "\n");
		sb.append("7\t" + options + "\n");
		sb.append("8\t" + sources + "\n");
		out.print(sb.toString());
	}
}
