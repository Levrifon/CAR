package arbres;

import java.io.Serializable;
import java.util.List;

import akka.actor.ActorRef;


public class ConstructMessage extends AbstractMessage implements Serializable{
	private static final long serialVersionUID = 5977777080166833290L;
	List<ActorRef> thisisyourchild;
	ActorRef thisisyourfather;
	
	public ConstructMessage(List<ActorRef> children) {
		this.thisisyourchild = children;
	}
	
	public ConstructMessage(List<ActorRef> children, ActorRef father) {
		this.thisisyourchild = children;
		this.thisisyourfather = father;
	}
	
	public List<ActorRef> getChildrenToMake() {
		return this.thisisyourchild;
	}
	
	public ActorRef getMyFather() {
		return thisisyourfather;
	}

	@Override
	public boolean isConstructive() {
		return true;
	}
	
	public boolean knowHisFather() {
		return thisisyourfather != null;
	}
}
