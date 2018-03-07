package chatServer;

public class MainServer {

	public static void main(String[] args) {
		TCPServer tcpServer = new TCPServer(20000, 10);
	}
}
