package chatServer;

public class MainServer {

	public static void main(String[] args) {
		OfflineWriter ow = new OfflineWriter("files/OfflineMap.txt");
		TCPServer tcpServer = new TCPServer(12345, 50, ow);
	}
}
