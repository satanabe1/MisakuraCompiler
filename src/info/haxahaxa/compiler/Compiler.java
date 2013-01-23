package info.haxahaxa.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class Compiler implements ICompiler {

	private DiagnosticCollector<JavaFileObject> diagnostics;

	private IArgument argument;

	public Compiler(IArgument compileArgument) {
		diagnostics = new DiagnosticCollector<JavaFileObject>();
		argument = compileArgument;
	}

	@Override
	public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
		return diagnostics.getDiagnostics();
	}

	@Override
	public ICompileResult compile(File sourceFile) throws IOException {
		if (ToolProvider.getSystemJavaCompiler() == null) {
			throw new NullPointerException(
					"ToolProvider.getSystemJavaCompiler() : null");
		}
		if (sourceFile == null) {
			throw new NullPointerException("no input file..");
		}
		if (!sourceFile.exists()) {
			throw new FileNotFoundException(sourceFile.getAbsolutePath());
		}
		// ここからコンパイルの準備
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = getStandardJavaFileManager(compiler);
		Iterable<? extends JavaFileObject> sources = getJavaSources(
				fileManager, sourceFile);
		Iterable<String> options = argument.getOptions();
		CompilationTask task = compiler.getTask(null, fileManager, diagnostics,
				options, null, sources);
		// コンパイルの実行は次の行のtask.call()命令
		ICompileResult compileResult = new CompileResultImpl(task.call(),
				fileManager, diagnostics.getDiagnostics(), options, sources);
		return compileResult;
	}

	/**
	 * {@link StandardJavaFileManager}インスタンスの取得<br>
	 * {@link StandardLocation}の6つのプロパティも設定された物がかえる
	 * 
	 * @param javaCompiler
	 * @return
	 * @throws IOException
	 */
	private StandardJavaFileManager getStandardJavaFileManager(
			JavaCompiler javaCompiler) throws IOException {
		StandardJavaFileManager fileManager = javaCompiler
				.getStandardFileManager(diagnostics, null, null);

		// 注釈プロセッサの検索場所
		{
			List<File> directories = strlist2filelist(argument
					.getANNOTATION_PROCESSOR_PATH());
			if (directories.size() != 0) {
				fileManager
						.setLocation(
								StandardLocation.ANNOTATION_PROCESSOR_PATH,
								directories);
			}
		}

		// クラスファイル出力先
		{
			List<File> directories = strlist2filelist(argument
					.getCLASS_OUTPUT());
			if (directories.size() != 0) {
				fileManager.setLocation(StandardLocation.CLASS_OUTPUT,
						directories);
			}
		}

		// クラスパスの追加
		{
			List<File> directories = strlist2filelist(argument.getCLASS_PATH());
			if (directories.size() != 0) {
				fileManager.setLocation(StandardLocation.CLASS_PATH,
						directories);
			}
		}

		// プラットフォームクラスの検索場所
		{
			List<File> directories = strlist2filelist(argument
					.getPLATFORM_CLASS_PATH());
			if (directories.size() != 0) {
				fileManager.setLocation(StandardLocation.PLATFORM_CLASS_PATH,
						directories);
			}
		}

		// ソースファイル出力先
		{
			List<File> directories = strlist2filelist(argument
					.getSOURCE_OUTPUT());
			if (directories.size() != 0) {
				fileManager.setLocation(StandardLocation.SOURCE_OUTPUT,
						directories);
			}
		}

		// ソースパスの追加
		{
			List<File> directories = strlist2filelist(argument.getSOURCE_PATH());
			if (directories.size() != 0) {
				fileManager.setLocation(StandardLocation.SOURCE_PATH,
						directories);
			}
		}

		return fileManager;
	}

	private List<File> strlist2filelist(List<String> strlist) {
		List<File> filelist = new ArrayList<File>();
		for (String str : strlist) {
			filelist.add(new File(str));
		}
		return filelist;
	}

	private Iterable<? extends JavaFileObject> getJavaSources(
			StandardJavaFileManager fileManager, File sourceFile) {
		Iterable<? extends JavaFileObject> javaFileObjects = fileManager
				.getJavaFileObjects(sourceFile);
		return javaFileObjects;
	}
}
