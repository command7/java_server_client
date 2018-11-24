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
            new ServerThread(serverSocket).start();
         }
      }
      catch (Exception ex) {}
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
            while(true)
            { 
               String inputMessage = clientRequest.readLine();
               if(inputMessage.equals("quit"))
               {
                  clientRequest.close();
                  serverResponse.close();
                  serverSocket.close();
               }
               serverResponse.println(inputMessage);
               serverResponse.flush();
               //serverSocket.close(); 
            }

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