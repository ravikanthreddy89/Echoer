import java.io.*;
import java.net.*;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;


class Input extends Thread {
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		int curr_cons=0;
		String cmd;
		String temp;
		String temp2;
		int total_conns=0;
		int remote_pt;
		ArrayList<Datum> ar= new ArrayList<Datum>(20);
		public void run () {
		
		
			
		while(true) {
			try {
			cmd=inFromUser.readLine();
			cmd.trim();
			StringTokenizer st= new StringTokenizer(cmd);
			
		if(st.hasMoreTokens())
		{
			temp=st.nextToken();
				// Info command handling
					if(temp.equals("info")){
			       
						InetAddress addr=  Echoer.IP;
						System.out.println("Ipaddress\tHostname\tudp port\t tcp port");
						System.out.println(addr.getHostAddress()+"\t"+InetAddress.getLocalHost().getHostName()+"\t"+Echoer.UDP_PORT+"\t         "+Echoer.TCP_PORT);
							// InetAddress addr1=InetAddress.getByName(addr.getHostName());
							} 
			  // end of info command code
					
			  // connect command code
                       
				     else if(temp.equals("connect")){
					     try
					       {
					    	 temp2= st.nextToken();
					    	 
					    	 							 
							     InetAddress ip= InetAddress.getByName(temp2);
								 remote_pt = Integer.parseInt(st.nextToken());
							
								 if(remote_pt > 65000 || remote_pt < 0 ) {
									 	System.out.println("Enter a valid port number");
									 	continue;
									}
			      //duplicate connection check
						
						
						boolean jump=false;
						for(int g=0;g<ar.size();g++)
						{
							if(ar.get(g).remote_port == remote_pt && ar.get(g).s.getInetAddress().equals(ip) ){
						            System.out.println("Error : Connection already exits");
						            jump=true;
						            break;
							}
						
						}
						if(jump)	{
							continue; // continue the main loop to wait for the user command
							}	
					//	duplicate connection check
						//Self connection check
						if(InetAddress.getLocalHost().getHostAddress().equals(ip.getHostAddress())&&remote_pt==Echoer.TCP_PORT)
						{
							System.out.println("\nSelf Connection Error: Attempting to connect to Self");
						continue;
						}
						//Self connection check
						// checking whether it is valid ip or not
						System.out.println("validating ip");
						StringTokenizer lm= new StringTokenizer(temp2,".");
						 int byte1= Integer.parseInt(lm.nextToken());
						 int byte2 = Integer.parseInt(lm.nextToken());
						 int byte3 = Integer.parseInt(lm.nextToken());
						 int byte4 = Integer.parseInt(lm.nextToken());
						  
						 if(byte1 > 255 || byte2 > 255 || byte3 > 255 || byte4 > 255 ){
							 System.out.println("Invalid Ip. Enter a valid Ip");
							 continue;
						 }

						ar.add(new Datum());
					//System.out.println(InetAddress.getLocalHost().getHostAddress()+"\n"+ip.getHostAddress());
						
					ar.get(ar.size()-1).s= new Socket(ip,remote_pt);
					if(ar.get(ar.size()-1).s.isConnected())
						System.out.println("\nConnection established to "+ar.get(ar.size()-1).s.getRemoteSocketAddress());
						
					ar.get(ar.size()-1).remote_port= remote_pt;
					ar.get(ar.size()-1).conn_id=ar.size();  	
					
					}
					     
		
			      catch(NumberFormatException e)
			      {
			    	  System.out.println("\nError:enter valid port number");
			      
			      }
					
			      
					catch(NoSuchElementException e)
					{
						System.out.println("Error:\n Syntax :connect <ipaddress>  <port number>");

					}
					
					
					
				}
				
				else if(temp.equals("send")){
					int i=0;
					try
					{
					int id=Integer.parseInt(st.nextToken());
					
					for ( i=0;i<ar.size();i++)
					{
						if(ar.get(i).conn_id==id)
							break;
					}
				     if(i>=ar.size())
				     {
				    	System.out.println("Error :No such connection id"); 
				     }
				     else
				     {
					PrintWriter pw= new PrintWriter(((Datum )ar.get(i)).s.getOutputStream(),true);
					if(st.hasMoreTokens())
					{
					String s=st.nextToken();
					
						pw.println(s);
					}
					else
						pw.println(" ");
				     }
					}
					catch(NoSuchElementException   e)
					{
						System.out.println("Enter a valid connection id");
					}
				}
		
								
				else if (temp.equals("disconnect")){
					 int i;
					 try
					 {
					 
						 int j=Integer.parseInt(st.nextToken());
					 
					  // find the index of the given connection id
						 for(i=0;i<ar.size();i++)
						 {
							 if(j==ar.get(i).conn_id)
								 break;
						 }
						 if(i>=ar.size())
						 {
							 System.out.println("no such connection id");
						 }
						 else
						 {
							 ar.get(i).s.close();
							 ar.remove(i); 
							 
							
							 // now the connection ids of rest of the connection should be update which is done below
							       for(int k=i; k < ar.size();k++) {
						    	   ar.get(k).conn_id= k+1;
						    	   
							       }
						 }
					 
					 }
					 catch(NumberFormatException e)
					 {
						 System.out.println("Error: Enter valid connection id");
					 }
					 catch(NoSuchElementException e)
					 {
						 System.out.println("Syntax error:\nSyntax: disconnect <connection id>");
					 }
				}
					
				else if(temp.equals("sendto")){
					try
					{
					InetAddress udpip = InetAddress.getByName(st.nextToken());
					int udpport= Integer.parseInt(st.nextToken());
					byte [] send_data= new byte[1024];
					DatagramSocket client_socket= new DatagramSocket();
					String msg_container="";
					
					while(st.hasMoreElements()){
					msg_container=msg_container+" "+st.nextToken().toString();
					
					}
					
					
					send_data= msg_container.getBytes();
					DatagramPacket send_packet= new DatagramPacket(send_data, send_data.length, udpip, udpport);
					
					client_socket.send(send_packet);
					}
					catch(NoSuchElementException e)
					{
						System.out.println("\nSyntax Error:\nSyntax: sendto <IP address> <UDP Port number> <Message>");
					}
					catch(NumberFormatException e)
					{
						System.out.println("\nInvalid port number");
					}
				   }
				else if(temp.equals("show")){
					System.out.println("Conn ID\t Ip \t Hostname \t Localport \t RemotePort"); 
					for(int i =0; i< ar.size(); i++) {
						  int connection_id= ((Datum )ar.get(i)).conn_id;
						  InetAddress IPP = ((Datum )ar.get(i)).s.getInetAddress();
						  String hostname= IPP.getHostName();
						  int localport= ((Datum )ar.get(i)).s.getLocalPort();
						  int rmt_port= ((Datum )ar.get(i)).s.getPort();
						  
						  System.out.println(connection_id+"\t|"+IPP+"\t|"+hostname+"\t|"+localport+"\t|"+rmt_port);
						  
					 }
				}
				else 
				{
					System.out.println("\nInvalid command");
				}
		
		}
		}
		
			catch(Exception e) {
				 System.out.println(e);
				}
		
		}
		
		
}
}