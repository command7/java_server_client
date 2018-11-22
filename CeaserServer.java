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
               String response = "";
               while(true)
               {
                  String msg = clientRequest.readLine();
                  if (msg.equals("."))
                  {
                     serverResponse.write(response);
                     System.out.println("Reply to client successful");
                     break;
                  }
                  else
                  {
                     response = response + msg + "\n";
                  }
               }
               serverResponse.flush();
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