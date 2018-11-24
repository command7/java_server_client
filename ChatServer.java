import java.io.*;
import java.net.*;
import java.util.*;
public class ChatServer
{
   private Vector<PrintWriter> broadcaster = new Vector<PrintWriter>();
   public ChatServer()
   { 
      startServer();
   }
         
   public void startServer()
   {
      try
      {
         ServerSocket server = new ServerSocket(16789);
         
         while(true)
         {
            Socket serverSocket = server.accept();
            //System.out.println(serverSocket.getInetAddress());
            System.out.println("Client IP Address : " + serverSocket.getInetAddress());
            new ServerThread(serverSocket).start();
         }
      }
      catch (BindException be)
      {
         System.out.println("Port in use. Bind failed");
      }
      catch (Exception ex) {
      ex.printStackTrace();}
   }
   
   public void broadcastMessages(String msg)
   {
      for(PrintWriter recipient: broadcaster)
      {
         recipient.println(msg);
         recipient.flush();
      }
   }

   
   public class ServerThread extends Thread
   {
      private Socket serverSocket;
      
      public ServerThread(Socket _s)
      {
         serverSocket = _s;
      }
      
      public void run()
      {
         try
         {
            BufferedReader clientRequest = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter serverResponse = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            broadcaster.add(serverResponse);
            while(true)
            { 
               String message = clientRequest.readLine();
               if(message.equals("quit"))
               {
                  clientRequest.close();
                  serverResponse.close();
                  serverSocket.close();
                  break;
               }
               broadcastMessages(message);
            }
            System.out.println("Client " + serverSocket.getInetAddress() + " disconnected");
         }
         catch (Exception e) {
         e.printStackTrace();}
      }

   }   

   
   public static void main(String [] args)
   {
      ChatServer test = new ChatServer();
   }
}