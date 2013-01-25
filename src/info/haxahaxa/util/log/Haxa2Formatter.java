package info.haxahaxa.util.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Haxa2Formatter extends Formatter {

	/**
	 * Logの出力文字列を生成する。 出力書式：<br>
	 * 年/月/日/ 時:分:秒.ミリ秒 スレッドID ログレベル クラス名 [メソッド名] メッセージ
	 */
	@Override
	public synchronized String format(final LogRecord aRecord) {
		final StringBuffer message = new StringBuffer(131);

		long millis = aRecord.getMillis();
		String time = String.format("%tD %<tT.%<tL", millis);
		message.append(time);
		message.append(' ');
		message.append(aRecord.getThreadID());
		message.append(' ');
		message.append(aRecord.getLevel().toString());
		message.append(' ');
		String className = aRecord.getSourceClassName();
		message.append(className != null ? className : aRecord.getLoggerName());
		message.append(" [");

		String methodName = aRecord.getSourceMethodName();
		message.append(methodName != null ? methodName : "N/A");
		message.append("] ");

		message.append(formatMessage(aRecord));
		message.append('\n');
		Throwable throwable = aRecord.getThrown();
		if (throwable != null) {
			message.append(throwable.toString());
			message.append('\n');
			for (StackTraceElement trace : throwable.getStackTrace()) {
				message.append('\t');
				message.append(trace.toString());
				message.append('\n');
			}
		}
		return message.toString();
	}
}
