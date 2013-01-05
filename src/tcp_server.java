import java.io.IOException;
import java.net.*;
public class tcp_server  extends Thread {
	 int server_port ;
	 
	 tcp_server(int x) {
		  server_port= x;
	 }
	 public void run () {
		try {
			ServerSocket listener = new ServerSocket(server_port);
			while(true) {			
		    Socket s =listener.accept();
		    TcpConnectionHandler tcp=new TcpConnectionHandler(s);
		    System.out.println("got Conn. request from "+s.getInetAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	 }
	

}
