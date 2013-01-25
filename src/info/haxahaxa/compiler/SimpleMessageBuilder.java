package info.haxahaxa.compiler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class SimpleMessageBuilder implements IMessageBuilder {
	private Map<JavaFileObject, List<String>> codes = new HashMap<JavaFileObject, List<String>>();

	@Override
	public String getCompleteMessage(ICompileResult result) {
		// TODO Auto-generated method stub
		return "Complete";
	}

	@Override
	public String getErrorMessage(
			Diagnostic<? extends JavaFileObject> diagnostic) throws Exception {
		// TODO Auto-generated method stub
		return diagnostic.getMessage(null) + getLine(diagnostic);
	}

	protected String getLine(Diagnostic<? extends JavaFileObject> diagnostic) {
		String ret = System.getProperty("line.separator", "\n");
		StringBuilder sb = new StringBuilder();
		List<String> code = getSourceCode(diagnostic.getSource());
		if (code == null) {
			return null;
		}
		sb.append(ret);
		sb.append(code.get((int) diagnostic.getLineNumber() - 1));
		sb.append(ret);
		sb.append(String.format("%" + diagnostic.getColumnNumber() + "s", "^"));

		return sb.toString();
	}

	protected List<String> getSourceCode(JavaFileObject javaFile) {
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
