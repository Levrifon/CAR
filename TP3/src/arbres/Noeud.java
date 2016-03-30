package arbres;
import java.util.ArrayList;
import java.util.List;

import message.ConstructMessage;
import message.CounterMessage;
import message.Message;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * 
 * @author Remy.D
 *
 */
public class Noeud extends UntypedActor{
	private List<ActorRef> fils;
	private ActorRef father;
	public Noeud() {
		this.fils = new ArrayList<ActorRef>();
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
			/* sinon si c'est un message qui compte le nombre de transferts */
		}else if(message instanceof CounterMessage) {
			for(ActorRef a : fils) {
				((CounterMessage) message).incrementMessage(); /* on incrémente le nombre de messages envoyés courant */
				a.tell(message,getSelf());
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
}
