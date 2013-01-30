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

package info.haxahaxa.compiler.misakura;

import info.haxahaxa.compiler.ArgumentImpl;
import info.haxahaxa.compiler.CompilerImpl;
import info.haxahaxa.compiler.ICompileResult;
import info.haxahaxa.compiler.ICompiler;
import info.haxahaxa.compiler.IMessageBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * EntoryPoint<br>
 * 内部でjavacを呼び，結果をみさくら語で表示する<br>
 * 
 * @author satanabe1
 * 
 */

public class Misakura {

	/**
	 * EntryPoint<br>
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		ArgumentImpl argument = new ArgumentImpl(args);
		if (argument.getSources().size() == 0) {
			help();
		} else {
			compile(argument);
		}
	}

	/**
	 * 与えられた引数に基づいてコンパイルを行う
	 * 
	 * @param argument
	 *            コンパイル引数
	 */
	private static void compile(ArgumentImpl argument) {
		try {
			ICompiler compiler = new CompilerImpl(argument);
			for (String sourceFilePath : argument.getSources()) {
				ICompileResult result = compiler.compile(new File(
						sourceFilePath));
				if (result.isSuccess()) {
					printSuccess(result);
				} else {
					printCompileError(result);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("---------------------------------");
			help();
		}
	}

	/**
	 * 成功メッセージを表示する
	 * 
	 * @param result
	 *            コンパイル結果<br>
	 *            {@link ICompiler#compile(File)}から取得する
	 */
	private static void printSuccess(ICompileResult result) {
		IMessageBuilder misakura = MisakuraMessageBuilderFactory
				.getMessageBuilder();
		System.out.println(misakura.getCompleteMessage(result));
	}

	/**
	 * コンパイルエラーを表示する
	 * 
	 * @param result
	 *            コンパイル結果<br>
	 *            {@link ICompiler#compile(File)}から取得する
	 */
	private static void printCompileError(ICompileResult result) {
		IMessageBuilder misakura = MisakuraMessageBuilderFactory
				.getMessageBuilder();
		int errorcount = 0;
		for (Diagnostic<? extends JavaFileObject> diagnostic : result
				.getDiagnostics()) {
			errorcount++;
			try {
				System.err.println(misakura.getErrorMessage(diagnostic));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.err.println("えらぁあああ あぉは " + errorcount + " これしゅぅぅぅ");
	}

	/**
	 * ヘルプを表示する
	 */
	private static void help() {
		MisakuraConverter misakuraConverter = new MisakuraConverterImpl();
		try {
			Process javacProc = Runtime.getRuntime().exec(
					new String[] { "javac", "-J-Duser.language=ja", "-help" });
			BufferedReader br = new BufferedReader(new InputStreamReader(
					javacProc.getErrorStream()));
			String str = null;
			while ((str = br.readLine()) != null) {
				String message = str.replaceAll("javac", "misakura");
				System.err.println(misakuraConverter
						.jap2misakura(message, true));
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
