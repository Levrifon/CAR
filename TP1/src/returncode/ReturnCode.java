package returncode;

public class ReturnCode {
	private static final int START_TRANS = 125;
	private static final int FILE_STATUS_OK = 150;
	private static final int DATA_OPEN = 200;
	private static final int SYST_TYPE = 215;
	private static final int SERVICE_OK = 220;
	private static final int DECONNECT = 221;
	private static final int FINISH_TRANS = 226;
	private static final int AUTH_SUCC = 230;
	private static final int FILE_ACTION_OK = 250;
	private static final int FILE_PATH = 257;
	private static final int WAIT_MDP = 331;
	private static final int AUTH_FAIL = 332;
	private static final int FILE_ACTION_NOT_TAKEN = 450;
	private static final int ERR_CMD = 503;
	private static final int NON_AUTH = 530;
	private static final int FILE_NOTFOUND = 550;
	private static final int FILE_NOT_ALLOWED = 553;
	
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
	public static String dataConnectionOpen() {
		return DATA_OPEN + " PORT command Successful";
	}
	public static String startTransfert() {
		return START_TRANS + " Transfer starting for file list";
	}
	public static String finishTransfert() {
		return FINISH_TRANS + " Transfer finish for file list";
	}
	public static String systType() {
		return SYST_TYPE + " UNIX Type: L8";
	}
	public static String fileStatusOk() {
		return  FILE_STATUS_OK + " About to open data connection";
	}
	public static String fileActionOkay() {
		return FILE_ACTION_OK + " Requested file action okay, completed";
	}
	public static String actionNotTaken() {
		return FILE_ACTION_NOT_TAKEN + " Requested file action not taken";
	}
	public static String quit() {
		return DECONNECT + " leaving FTP server";
	}
	public static String notAllowed() {
		return FILE_NOT_ALLOWED + " Requested action not taken. File name not allowed";
	}
	public static String pathCreated() {
		return FILE_PATH + " Filepath created";
	}
	
}
