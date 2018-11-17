import java.io.*;
import java.net.*;
public class Server
{
   public Server()
   {
      try
      {
         ServerSocket server = new ServerSocket(1234);
         System.out.println("Listening for input requests");
         Socket clientCon = server.accept();
         BufferedReader clientRequest = new BufferedReader(new InputStreamReader(clientCon.getInputStream()));
         PrintWriter clientResponse = new PrintWriter(clientCon.getOutputStream());
         String response = "";
         while(true)
         {
            String msg = clientRequest.readLine();
            if (msg.equals("."))
            {
               clientResponse.println(response);
               clientResponse.flush();
               break;
            }
            else
            {
               response += msg;
            }
         }
         clientRequest.close();
         clientResponse.close();
         clientCon.close();
      }
      catch (Exception e) {} 
   }
   public static void main(String [] args)
   {
      Server test = new Server();
   }
}