package test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import server.Serveur;

public class TestFtpRequest {
	private Serveur s;
	private PrintWriter testOutput;
	private BufferedReader testInput;

	@Before
	public void init() {
		s = new Serveur(new File("toto"),8080);
	}
	
	@Test
	public void testServerisOnline() {
		assertTrue(s.isOnline());
	}
	
	@Test
	public void testServiceOK() {
		try {
			Socket sc = new Socket("127.0.0.1", 8080);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
