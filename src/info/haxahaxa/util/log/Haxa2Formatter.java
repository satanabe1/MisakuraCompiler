package info.haxahaxa.util.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Haxa2Formatter extends Formatter {

	public static final int TERSE = 2;
	public static final int NORMAL = 4;
	public static final int VERBOSE = 8;

	private int level = NORMAL;

	@Override
	public synchronized String format(LogRecord record) {
		switch (level) {
		case TERSE:
			return terseFormat(record);
		case NORMAL:
			return normalFormat(record);
		case VERBOSE:
			return verboseFormat(record);
		default:
			return normalFormat(record);
		}
	}

	private String terseFormat(LogRecord record) {
		return normalFormat(record);
	}

	private String verboseFormat(LogRecord record) {
		return normalFormat(record);
	}

	/**
	 * Logの出力文字列を生成する。 出力書式：<br>
	 * 年/月/日/ 時:分:秒.ミリ秒 スレッドID ログレベル クラス名 [メソッド名] メッセージ
	 */
	private String normalFormat(LogRecord record) {
		final StringBuilder message = new StringBuilder();
		long millis = record.getMillis();
		String time = String.format("%tD %<tT.%<tL", millis);
		message.append(time);
		message.append(' ');
		message.append(record.getThreadID());
		message.append(' ');
		message.append(record.getLevel().toString());
		message.append(' ');
		String className = record.getSourceClassName();
		message.append(className != null ? className : record.getLoggerName());
		message.append(" [");

		String methodName = record.getSourceMethodName();
		message.append(methodName != null ? methodName : "N/A");
		message.append("] ");

		message.append(formatMessage(record));
		message.append('\n');
		Throwable throwable = record.getThrown();
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
