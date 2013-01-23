package info.haxahaxa.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class MisakuraMessage implements IErrorMessage {

	private static final MisakuraMessage misakura = new MisakuraMessage();
	private static IOException instanceCreationError = null;

	private Properties properties;

	private MisakuraMessage() {
		try {
			properties = new Properties();
			String propFileName = "props" + File.separator
					+ MisakuraMessage.class.getSimpleName() + ".properties";
			InputStream propStream = null;
			propStream = MisakuraMessage.class.getClassLoader()
					.getResourceAsStream(propFileName);
			if (propStream == null) {
				propStream = new FileInputStream(propFileName);
			}
			properties.load(propStream);

		} catch (IOException ex) {
			instanceCreationError = ex;
		}
	}

	public static IErrorMessage getInstance() {
		if (instanceCreationError != null) {
			throw new RuntimeException(instanceCreationError);
		}
		return misakura;
	}

	@Override
	public String getMessage(Diagnostic<? extends JavaFileObject> diagnostic)
			throws Exception {
		// TODO Auto-generated method stub
		String message = properties.getProperty(diagnostic.getCode());
		Object[] darg;
		List<String> rep = new ArrayList<String>();

		// reflection・・・妥協してしまった．．．
		Method getArgsMethod = diagnostic.getClass().getMethod("getArgs");

		darg = ((Object[]) getArgsMethod.invoke(diagnostic));
		rep.add(diagnostic.getSource() + ":" + diagnostic.getLineNumber()
				+ ": " + darg[0]);
		for (int i = 1; i < darg.length; i++) {
			rep.add(darg[i] + "");
		}

		for (int i = 0; i < rep.size(); i++) {
			message = message.replaceAll("\\{" + i + "\\}", rep.get(i));
		}

		return message;
	}
}
