package info.haxahaxa.compiler;

import java.util.List;
/**
 * コンパイル引数を表す
 * @author satanabe1
 *
 */
public interface IArgument {

	List<String> getANNOTATION_PROCESSOR_PATH();

	List<String> getCLASS_OUTPUT();

	List<String> getCLASS_PATH();

	List<String> getPLATFORM_CLASS_PATH();

	List<String> getSOURCE_OUTPUT();

	List<String> getSOURCE_PATH();

	List<String> getOptions();

	List<String> getSources();

}
