package message;
import java.io.Serializable;


public class Message extends AbstractMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Message(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	@Override
	public boolean isConstructive() {
		return false;
	}

}
