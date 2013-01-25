package info.haxahaxa.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

/**
 * 何回も500行近いプロパティファイルを読みたくないので，Singletonを採用
 * 
 * @author satanabe1
 * 
 */
public class MisakuraMessageBuilder implements IMessageBuilder {

	private static final MisakuraMessageBuilder misakura = new MisakuraMessageBuilder();
	private static IOException instanceCreationError = null;

	private Properties properties;
	private Map<JavaFileObject, List<String>> codes = new HashMap<JavaFileObject, List<String>>();

	private MisakuraMessageBuilder() {
		try {
			properties = new Properties();
			String propFileName = "props" + File.separator
					+ MisakuraMessageBuilder.class.getSimpleName()
					+ ".properties";
			InputStream propStream = null;
			propStream = MisakuraMessageBuilder.class.getClassLoader()
					.getResourceAsStream(propFileName);
			if (propStream == null) {
				propStream = new FileInputStream(propFileName);
			}
			properties.load(propStream);

		} catch (IOException ex) {
			instanceCreationError = ex;
		}
	}

	public static IMessageBuilder getInstance() {
		if (instanceCreationError != null) {
			throw new RuntimeException(instanceCreationError);
		}
		return misakura;
	}

	public String getCompleteMessage(ICompileResult result) {
		StringBuilder sb = new StringBuilder();
		String ret = System.getProperty("line.separator", "\n");
		StringBuilder distStr = new StringBuilder();
		Iterator<? extends File> distIter = result.getFileManager()
				.getLocation(StandardLocation.CLASS_OUTPUT).iterator();
		while (distIter.hasNext()) {
			File file = distIter.next();
			try {
				distStr.append("[" + file.getCanonicalPath() + "]");
			} catch (Exception ex) {
				distStr.append("[" + file.getAbsolutePath() + "]");
			}
			if (distIter.hasNext()) {
				distStr.append(" , ");
			}
		}

		sb.append("こんぱいぃるはせいぃこうしましたのぉおお" + ret);
		sb.append("しゅちゅりょくしゃきは " + distStr.toString() + " れしゅぅぅぅ" + ret);
		sb.append("またのぉおおごりようをおﾞぉおォおんまちしてぇぇぇぇ゛おﾞぉおォおんりましゅぅぅぅ");
		return sb.toString();
	}

	@Override
	public String getErrorMessage(
			Diagnostic<? extends JavaFileObject> diagnostic) throws Exception {
		// TODO Auto-generated method stub
		String message = properties.getProperty(diagnostic.getCode());
		String ret = System.getProperty("line.separator", "\n");
		if (message == null) {
			message = diagnostic.getMessage(null);
		}

		message = message.replaceAll("<br>", ret);
		List<String> rep = new ArrayList<String>();
		// reflection・・・妥協してしまった．．．
		// Method getArgsMethod = diagnostic.getClass().getMethod("getArgs");
		// Object[] darg = ((Object[]) getArgsMethod.invoke(diagnostic));
		Object[] darg = getArgs(diagnostic);
		for (int i = 0; i < darg.length; i++) {
			rep.add(darg[i] + "");
		}
		for (int i = 0; i < rep.size(); i++) {
			message = message.replaceAll("\\{" + i + "\\}", rep.get(i));
		}
		message = diagnostic.getSource() + ":" + diagnostic.getLineNumber()
				+ ": " + message;
		message = message + getLine(diagnostic);
		return message;
	}

	@SuppressWarnings("unchecked")
	private Object[] getArgs(Diagnostic<? extends JavaFileObject> diagnostic) {
		Logger logger = Logger.getAnonymousLogger();
		Method getArgsMethod = null;
		try {
			getArgsMethod = diagnostic.getClass().getDeclaredMethod("getArgs");
		} catch (Exception ex) {
			logger.log(Level.FINE, "getArgs", ex);
		}
		if (getArgsMethod == null) {
			for (Field field : diagnostic.getClass().getFields()) {
				field.setAccessible(true);
				Object[] messageArgs = null;
				try {
					Object fobj = field.get(diagnostic);
					if (fobj instanceof Diagnostic) {
						messageArgs = getArgs((Diagnostic<? extends JavaFileObject>) fobj);
					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, "getArgs", ex);
				}
				if (messageArgs != null) {
					return messageArgs;
				}
			}
		} else {
			getArgsMethod.setAccessible(true);
			try {
				return (Object[]) getArgsMethod.invoke(diagnostic);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private String getLine(Diagnostic<? extends JavaFileObject> diagnostic) {
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
