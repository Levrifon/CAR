package arbres;
import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;


public class Noeud extends UntypedActor{
	private List<ActorRef> fils;
	private ActorRef father;
	private boolean isLeaf;
	private String messagerecu;
	public Noeud() {
		this.fils = new ArrayList<ActorRef>();
		isLeaf = false;
	}
	@Override
	public void onReceive(Object message) throws InterruptedException {
		/* si c'est juste un passage de message on l'envoie à tous les fils */
		if(message instanceof Message) {
			System.out.println("Je suis : " + this.getMyName());
			for(ActorRef a : fils) {
				System.out.println(" Sending message to " + getItsName(a));
				a.tell(message, getSelf());
			}
			if(!getSender().equals(self())) {
				messagerecu = message.toString();
			}
			/* sinon si c'est un message de construction d'arbre ou de création de père etc */
		} else if(message instanceof ConstructMessage) {
			for(ActorRef a : ((ConstructMessage) message).getChildrenToMake()) {
				this.addChild(a);
			}
			/* si le message spécifie le père */
			if(((ConstructMessage) message).knowHisFather()) {
				this.father = ((ConstructMessage) message).getMyFather();
				System.out.println("Je suis : " + this.getMyName() + " mes fils sont : " + fils + ", Mon pere est : " +  getItsName(father));
			} else {
				System.out.println("Je suis : " + this.getMyName() + " mes fils sont : " + fils);
			}
		} else {
			unhandled(message);
		}
		
	}
	public void addChild(ActorRef n) {
		fils.add(n);
	}
	
	private String getMyName() {
		return this.self().path().name();
	}
	
	private String getItsName(ActorRef a) {
		return a.path().name();
	}
	public String getReceivedMessage() {
		return this.messagerecu;
	}
}
