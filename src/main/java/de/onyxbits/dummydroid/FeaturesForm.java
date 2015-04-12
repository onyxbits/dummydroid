package de.onyxbits.dummydroid;

import java.util.List;

public class FeaturesForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FeaturesForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
	}

	@Override
	protected List<String> getItems() {
		return formData.getDeviceConfigurationProtoBuilder().getSystemAvailableFeatureList();
	}

	@Override
	public void commitForm() throws RuntimeException {
		formData.getDeviceConfigurationProtoBuilder().clearSystemAvailableFeature();
		if (getContent().size() > 0) {
			formData.getDeviceConfigurationProtoBuilder().addAllSystemAvailableFeature(getContent());
		}
	}

}
