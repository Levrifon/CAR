package message;

import java.io.Serializable;

public class CounterMessage extends AbstractMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private int counter;
	
	public CounterMessage(String message) {
		this.message = message;
		counter = 0;
	}
	
	public void incrementMessage() {
		counter++;
	}

	public int getCounter() {
		return this.counter;
	}

	@Override
	public boolean isConstructive() {
		return false;
	}
}
