package de.onyxbits.dummydroid;

public class HardwareForm extends PropertyForm implements DummyDroidProperties {

	public HardwareForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String[] listProperties() {
		return new String[] {
				"ro.build.fingerprint",
				"ro.product.board",
				"ro.product.device",
				"ro.product.model",
				"ro.product.manufacturer",
				"ro.product.name",
				"ro.sf.lcd_density",
				SCREENWIDTH,
				SCREENHEIGHT };
	}

	@Override
	protected String getInitialValue(String pn) {
		if (pn.equals("ro.build.fingerprint")) {
			return formData.getAndroidBuildProtoBuilder().getId();
		}
		if (pn.equals("ro.product.board")) {
			return formData.getAndroidBuildProtoBuilder().getProduct();
		}
		if (pn.equals("ro.product.device")) {
			return formData.getAndroidBuildProtoBuilder().getDevice();
		}
		if (pn.equals("ro.product.model")) {
			return formData.getAndroidBuildProtoBuilder().getModel();
		}
		if (pn.equals("ro.product.manufacturer")) {
			return formData.getAndroidBuildProtoBuilder().getManufacturer();
		}
		if (pn.equals("ro.product.name")) {
			return formData.getAndroidBuildProtoBuilder().getBuildProduct();
		}

		if (pn.equals("ro.sf.lcd_density")) {
			return "" + formData.getDeviceConfigurationProtoBuilder().getScreenDensity();
		}
		if (pn.equals(SCREENWIDTH)) {
			return "" + formData.getDeviceConfigurationProtoBuilder().getScreenWidth();
		}
		if (pn.equals(SCREENHEIGHT)) {
			return "" + formData.getDeviceConfigurationProtoBuilder().getScreenHeight();
		}

		return "";
	}

	@Override
	public void commitForm() {
		validateIntOrEmpty(SCREENHEIGHT, SCREENWIDTH, "ro.sf.lcd_density");

		if (isEmpty("ro.sf.lcd_density")) {
			formData.getDeviceConfigurationProtoBuilder().clearScreenDensity();
		}
		else {
			formData.getDeviceConfigurationProtoBuilder().setScreenDensity(
					Integer.parseInt(getProperty("ro.sf.lcd_density")));
		}

		if (isEmpty(SCREENHEIGHT)) {
			formData.getDeviceConfigurationProtoBuilder().clearScreenHeight();
		}
		else {
			formData.getDeviceConfigurationProtoBuilder().setScreenHeight(
					Integer.parseInt(getProperty(SCREENHEIGHT)));
		}

		if (isEmpty(SCREENWIDTH)) {
			formData.getDeviceConfigurationProtoBuilder().clearScreenWidth();
		}
		else {
			formData.getDeviceConfigurationProtoBuilder().setScreenWidth(
					Integer.parseInt(getProperty(SCREENWIDTH)));
		}

		if (isEmpty("ro.build.fingerprint")) {
			formData.getAndroidBuildProtoBuilder().clearId();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setId(getProperty("ro.build.fingerprint"));
		}

		if (isEmpty("ro.product.board")) {
			formData.getAndroidBuildProtoBuilder().clearProduct();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setProduct(getProperty("ro.product.board"));
		}

		if (isEmpty("ro.product.device")) {
			formData.getAndroidBuildProtoBuilder().clearDevice();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setDevice(getProperty("ro.product.device"));
		}

		if (isEmpty("ro.product.model")) {
			formData.getAndroidBuildProtoBuilder().clearModel();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setModel(getProperty("ro.product.model"));
		}

		if (isEmpty("ro.product.manufacturer")) {
			formData.getAndroidBuildProtoBuilder().clearManufacturer();
		}
		else {
			formData.getAndroidBuildProtoBuilder()
					.setManufacturer(getProperty("ro.product.manufacturer"));
		}

		if (isEmpty("ro.product.name")) {
			formData.getAndroidBuildProtoBuilder().clearBuildProduct();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setBuildProduct(getProperty("ro.product.name"));
		}

		/*
		 * formData.getAndroidBuildProtoBuilder().setId(getProperty(
		 * "ro.build.fingerprint"))
		 * .setProduct(getProperty("ro.product.board")).setDevice
		 * (getProperty("ro.product.device"))
		 * .setModel(getProperty("ro.product.model"))
		 * .setManufacturer(getProperty("ro.product.manufacturer"))
		 * .setBuildProduct(getProperty("ro.product.name"));
		 */

	}
}
