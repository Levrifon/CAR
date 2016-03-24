package arbres;
import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;


public class Noeud extends UntypedActor{
	List<ActorRef> fils;
	ActorRef father;
	public Noeud() {
		this.fils = new ArrayList<ActorRef>();
	}
	@Override
	public void onReceive(Object message) throws InterruptedException {
		if(message instanceof Message) {
			for(ActorRef a : fils) {
				System.out.println("Send message to " + a.path().name());
				a.tell(message, getSelf());
			}
			System.out.println("Fin transmission");
		} else if(message instanceof ConstructMessage) {
			for(ActorRef a : ((ConstructMessage) message).getChildrenToMake()) {
				this.addChild(a);
			}
			if(((ConstructMessage) message).knowHisFather()) {
				this.father = ((ConstructMessage) message).getMyFather();
				System.out.println("Mon pere est : " + this.father.path().name());
			}
			System.out.println("Je suis " + this.self().path().name() + " mes fils sont :" + fils);
		}
		
	}
	public void addChild(ActorRef n) {
		fils.add(n);
	}

}
