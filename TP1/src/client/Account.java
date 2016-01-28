package client;

import java.util.HashMap;

public class Account{
	private HashMap<String,String> account;
	
	public Account(String user , String pwd) {
		account.put(user, pwd);
	}
	
	public Account getAccount() {
		return this;
	}
	
	public boolean getUser(String user) {
		return account.containsKey(user);
	}
}
