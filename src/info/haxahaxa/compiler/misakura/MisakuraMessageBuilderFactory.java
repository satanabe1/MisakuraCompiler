package info.haxahaxa.compiler.misakura;

import info.haxahaxa.compiler.IMessageBuilder;

public abstract class MisakuraMessageBuilderFactory {

	public static IMessageBuilder getMessageBuilder() {
		return getMessageBuilder(BuilderVersion.V2);
	}

	@SuppressWarnings("deprecation")
	public static IMessageBuilder getMessageBuilder(BuilderVersion version) {
		if (version == null) {

		}
		if (version.equals(BuilderVersion.V1)) {
			return MisakuraMessageBuilderV1.getInstance();
		} else if (version.equals(BuilderVersion.V2)) {
			return new MisakuraMessageBuilderV2(new MisakuraConverterImpl());
		}

		return null;
	}

	public enum BuilderVersion {
		/**
		 * @deprecated
		 */
		V1,
		/**
		 * 
		 */
		V2,
	}
}
