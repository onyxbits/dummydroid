package de.onyxbits.dummydroid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LoadForm extends AbstractForm implements ActionListener, DummyDroidProperties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFileChooser fileChooser;
	private Properties properties;

	public LoadForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
		fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
		fileChooser.setFileFilter(new BuildPropFilter());
		properties = new Properties();
		add(fileChooser);
	}

	@Override
	public void edit(FormData formData) {
		super.edit(formData);
		backwardAction.setEnabled(false);
		forwardAction.setEnabled(true);
		fileChooser.removeActionListener(this);
		fileChooser.addActionListener(this);
	}

	@Override
	public void commitForm() {

		String defWidth = "" + formData.getDeviceConfigurationProtoBuilder().getScreenWidth();
		String defHeight = "" + formData.getDeviceConfigurationProtoBuilder().getScreenHeight();
		String defDensity = "" + formData.getDeviceConfigurationProtoBuilder().getScreenDensity();

		int sdkversion = Integer.parseInt(properties.getProperty("ro.build.version.sdk", "1"));
		int glesversion = Integer.parseInt(properties.getProperty("ro.opengles.version", "1"));
		int screendensity = Integer.parseInt(properties.getProperty("ro.sf.lcd_density", defDensity));
		int width = Integer.parseInt(properties.getProperty(SCREENHEIGHT, defWidth));
		int height = Integer.parseInt(properties.getProperty(SCREENWIDTH, defHeight));

		formData.getAndroidBuildProtoBuilder()
				.setId(properties.getProperty("ro.build.fingerprint", ""))
				.setProduct(properties.getProperty("ro.product.board", ""))
				.setCarrier(properties.getProperty("ro.carrier", ""))
				.setBootloader(properties.getProperty("ro.bootloader", ""))
				.setClient(properties.getProperty("ro.com.google.clientidbase", ""))
				.setGoogleServices(sdkversion).setDevice(properties.getProperty("ro.product.device", ""))
				.setSdkVersion(sdkversion).setModel(properties.getProperty("ro.product.model", ""))
				.setManufacturer(properties.getProperty("ro.product.manufacturer", ""))
				.setBuildProduct(properties.getProperty("ro.product.name", ""))
				.setRadio(properties.getProperty("gsm.version.baseband", ""));

		formData.getDeviceConfigurationProtoBuilder().setGlEsVersion(glesversion)
				.setScreenDensity(screendensity).setScreenHeight(height).setScreenWidth(width);

		formData.getAndroidCheckinRequestBuilder().setLocale(
				properties.getProperty("ro.product.locale.language", "") + "_"
						+ properties.getProperty("ro.product.locale.region", ""));

		if (properties.get(FEATURES) != null) {
			formData
					.getDeviceConfigurationProtoBuilder()
					.clearSystemAvailableFeature()
					.addAllSystemAvailableFeature(
							Arrays.asList(properties.getProperty(FEATURES, "").trim().split(", *")));
		}

		if (properties.get(LIBRARIES) != null) {
			formData
					.getDeviceConfigurationProtoBuilder()
					.clearSystemSharedLibrary()
					.addAllSystemSharedLibrary(
							Arrays.asList(properties.getProperty(LIBRARIES, "").trim().split(", *")));
		}
		if (properties.get(PLATFORMS) != null) {
			formData
					.getDeviceConfigurationProtoBuilder()
					.clearNativePlatform()
					.addAllNativePlatform(
							Arrays.asList(properties.getProperty(PLATFORMS, "").trim().split(", *")));
		}

	}

	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
			try {
				properties.load(new FileInputStream(fileChooser.getSelectedFile()));
				forwardAction.actionPerformed(null);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error parsing file");
			}
		}
		if (action.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
			properties = new Properties();
			fileChooser.setSelectedFile(new File(System.getProperty("user.dir")));
		}
	}
}
