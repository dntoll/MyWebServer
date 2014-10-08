package se.lnu.http;

import se.lnu.http.exceptions.InvalidPortException;

public class Port {

	private int portNumber;

	public Port(int portNumber) throws InvalidPortException {
		if (portNumber <= 0 || portNumber > 65535) {
			throw new InvalidPortException("invalid port number please choose a port between 1 and 65535");
		}
		
		this.portNumber = portNumber;
		
	}

	public int getPort() {
		return portNumber;
	}
	
}
