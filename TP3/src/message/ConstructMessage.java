package message;

import java.io.Serializable;
import java.util.List;

import akka.actor.ActorRef;


public class ConstructMessage extends AbstractMessage implements Serializable{
	private static final long serialVersionUID = 5977777080166833290L;
	List<ActorRef> thisisyourchild;
	ActorRef thisisyourfather;
	/**
	 * Allows to create children from our node
	 * @param children to make from current node
	 */
	public ConstructMessage(List<ActorRef> children) {
		this.thisisyourchild = children;
	}
	/**
	 * Allows to create children from current node AND to know who is our father
	 * @param children
	 * @param father
	 */
	public ConstructMessage(List<ActorRef> children, ActorRef father) {
		this.thisisyourchild = children;
		this.thisisyourfather = father;
	}
	/**
	 * get the list of children of current node
	 * @return list of children
	 */
	public List<ActorRef> getChildrenToMake() {
		return this.thisisyourchild;
	}
	/**
	 * 
	 * @return node's father
	 */
	public ActorRef getMyFather() {
		return thisisyourfather;
	}
	/**
	 * return true if the message is building the tree 
	 */
	@Override
	public boolean isConstructive() {
		return true;
	}
	
	public boolean knowHisFather() {
		return thisisyourfather != null;
	}
}
