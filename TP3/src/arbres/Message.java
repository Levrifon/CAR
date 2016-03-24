package arbres;
import java.io.Serializable;


public class Message extends AbstractMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	public String who;
	
	public Message(String who) {
		this.who = who;
	}
	
	public String getMessage() {
		return this.who;
	}

	@Override
	public boolean isConstructive() {
		return false;
	}

}
