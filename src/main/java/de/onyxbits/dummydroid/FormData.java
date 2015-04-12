package de.onyxbits.dummydroid;

import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import com.akdeniz.googleplaycrawler.GooglePlay.AndroidBuildProto;
import com.akdeniz.googleplaycrawler.GooglePlay.AndroidCheckinProto;
import com.akdeniz.googleplaycrawler.GooglePlay.AndroidCheckinRequest;
import com.akdeniz.googleplaycrawler.GooglePlay.DeviceConfigurationProto;

/**
 * A datacapsule
 * 
 * @author patrick
 * 
 */
public class FormData {

	private AndroidCheckinRequest.Builder androidCheckinRequestBuilder;
	private AndroidCheckinProto.Builder androidCheckinProtoBuilder;
	private DeviceConfigurationProto.Builder deviceConfigurationProtoBuilder;
	private AndroidBuildProto.Builder androidBuildProtoBuilder;

	private String username = "";
	private String password = "";

	public FormData() {
	}

	public AndroidCheckinRequest assemble() {
		getAndroidBuildProtoBuilder();
		getAndroidCheckinProtoBuilder();
		getAndroidCheckinRequestBuilder();
		return androidCheckinRequestBuilder
				.setCheckin(androidCheckinProtoBuilder.setBuild(androidBuildProtoBuilder))
				.setDeviceConfiguration(getDeviceConfigurationProtoBuilder()).setFragment(0).build();
	}

	/**
	 * @return the androidCheckinRequestBuilder
	 */
	public AndroidCheckinRequest.Builder getAndroidCheckinRequestBuilder() {
		if (androidCheckinRequestBuilder == null) {
			String tz = TimeZone.getDefault().getID();
			androidCheckinRequestBuilder = AndroidCheckinRequest.newBuilder().setId(0).setVersion(3)
					.setTimeZone(tz);
		}
		return androidCheckinRequestBuilder;
	}

	/**
	 * @return the androidCheckinProtoBuilder
	 */
	public AndroidCheckinProto.Builder getAndroidCheckinProtoBuilder() {
		if (androidCheckinProtoBuilder == null) {
			androidCheckinProtoBuilder = AndroidCheckinProto.newBuilder();
		}
		return androidCheckinProtoBuilder;
	}

	/**
	 * @param androidCheckinRequestBuilder
	 *          the androidCheckinRequestBuilder to set
	 */
	public void setAndroidCheckinRequestBuilder(
			AndroidCheckinRequest.Builder androidCheckinRequestBuilder) {
		this.androidCheckinRequestBuilder = androidCheckinRequestBuilder;
	}

	/**
	 * @param androidCheckinProtoBuilder
	 *          the androidCheckinProtoBuilder to set
	 */
	public void setAndroidCheckinProtoBuilder(AndroidCheckinProto.Builder androidCheckinProtoBuilder) {
		this.androidCheckinProtoBuilder = androidCheckinProtoBuilder;
	}

	/**
	 * @return the deviceConfigurationProtoBuilder
	 */
	public DeviceConfigurationProto.Builder getDeviceConfigurationProtoBuilder() {
		if (deviceConfigurationProtoBuilder == null) {
			deviceConfigurationProtoBuilder = DeviceConfigurationProto
					.newBuilder()
					.setTouchScreen(3)
					.setKeyboard(1)
					.setNavigation(1)
					.setScreenLayout(2)
					.setHasHardKeyboard(false)
					.setHasFiveWayNavigation(false)
					.setScreenDensity(320)
					.setScreenWidth(720)
					.setScreenHeight(1184)
					.setGlEsVersion(131072)
					.addAllSystemSharedLibrary(
							Arrays.asList("android.test.runner", "com.android.future.usb.accessory",
									"com.android.location.provider", "com.android.nfc_extras",
									"com.google.android.maps", "com.google.android.media.effects",
									"com.google.widevine.software.drm", "javax.obex"))
					.addAllSystemAvailableFeature(
							Arrays.asList("android.hardware.bluetooth", "android.hardware.camera",
									"android.hardware.camera.autofocus", "android.hardware.camera.flash",
									"android.hardware.camera.front", "android.hardware.faketouch",
									"android.hardware.location", "android.hardware.location.gps",
									"android.hardware.location.network", "android.hardware.microphone",
									"android.hardware.nfc", "android.hardware.screen.landscape",
									"android.hardware.screen.portrait", "android.hardware.sensor.accelerometer",
									"android.hardware.sensor.barometer", "android.hardware.sensor.compass",
									"android.hardware.sensor.gyroscope", "android.hardware.sensor.light",
									"android.hardware.sensor.proximity", "android.hardware.telephony",
									"android.hardware.telephony.gsm", "android.hardware.touchscreen",
									"android.hardware.touchscreen.multitouch",
									"android.hardware.touchscreen.multitouch.distinct",
									"android.hardware.touchscreen.multitouch.jazzhand",
									"android.hardware.usb.accessory", "android.hardware.usb.host",
									"android.hardware.wifi", "android.hardware.wifi.direct",
									"android.software.live_wallpaper", "android.software.sip",
									"android.software.sip.voip", "com.cyanogenmod.android",
									"com.cyanogenmod.nfc.enhanced", "com.google.android.feature.GOOGLE_BUILD",
									"com.nxp.mifare", "com.tmobile.software.themes"))
					.addAllNativePlatform(Arrays.asList("armeabi-v7a", "armeabi"))
					.addAllSystemSupportedLocale(
							Arrays.asList("af", "af_ZA", "am", "am_ET", "ar", "ar_EG", "bg", "bg_BG", "ca",
									"ca_ES", "cs", "cs_CZ", "da", "da_DK", "de", "de_AT", "de_CH", "de_DE", "de_LI",
									"el", "el_GR", "en", "en_AU", "en_CA", "en_GB", "en_NZ", "en_SG", "en_US", "es",
									"es_ES", "es_US", "fa", "fa_IR", "fi", "fi_FI", "fr", "fr_BE", "fr_CA", "fr_CH",
									"fr_FR", "hi", "hi_IN", "hr", "hr_HR", "hu", "hu_HU", "in", "in_ID", "it",
									"it_CH", "it_IT", "iw", "iw_IL", "ja", "ja_JP", "ko", "ko_KR", "lt", "lt_LT",
									"lv", "lv_LV", "ms", "ms_MY", "nb", "nb_NO", "nl", "nl_BE", "nl_NL", "pl",
									"pl_PL", "pt", "pt_BR", "pt_PT", "rm", "rm_CH", "ro", "ro_RO", "ru", "ru_RU",
									"sk", "sk_SK", "sl", "sl_SI", "sr", "sr_RS", "sv", "sv_SE", "sw", "sw_TZ", "th",
									"th_TH", "tl", "tl_PH", "tr", "tr_TR", "ug", "ug_CN", "uk", "uk_UA", "vi",
									"vi_VN", "zh_CN", "zh_TW", "zu", "zu_ZA"))
					.addAllGlExtension(
							Arrays.asList("GL_EXT_debug_marker", "GL_EXT_discard_framebuffer",
									"GL_EXT_multi_draw_arrays", "GL_EXT_shader_texture_lod",
									"GL_EXT_texture_format_BGRA8888", "GL_IMG_multisampled_render_to_texture",
									"GL_IMG_program_binary", "GL_IMG_read_format", "GL_IMG_shader_binary",
									"GL_IMG_texture_compression_pvrtc", "GL_IMG_texture_format_BGRA8888",
									"GL_IMG_texture_npot", "GL_IMG_vertex_array_object", "GL_OES_EGL_image",
									"GL_OES_EGL_image_external", "GL_OES_blend_equation_separate",
									"GL_OES_blend_func_separate", "GL_OES_blend_subtract", "GL_OES_byte_coordinates",
									"GL_OES_compressed_ETC1_RGB8_texture", "GL_OES_compressed_paletted_texture",
									"GL_OES_depth24", "GL_OES_depth_texture", "GL_OES_draw_texture",
									"GL_OES_egl_sync", "GL_OES_element_index_uint", "GL_OES_extended_matrix_palette",
									"GL_OES_fixed_point", "GL_OES_fragment_precision_high",
									"GL_OES_framebuffer_object", "GL_OES_get_program_binary", "GL_OES_mapbuffer",
									"GL_OES_matrix_get", "GL_OES_matrix_palette", "GL_OES_packed_depth_stencil",
									"GL_OES_point_size_array", "GL_OES_point_sprite", "GL_OES_query_matrix",
									"GL_OES_read_format", "GL_OES_required_internalformat", "GL_OES_rgb8_rgba8",
									"GL_OES_single_precision", "GL_OES_standard_derivatives", "GL_OES_stencil8",
									"GL_OES_stencil_wrap", "GL_OES_texture_cube_map", "GL_OES_texture_env_crossbar",
									"GL_OES_texture_float", "GL_OES_texture_half_float",
									"GL_OES_texture_mirrored_repeat", "GL_OES_vertex_array_object",
									"GL_OES_vertex_half_float"));
		}
		return deviceConfigurationProtoBuilder;
	}

	/**
	 * @param deviceConfigurationProtoBuilder
	 *          the deviceConfigurationProtoBuilder to set
	 */
	public void setDeviceConfigurationProtoBuilder(
			DeviceConfigurationProto.Builder deviceConfigurationProtoBuilder) {
		this.deviceConfigurationProtoBuilder = deviceConfigurationProtoBuilder;
	}

	/**
	 * @return the androidBuildProtoBuilder
	 */
	public AndroidBuildProto.Builder getAndroidBuildProtoBuilder() {
		if (androidBuildProtoBuilder == null) {
			androidBuildProtoBuilder = AndroidBuildProto.newBuilder().setTimestamp(
					new Date().getTime() / 1000);
		}
		return androidBuildProtoBuilder;
	}

	/**
	 * @param androidBuildProtoBuilder
	 *          the androidBuildProtoBuilder to set
	 */
	public void setAndroidBuildProtoBuilder(AndroidBuildProto.Builder androidBuildProtoBuilder) {
		this.androidBuildProtoBuilder = androidBuildProtoBuilder;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
