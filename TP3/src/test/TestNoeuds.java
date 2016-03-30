package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import message.ConstructMessage;
import message.CounterMessage;

import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import arbres.Noeud;

public class TestNoeuds {
	ActorRef ref1, ref2,ref3;
	ActorSystem system;
	@Before
	public void init() {
		system = ActorSystem.create("MySystem");
		ref1 = system.actorOf(Props.create(Noeud.class), "A");
		ref2 = system.actorOf(Props.create(Noeud.class), "B");
		ref3 = system.actorOf(Props.create(Noeud.class), "C");
	}
	
	@Test
	public void testSendSimpleMessage() {
		List<ActorRef> fils = new ArrayList<ActorRef>();
		CounterMessage counter = new CounterMessage("Coucou");
		/* A --> B et C*/
		fils.add(ref2);
		fils.add(ref3);
		assertTrue(counter.getCounter() == 0);
		ref1.tell(new ConstructMessage(fils), ActorRef.noSender());
		ref1.tell(counter, ActorRef.noSender());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		system.shutdown();
		assertTrue(counter.getCounter() == 2);
	}
	
	/* les autres tests seraient dans le même esprit avec :
	 * 	tester si tout les Noeuds "hasReceivedMessage"
	 *  tester si ils ont bien reçus le message "Coucou"
	 *  tester si la racine correspond bien au même noeud à chaque fois
	 *  etc..
	 */
}
