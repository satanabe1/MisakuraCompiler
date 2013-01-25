package info.haxahaxa.util.log;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Haxa2Logger {

	public static void main(String[] args) {
		LogManager manager = LogManager.getLogManager();
		Enumeration<String> names = manager.getLoggerNames();
		while (names.hasMoreElements()) {
			System.out.println(names.nextElement());
		}
		
	}
}
