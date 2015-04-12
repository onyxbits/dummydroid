package de.onyxbits.dummydroid;

public class MiscForm extends PropertyForm implements DummyDroidProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MiscForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
	}

	@Override
	protected String[] listProperties() {
		return new String[] {
				"ro.carrier",
				"ro.com.google.clientidbase",
				CELLOPERATOR,
				SIMOPERATOR,
				LOCALE,
				TIMEZONE };
	}

	@Override
	protected String getInitialValue(String pn) {
		if (pn.equals("ro.carrier")) {
			return formData.getAndroidBuildProtoBuilder().getCarrier();
		}
		if (pn.equals("ro.com.google.clientidbase")) {
			return formData.getAndroidBuildProtoBuilder().getClient();
		}
		if (pn.equals(LOCALE)) {
			return formData.getAndroidCheckinRequestBuilder().getLocale();
		}
		if (pn.equals(TIMEZONE)) {
			return formData.getAndroidCheckinRequestBuilder().getTimeZone();
		}
		if (pn.equals(SIMOPERATOR)) {
			return formData.getAndroidCheckinProtoBuilder().getSimOperator();
		}
		if (pn.equals(CELLOPERATOR)) {
			return formData.getAndroidCheckinProtoBuilder().getCellOperator();
		}
		return null;
	}

	@Override
	public void commitForm() throws RuntimeException {
		/*
		 * formData.getAndroidBuildProtoBuilder().setCarrier(getProperty("ro.carrier"
		 * )) .setClient(getProperty("ro.com.google.clientidbase"));
		 * formData.getAndroidCheckinRequestBuilder
		 * ().setTimeZone(getProperty(TIMEZONE)) .setLocale(getProperty(LOCALE));
		 */
		if (isEmpty(CELLOPERATOR) || isEmpty(SIMOPERATOR)) {
			formData.getAndroidCheckinProtoBuilder().clearCellOperator().clearSimOperator();
		}
		else {
			formData.getAndroidCheckinProtoBuilder().setCellOperator(getProperty(CELLOPERATOR))
					.setSimOperator(getProperty(SIMOPERATOR));
		}

		if (isEmpty("ro.carrier")) {
			formData.getAndroidBuildProtoBuilder().clearCarrier();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setCarrier(getProperty("ro.carrier"));
		}
		if (isEmpty("ro.com.google.clientidbase")) {
			formData.getAndroidBuildProtoBuilder().clearClient();
		}
		else {
			formData.getAndroidBuildProtoBuilder().setClient(getProperty("ro.com.google.clientidbase"));
		}
		if (isEmpty(TIMEZONE)) {
			formData.getAndroidCheckinRequestBuilder().clearTimeZone();
		}
		else {
			formData.getAndroidCheckinRequestBuilder().setTimeZone(getProperty(TIMEZONE));
		}
		if (isEmpty(LOCALE)) {
			formData.getAndroidCheckinRequestBuilder().clearLocale();
		}
		else {
			formData.getAndroidCheckinRequestBuilder().setLocale(getProperty(LOCALE));
		}
	}

}
