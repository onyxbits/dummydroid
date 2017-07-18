package de.onyxbits.dummydroid;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import javax.swing.SwingWorker;

import com.akdeniz.googleplaycrawler.GooglePlay.AndroidCheckinRequest;
import com.akdeniz.googleplaycrawler.GooglePlay.AndroidCheckinResponse;
import com.akdeniz.googleplaycrawler.GooglePlay.ResponseWrapper;
import com.akdeniz.googleplaycrawler.GooglePlay.UploadDeviceConfigRequest;
import com.akdeniz.googleplaycrawler.GooglePlayAPI;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * A swingworker for uploading the checkinrequest to Play and getting a GSF ID.
 * 
 * @author patrick
 * 
 */
class CheckinWorker extends SwingWorker<String, Object> {

	private FormData formData;
	private CheckinForm callback;

	/**
	 * Name of the file containing the network config
	 */
	public static final String NETCFG = "network.cfg";
	public static final String PROXYHOST = "proxyhost";
	public static final String PROXYPORT = "proxyport";
	public static final String PROXYUSER = "proxyuser";
	public static final String PROXYPASS = "proxypass";

	public CheckinWorker(CheckinForm callback, FormData formData) {
		this.formData = formData;
		this.callback = callback;
	}

	protected void setProxy(HttpClient client) throws IOException {
		File cfgfile = new File(NETCFG);
		if (cfgfile.exists()) {
			Properties cfg = new Properties();
			cfg.load(new FileInputStream(cfgfile));
			String ph = cfg.getProperty(PROXYHOST, null);
			String pp = cfg.getProperty(PROXYPORT, null);
			String pu = cfg.getProperty(PROXYUSER, null);
			String pw = cfg.getProperty(PROXYPASS, null);
			if (ph == null || pp == null) {
				return;
			}
			final HttpHost proxy = new HttpHost(ph, Integer.parseInt(pp));
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			if (pu != null && pw != null) {
				((DefaultHttpClient) client).getCredentialsProvider().setCredentials(
						new AuthScope(proxy), new UsernamePasswordCredentials(pu, pw));
			}
		}
	}

	@Override
	protected String doInBackground() throws Exception {
		GooglePlayAPI api = new GooglePlayAPI(formData.getUsername(), formData.getPassword());
		setProxy(api.getClient());
		// this first checkin is for generating android-id
		byte[] buf = AndroidCheckinRequest.newBuilder(formData
				.getAndroidCheckinRequestBuilder().build()).build().toByteArray();
		AndroidCheckinResponse checkinResponse = api.postCheckin(buf);
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
