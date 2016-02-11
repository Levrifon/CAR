package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import server.Serveur;

public class TestServer {
	private Serveur server;
	String user = "toto";
	String mdp = "pwd";
	
	@Before
	public void init() {
		server = new Serveur(new File("toto"), 8080);
	}
	
	@Test
	public void testGetCurrentDir() {
		File tmp = new File("toto");
		assertEquals(tmp,server.getCurrentDirectory());
		server.closeServer();
	}
	@Test
	public void testAddUser() {
		assertFalse(server.userExists("toto"));
		server.addAccount(user,mdp);
		assertTrue(server.userExists("toto"));
		server.closeServer();
	}
	@Test
	public void testExists() {
		server.addAccount(user, mdp);
		assertTrue(server.userExists("toto"));
		server.closeServer();
	}
	
	@Test
	public void testConnect() {
		String wrongUser = "test";
		String wrongPwd = "password";
		server.addAccount(user,mdp);
		assertFalse(server.connect(wrongUser,wrongPwd));
		assertTrue(server.connect(user, mdp));
		server.closeServer();
	}

}
