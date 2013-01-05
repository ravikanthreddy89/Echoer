import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
public class TcpConnectionHandler extends Thread{
	volatile Socket s;
	public TcpConnectionHandler(Socket s) {
		// TODO Auto-generated constructor stub
	this.s=s;
	this.start();
	}
public void run() 
{
 try
{
	 BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
	 PrintWriter pr=new PrintWriter(s.getOutputStream(),true);
	 String message=null;
while((message=br.readLine())!=null)
{
	
System.out.println("echoing:- "+message+"\n\tto: IP = "+s.getInetAddress()+"\n\ttype = tcp");
pr.write(message);
}
System.out.println("Client "+s.getRemoteSocketAddress()+" disconnnected");
s.close();
}
catch(Exception e){
System.out.println(e.toString());	
}
}
}