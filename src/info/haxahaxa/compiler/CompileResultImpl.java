package info.haxahaxa.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

public class CompileResultImpl implements ICompileResult {

	private boolean result;
	private List<Diagnostic<? extends JavaFileObject>> diDiagnostics;
	private StandardJavaFileManager fileManager;
	private Iterable<String> options;
	private Iterable<? extends JavaFileObject> sources;
	private Map<JavaFileObject, List<String>> codes;

	public CompileResultImpl(boolean result,
			StandardJavaFileManager fileManager,
			List<Diagnostic<? extends JavaFileObject>> diagnostics,
			Iterable<String> options, Iterable<? extends JavaFileObject> sources) {
		// TODO Auto-generated constructor stub
		this.result = result;
		this.fileManager = fileManager;
		this.diDiagnostics = diagnostics;
		this.options = options;
		this.sources = sources;

		codes = new HashMap<JavaFileObject, List<String>>();
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
		// TODO Auto-generated method stub
		return diDiagnostics;
	}

	@Override
	public StandardJavaFileManager getFileManager() {
		return fileManager;
	}

	@Override
	public void dump(OutputStream out) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		printWriter.println("vvvvvvvvvvvvvvvvvvvv");
		printWriter.println("ClassPath");
		printWriter.print("\t");
		printWriter.println(fileManager
				.getLocation(StandardLocation.CLASS_PATH));
		printWriter.println("SourcePath");
		printWriter.print("\t");
		printWriter.println(fileManager
				.getLocation(StandardLocation.SOURCE_PATH));
		printWriter.println("Options");
		printWriter.print("\t");
		printWriter.println(options);
		printWriter.println("DistDir");
		printWriter.print("\t");
		printWriter.println(fileManager
				.getLocation(StandardLocation.CLASS_OUTPUT));
		printWriter.println("Target");
		printWriter.print("\t");
		printWriter.println(sources);
		printWriter.println("Result");
		printWriter.print("\t");
		printWriter.println(result);
		for (Diagnostic<? extends JavaFileObject> diagnostic : diDiagnostics) {
			printWriter.println("-- ERROR --");
			List<String> code = getSourceCode(diagnostic.getSource());
			printWriter.println(diagnostic.getCode());
			printWriter.println(diagnostic.getMessage(null));
			if (code != null) {
				printWriter
						.println(code.get((int) diagnostic.getLineNumber() - 1));
				printWriter.println(String.format(
						"%" + diagnostic.getColumnNumber() + "s", "^"));
			}
		}
		printWriter.println("^^^^^^^^^^^^^^^^^^^^\n");
		printWriter.close();
		try {
			out.write(stringWriter.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<String> getSourceCode(JavaFileObject javaFile) {
		if (javaFile == null) {
			return null;
		}
		List<String> code = codes.get(javaFile);
		if (code != null) {
			return code;
		}
		code = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					javaFile.openInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				code.add(line);
			}
			codes.put(javaFile, code);
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return code;
	}
}
