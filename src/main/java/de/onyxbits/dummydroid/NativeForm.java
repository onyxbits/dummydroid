package de.onyxbits.dummydroid;

import java.util.List;

public class NativeForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NativeForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
	}

	@Override
	protected List<String> getItems() {
		return formData.getDeviceConfigurationProtoBuilder().getNativePlatformList();
	}

	@Override
	public void commitForm() throws RuntimeException {
		formData.getDeviceConfigurationProtoBuilder().clearNativePlatform();
		if (getContent().size() > 0) {
			formData.getDeviceConfigurationProtoBuilder().addAllNativePlatform(getContent());
		}
	}

}
