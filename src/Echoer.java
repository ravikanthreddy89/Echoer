import java.io.*;
import java.net.*;
public class Echoer {
	static  int TCP_PORT;
	static  int UDP_PORT;
	static InetAddress IP;
	public static void main(String args[]){
		try
		{
		TCP_PORT = Integer.parseInt(args[0]);
		UDP_PORT = Integer.parseInt(args[1]);
		IP ip=new IP();
		IP=ip.getIP();
		if(TCP_PORT>60000||UDP_PORT>60000)
			throw new NumberFormatException() ;
		tcp_server ts = new tcp_server(TCP_PORT);
	    ts.start();
	    udp_server us= new udp_server(UDP_PORT);
	    us.start();
	    Input In=new Input();
	    In.start();
		}
		catch(NumberFormatException e)
		{
		System.out.println("\nInvalid port numbers:Valid port numbers are integers between 0 and 60000");	
		
		} 
	    
	}
}



