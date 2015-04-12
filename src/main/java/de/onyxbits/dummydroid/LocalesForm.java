package de.onyxbits.dummydroid;

import java.util.List;

public class LocalesForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocalesForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
	}

	@Override
	protected List<String> getItems() {
		return formData.getDeviceConfigurationProtoBuilder().getSystemSupportedLocaleList();
	}

	@Override
	public void commitForm() throws RuntimeException {
		formData.getDeviceConfigurationProtoBuilder().clearSystemSupportedLocale();
		if (getContent().size() > 0) {
			formData.getDeviceConfigurationProtoBuilder().addAllSystemSupportedLocale(getContent());
		}
	}

}
