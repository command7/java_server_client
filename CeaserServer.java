import java.io.*;
import java.net.*;
public class CeaserServer
{
   public CeaserServer()
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
      public ServerThread(Socket _socket)
      {
         this.serverSocket = _socket;
      }
      
      public void run()
      {
         while(true)
         {
            try
            {
               BufferedReader clientRequest = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
               PrintWriter serverResponse = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
               String inputCommand = clientRequest.readLine();
               System.out.println(inputCommand);
               String message;
               if (inputCommand.equals("ENCRYPT"))
               {
                  serverResponse.println("OK");
                  serverResponse.flush();
                  message = clientRequest.readLine();
                  System.out.println("Msg to be encrypted: " + message);
                  serverResponse.println("Working on encryption");
                  serverResponse.flush();
               }
               else if (inputCommand.equals("DECRYPT"))
               {
                  message = clientRequest.readLine();
                  System.out.println("Msg to be decrypted: " + message);
               }
               else
               {
                  System.out.println("Invalid command");
               }
               //serverResponse.flush();
               clientRequest.close();
               serverResponse.close();
               serverSocket.close();
            }
            catch (Exception e) {}
         }
      }
   }

   public static void main(String [] args)
   {
      CeaserServer test = new CeaserServer();
   }
}