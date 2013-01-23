package info.haxahaxa.compiler;

import java.util.List;

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
