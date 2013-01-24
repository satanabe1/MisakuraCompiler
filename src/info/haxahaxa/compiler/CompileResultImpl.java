package info.haxahaxa.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
		StringBuilder sb = new StringBuilder();
		sb.append("vvvvvvvvvvvvvvvvvvvv");
		sb.append("\nClassPath\n\t");
		sb.append(fileManager.getLocation(StandardLocation.CLASS_PATH));
		sb.append("\nSourcePath\n\t");
		sb.append(fileManager.getLocation(StandardLocation.SOURCE_PATH));
		sb.append("\nOptions\n\t");
		sb.append(options);
		sb.append("\nDistDir\n\t");
		sb.append(fileManager.getLocation(StandardLocation.CLASS_OUTPUT));
		sb.append("\nTarget\n\t");
		sb.append(sources);
		sb.append("\nResult\n\t");
		sb.append(result);
		sb.append("\n");
		for (Diagnostic<? extends JavaFileObject> diagnostic : diDiagnostics) {
			sb.append("-- ERROR --\n");
			List<String> code = getSourceCode(diagnostic.getSource());
			sb.append(diagnostic.getCode());
			sb.append("\n");
			sb.append(diagnostic.getMessage(null));
			sb.append("\n");
			if (code != null) {
				sb.append(code.get((int) diagnostic.getLineNumber() - 1));
				sb.append("\n");
				sb.append(String.format("%" + diagnostic.getColumnNumber()
						+ "s", "^"));
				sb.append("\n");
			}
		}
		sb.append("^^^^^^^^^^^^^^^^^^^^\n");
		try {
			out.write(sb.toString().getBytes());
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
