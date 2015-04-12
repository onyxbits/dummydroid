package de.onyxbits.dummydroid;

public class SoftwareForm extends PropertyForm implements DummyDroidProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SoftwareForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
	}

	@Override
	protected String[] listProperties() {
		return new String[] {
				"ro.build.version.sdk",
				"ro.bootloader",
				"ro.opengles.version",
				"gsm.version.baseband" };
	}

	@Override
	protected String getInitialValue(String pn) {

		if (pn.equals("ro.build.version.sdk")) {
			return "" + formData.getAndroidBuildProtoBuilder().getSdkVersion();
		}
		if (pn.equals("ro.bootloader")) {
			return formData.getAndroidBuildProtoBuilder().getBootloader();
		}
		if (pn.equals("ro.opengles.version")) {
			return "" + formData.getDeviceConfigurationProtoBuilder().getGlEsVersion();
		}
		if (pn.equals("gsm.version.baseband")) {
			return formData.getAndroidBuildProtoBuilder().getRadio();
		}
		return null;
	}

	@Override
	public void commitForm() {
		validateIntOrEmpty("ro.build.version.sdk", "ro.opengles.version");

		if (getProperty("gsm.version.baseband").length() == 0) {
			formData.getAndroidBuildProtoBuilder().clearRadio();
		}
		if (getProperty("ro.bootloader").length() == 0) {
			formData.getAndroidBuildProtoBuilder().clearBootloader();
		}

		if (isEmpty("gsm.version.baseband")) {
			formData.getAndroidBuildProtoBuilder().clearRadio();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setRadio(getProperty("gsm.version.baseband"));
		}

		if (isEmpty("ro.bootloader")) {
			formData.getAndroidBuildProtoBuilder().clearBootloader();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setBootloader(getProperty("ro.bootloader"));
		}

		if (isEmpty("ro.build.version.sdk")) {
			formData.getAndroidBuildProtoBuilder().clearSdkVersion();
			formData.getAndroidBuildProtoBuilder().clearGoogleServices();
		}
		else {
			int sdk = Integer.parseInt(getProperty("ro.build.version.sdk"));
			formData.getAndroidBuildProtoBuilder().setSdkVersion(sdk);
			formData.getAndroidBuildProtoBuilder().setGoogleServices(sdk);
		}

		if (isEmpty("ro.opengles.version")) {
			formData.getDeviceConfigurationProtoBuilder().clearGlEsVersion();
		}
		else {
			int gl = Integer.parseInt(getProperty("ro.opengles.version"));
			formData.getDeviceConfigurationProtoBuilder().setGlEsVersion(gl);
		}
	}
}
