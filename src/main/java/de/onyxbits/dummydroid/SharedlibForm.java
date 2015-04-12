package de.onyxbits.dummydroid;

import java.util.List;

public class SharedlibForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SharedlibForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
	}

	@Override
	protected List<String> getItems() {
		return formData.getDeviceConfigurationProtoBuilder().getSystemSharedLibraryList();
	}

	@Override
	public void commitForm() throws RuntimeException {
		formData.getDeviceConfigurationProtoBuilder().clearSystemSharedLibrary();
		if (getContent().size() > 0) {
			formData.getDeviceConfigurationProtoBuilder().addAllSystemSharedLibrary(getContent());
		}
	}

}
