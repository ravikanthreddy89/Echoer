import java.net.DatagramPacket;
import java.net.*;

public class udp_server extends Thread {
	int udp_port ;
	 udp_server(int udp_port) {
		  this.udp_port=udp_port;
	 }
	 
	 public void run() {
		 byte[] receive_data= new byte[1024];  // packet parameters
		 //byte[] send_data= new byte[1024];
		 int recv_port;
		 try {
			DatagramSocket d= new DatagramSocket(udp_port);     // listening socket
			System.out.println("UDP Server running on port:"+udp_port);
			while(true){
				DatagramPacket receive_packet = new DatagramPacket (receive_data,receive_data.length); // packet collector
				
				d.receive(receive_packet);  // getting the packet
				String data = new String (receive_packet.getData(),0,receive_packet.getLength()); //retrieving the data from the packet collector
				InetAddress ip= receive_packet.getAddress(); // quering for ip address of the source
				recv_port = receive_packet.getPort(); // quering for port number
				
				System.out.println("echoing : "+data+"\n \t to : IP ="+ip+"\n \t type= UDP");
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	 }

}
