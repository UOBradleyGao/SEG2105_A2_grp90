// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
//**** Changed for E49 BG
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A client connected");
  }
  
//**** Changed for E49 BG
  @Override
  synchronized protected void clientDisconnected(
		    ConnectionToClient client) {
	  System.out.println("A client disconnected");
	  
  }
//**** Changed for E49 BG
  synchronized protected void clientException(
		    ConnectionToClient client, Throwable exception) {
	  System.out.println("A client disconnected");	  
  }
  
  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
//**** Changed for E50 BG
  public void handleMessageFromServerUI(String message)
  {
	//Splits message into commands to be used later
		String arr[] = message.split(" ");
		String firstWord = arr[0]; 
		
		if (firstWord.equals("#quit")) {
			try {
				close();
			} catch (IOException e) {
				System.out.println("An I/O error occurs while closing the server socket");
			}
			System.exit(0);
		  }
		else if(firstWord.equals("#stop")) {			
			stopListening();
		}
		else if(firstWord.equals("#close")) {	
			try {
				close();
			} catch (IOException e) {
				System.out.println("An I/O error occurs while closing the server socket");
			}
			
		}
		else if(firstWord.equals("#setport")) {			
			setPort(Integer.parseInt(arr[1]));
		}
		
		else if(firstWord.equals("#start")) {
			if(isListening()) {
				System.out.println("The server has not stopped, command ignored");
			}	else {
				try {
					listen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		
		}
		else if(firstWord.equals("#getport")) {	
			System.out.println("Port: "+ getPort());
		}


	else {  //Echo message on the server and client console
	    	//serverUI.display(">"+message);
			System.out.println(">"+message);
	    	sendToAllClients("SERVER MSG>"+ message);
	  }
    }  
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
