package returncode;

public class ReturnCode {
	private static final int SERVICE_OK = 220;
	private static final int WAIT_MDP = 331;
	private static final int AUTH_FAIL = 332;
	private static final int AUTH_SUCC = 230;
	private static final int START_TRANS = 125;
	private static final int FINISH_TRANS = 226;
	private static final int DECONNECT = 221;
	private static final int ERR_CMD = 503;
	private static final int NON_AUTH = 530;
	private static final int FILE_NOTFOUND = 550;
	
	public static String serviceOK() {
		return SERVICE_OK + " Service OK";
	}
	public static String wrongSequence() {
		return ERR_CMD + " Wrong command sequence";
	}
	
	public static String authFail() {
		return AUTH_FAIL + " Wrong user name";
	}
	public static String authSucc() {
		return AUTH_SUCC + " Authentification succeed";
	}
	public static String waitPwd() {
		return WAIT_MDP + " Waiting password";
	}
	public static String nonAuth() {
		return NON_AUTH + " Not logged in";
	}
	public static String fileNotFound() {
		return FILE_NOTFOUND + " File not found";
	}
}
