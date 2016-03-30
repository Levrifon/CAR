package main;

import java.util.ArrayList;
import java.util.List;

import message.ConstructMessage;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import arbres.Noeud;

public class Main {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("MySystem");
		/*ActorSystem system2 = ActorSystem.create("SecondarySystem"); permet de répondre à la question 4 du TP, en séparant les systèmes en deux parties*/ 
		ActorRef ref1, ref2, ref3, ref4, ref5, ref6;
		ref1 = system.actorOf(Props.create(Noeud.class), "Noeud1");
		ref2 = system.actorOf(Props.create(Noeud.class), "Noeud2");
		ref3 = system.actorOf(Props.create(Noeud.class), "Noeud3");
		ref4 = system.actorOf(Props.create(Noeud.class), "Noeud4");
		ref5 = system.actorOf(Props.create(Noeud.class), "Noeud5");
		ref6 = system.actorOf(Props.create(Noeud.class), "Noeud6");
	
		/*ref1 va recevoir la liste de ses fils {2,5}*/
		List<ActorRef> filsNoeud1 = new ArrayList<ActorRef>();
		filsNoeud1.add(ref2);
		filsNoeud1.add(ref5);
		ref1.tell(new ConstructMessage(filsNoeud1), ref1);
		/*ref2 va recevoir fils:{3,4} et père : {1}*/
		List<ActorRef> filsNoeud2 = new ArrayList<ActorRef>();
		filsNoeud2.add(ref3);
		filsNoeud2.add(ref4);
		ref2.tell(new ConstructMessage(filsNoeud2,ref1), ref2);
		/*ref3 va recevoir fils:{} et père : {2}*/
		List<ActorRef> filsNoeud3 = new ArrayList<ActorRef>();
		ref3.tell(new ConstructMessage(filsNoeud3, ref2), ref3);
		/*ref4 va recevoir fils:{} et père : {2}*/
		List<ActorRef> filsNoeud4= new ArrayList<ActorRef>();
		ref4.tell(new ConstructMessage(filsNoeud4, ref2), ref4);
		/*ref5 va recevoir fils:{6} et père : {1}*/
		List<ActorRef> filsNoeud5 = new ArrayList<ActorRef>();
		ref5.tell(new ConstructMessage(filsNoeud5,ref1), ref6);
		filsNoeud5.add(ref6);
		/*ref6 va recevoir fils:{} et père : {5}*/
		List<ActorRef> filsNoeud6 = new ArrayList<ActorRef>();
		ref6.tell(new ConstructMessage(filsNoeud6, ref5), ref6);
		filsNoeud4.add(ref6);
		/*ref4.tell(new ConstructMessage(filsNoeud4,ref2),ref4);*/
		system.shutdown();
		
		
	}

}
