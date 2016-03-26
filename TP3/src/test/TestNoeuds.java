package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import arbres.ConstructMessage;
import arbres.Message;
import arbres.Noeud;

public class TestNoeuds {
	ActorRef ref1, ref2;
	ActorSystem system;
	@Before
	public void init() {
		system = ActorSystem.create("MySystem");
		ref1 = system.actorOf(Props.create(Noeud.class), "A");
		ref2 = system.actorOf(Props.create(Noeud.class), "B");
	}
	
	@Test
	public void testSendSimpleMessage() {
		List<ActorRef> fils = new ArrayList<ActorRef>();
		/* A --> B */
		fils.add(ref2);
		assertTrue(Noeud.messagerecu == 0);
		ref1.tell(new ConstructMessage(fils), ActorRef.noSender());
		ref1.tell(new Message("Coucou"), ActorRef.noSender());
		system.stop(ref1);
		system.stop(ref2);
		system.shutdown();
		System.out.println(Noeud.messagerecu);
		assertTrue(Noeud.messagerecu == 2);
	}

}
