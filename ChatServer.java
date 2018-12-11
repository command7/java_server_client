/**
*Purpose:   This program is used to initiate a server that can receives messages from a client
*           and broadcasts to all clients. It acts as a chat server.
*                                                                  <br/>
*Date:      December 10, 2018
*@author    Vijay Raj Saravanan Radhakrishnan
*@version   1.1
*/

import java.io.*;
import java.net.*;
import java.util.*;
public class ChatServer
{
   /** Stores OutputStream of all clients to broadcast messages */
   private Vector<PrintWriter> broadcaster = new Vector<PrintWriter>();
   
/**
*  Constructor initializes the server, and listens for incoming requests at port 16789
*/
   public ChatServer()
   { 
      startServer();
   }
   
/**
*  Constantly listens for client requests and creates a thread for each client request
*/      
   public void startServer()
   {
      try
      {
         ServerSocket server = new ServerSocket(16789);
         
         while(true)
         {
            Socket serverSocket = server.accept();
            
            System.out.println("Client " + serverSocket.getInetAddress() + " connected");
            new ServerThread(serverSocket).start();
         }
      }
      catch (BindException be)
     {
         System.out.println("Port in use. Bind failed");
     }
     catch (IOException ex)
     {
      System.out.println("IO Exception");
     }
   }
   
/**
*  Broadcasts a message to all clients connected to the server.
*  @param msg Message to be broadcasted
*/
   public void broadcastMessages(String msg)
   {
      for(PrintWriter recipient: broadcaster)
      {
         recipient.println(msg);
         recipient.flush();
      }
   }

   /**
   *  Purpose: Create a thread for a client and constants responds to input messages and broadcasts them.
   *  Date: November 25, 2018
   */
   protected class ServerThread extends Thread
   {
      /** Socket at which the client is connected */
      private Socket serverSocket;
      
   /**
   *  Sets the Socket variable
   *  @param _s Socket at which client is connected
   */
      public ServerThread(Socket _s)
      {
         serverSocket = _s;
      }
      
   /**
   *  Receives input messages and broadcasts it until quit command is received in which case
   *  disconnects the client from the server.
   */
      public void run()
      {
         try
         {
            BufferedReader clientRequest = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter serverResponse = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            broadcaster.add(serverResponse);
            serverResponse.println("Welcome to chat screen");
            serverResponse.flush();
            while(true)
            { 
               String message = clientRequest.readLine();
               if(message.equals("quit"))
               {
//                   message = serverSocket.getInetAddress() + " has disconnected.";
//                   broadcastMessages(message);
                  clientRequest.close();
                  serverResponse.close();
                  serverSocket.close();
                  break;
               }
               broadcastMessages(message);
            }
            System.out.println("Client " + serverSocket.getInetAddress() + " disconnected");
         }
         catch (NullPointerException npe) {
            System.out.println("Client " + serverSocket.getInetAddress() + " disconnected");
         }
         catch (Exception e) {
         e.printStackTrace();}
      }

   }   

/**
*  Initializes the chat server
*  @param args None
*/
   public static void main(String [] args)
   {
      ChatServer test = new ChatServer();
   }
}