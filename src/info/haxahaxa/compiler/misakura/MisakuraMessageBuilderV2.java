package info.haxahaxa.compiler.misakura;

import info.haxahaxa.compiler.ICompileResult;
import info.haxahaxa.compiler.IMessageBuilder;
import info.haxahaxa.compiler.SimpleMessageBuilder;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

/**
 * たぶんそこそこマトモにみさくら変換できるように改造したクラス<br>
 * {@link MisakuraConverter}インスタンスを使ってみさくら語変換を行う
 * 
 * @author satanabe1
 * 
 */
public class MisakuraMessageBuilderV2 extends SimpleMessageBuilder implements
		IMessageBuilder {

	private MisakuraConverter converter;

	public MisakuraMessageBuilderV2(MisakuraConverter misakuraConverter) {
		converter = misakuraConverter;
	}

	@Override
	public String getCompleteMessage(ICompileResult result) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		StringBuilder distStr = new StringBuilder();
		Iterator<? extends File> distIter = result.getFileManager()
				.getLocation(StandardLocation.CLASS_OUTPUT).iterator();
		while (distIter.hasNext()) {
			File file = distIter.next();
			try {
				distStr.append("[");
				distStr.append(file.getCanonicalPath());
				distStr.append("]");
			} catch (Exception ex) {
				distStr.append("[");
				distStr.append(file.getAbsolutePath());
				distStr.append("]");
			}
			if (distIter.hasNext()) {
				distStr.append(" , ");// いや、普通に考えてこんなトコロに入ってこないけどさ
			}
		}

		printWriter.println("コンパイルは成功しました");
		printWriter.print("出力先は ");
		printWriter.print(distStr.toString());
		printWriter.println("です");
		printWriter.print("またのごりようをおまちしております");
		printWriter.close();

		return converter.jap2misakura(stringWriter.toString(), true);
	}

	@Override
	public String getErrorMessage(
			Diagnostic<? extends JavaFileObject> diagnostic) throws Exception {
		// TODO Auto-generated method stub
		String message = diagnostic.getMessage(Locale.JAPANESE);
		message = converter.jap2misakura(message, true);
		return message + getLine(diagnostic);
	}
}
