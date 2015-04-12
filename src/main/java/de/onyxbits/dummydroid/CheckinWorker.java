package de.onyxbits.dummydroid;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;

import javax.swing.SwingWorker;

import com.akdeniz.googleplaycrawler.GooglePlay.AndroidCheckinRequest;
import com.akdeniz.googleplaycrawler.GooglePlay.AndroidCheckinResponse;
import com.akdeniz.googleplaycrawler.GooglePlay.ResponseWrapper;
import com.akdeniz.googleplaycrawler.GooglePlay.UploadDeviceConfigRequest;
import com.akdeniz.googleplaycrawler.GooglePlayAPI;
import com.akdeniz.googleplaycrawler.Utils;

/**
 * A swingworker for uploading the checkinrequest to Play and getting a GSF ID.
 * 
 * @author patrick
 * 
 */
class CheckinWorker extends SwingWorker<String, Object> {

	private FormData formData;
	private CheckinForm callback;

	public CheckinWorker(CheckinForm callback, FormData formData) {
		this.formData = formData;
		this.callback = callback;
	}

	@Override
	protected String doInBackground() throws Exception {
		GooglePlayAPI api = new GooglePlayAPI(formData.getUsername(), formData.getPassword());
		// this first checkin is for generating android-id
		AndroidCheckinResponse checkinResponse = api.postCheckin(Utils.generateAndroidCheckinRequest()
				.toByteArray());
		api.setAndroidID(BigInteger.valueOf(checkinResponse.getAndroidId()).toString(16));
		api.setSecurityToken((BigInteger.valueOf(checkinResponse.getSecurityToken()).toString(16)));

		String c2dmAuth = api.loginAC2DM();

		AndroidCheckinRequest.Builder checkInbuilder = AndroidCheckinRequest.newBuilder(formData
				.getAndroidCheckinRequestBuilder().build());

		AndroidCheckinRequest build = checkInbuilder
				.setId(new BigInteger(api.getAndroidID(), 16).longValue())
				.setSecurityToken(new BigInteger(api.getSecurityToken(), 16).longValue())
				.addAccountCookie("[" + formData.getUsername() + "]").addAccountCookie(c2dmAuth).build();
		// this is the second checkin to match credentials with android-id
		api.postCheckin(build.toByteArray());

		api.login();

		UploadDeviceConfigRequest request = UploadDeviceConfigRequest.newBuilder()
				.setDeviceConfiguration(formData.getDeviceConfigurationProtoBuilder()).build();
		ResponseWrapper responseWrapper = api.executePOSTRequest(GooglePlayAPI.UPLOADDEVICECONFIG_URL,
				request.toByteArray(), "application/x-protobuf");
		responseWrapper.getPayload().getUploadDeviceConfigResponse();

		PrintWriter pw = new PrintWriter(new File(api.getAndroidID()+".txt"));
		pw.println(formData.assemble());
		pw.close();
		return api.getAndroidID();
	}

	public void done() {
		try {
			callback.finished(get());
		}
		catch (Exception e) {
			callback.finished(e);
		}
	}
}
