package info.haxahaxa.compiler.misakura;

import info.haxahaxa.compiler.IMessageBuilder;

public abstract class MisakuraMessageBuilderFactory {

	public static IMessageBuilder getMessageBuilder() {
		return getMessageBuilder(BuilderVersion.V2);
	}

	public static IMessageBuilder getMessageBuilder(BuilderVersion version) {
		if (version.equals(BuilderVersion.V1)) {
			return MisakuraMessageBuilderV1.getInstance();
		} else if (version.equals(BuilderVersion.V2)) {
			return new MisakuraMessageBuilderV2();
		} else {
			return new MisakuraMessageBuilderV3();
		}
	}

	public enum BuilderVersion {
		V1, V2, V3,
	}
}
