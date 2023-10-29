
/**
 * This class constructs the UI for a chat server.  It implements the
 * chat interface in order to activate the display() method.
 *
 * @author BG
 * @version 2023
 */
//**** Changed for E50 BG
import java.io.*;
import common.*;
import ocsf.server.*;

//**** Added for E50 BG

public class ServerConsole implements ChatIF {
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  EchoServer server;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the EchoServer UI.
   *
   * @param port The port to connect on.
   */
  public ServerConsole(int port) 
  {
      server= new EchoServer(port);
      
      try 
      {
        server.listen(); //Start listening for connections
      } 
      catch (Exception ex) 
      {
        System.out.println("ERROR - Could not listen for clients!");
      }      
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the server's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
        server.handleMessageFromServerUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Server UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    int port = DEFAULT_PORT;  //The port number

    
    if(args.length>0) port = Integer.parseInt(args[0]);
    
    ServerConsole chat= new ServerConsole(port);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class


