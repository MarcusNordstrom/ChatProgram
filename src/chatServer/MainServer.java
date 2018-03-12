package chatServer;

public class MainServer {

	public static void main(String[] args) {
		TCPServer tcpServer = new TCPServer(12345, 50);
	}
}
