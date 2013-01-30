/*
 * The MIT License
 * 
 * Copyright (c) 2013 haxahaxa.info
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction,including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software,and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

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
