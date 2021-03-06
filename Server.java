import java.io.*;
import java.net.*;
public class Server
{
   public Server()
   {
      try
      {
         ServerSocket server = new ServerSocket(1234);
         while(true)
         {
            System.out.println("Listening for input requests");
            Socket clientCon = server.accept();
            BufferedReader clientRequest = new BufferedReader(new InputStreamReader(clientCon.getInputStream()));
            PrintWriter clientResponse = new PrintWriter(new OutputStreamWriter(clientCon.getOutputStream()));
            String response = "";
            while(true)
            {
               String msg = clientRequest.readLine();
               if (msg.equals("."))
               {
                  clientResponse.write(response);
                  System.out.println("Reply to client successful");
                  break;
               }
               else
               {
                  response = response + msg + "\n";
               }
            }
            clientResponse.flush();
            clientRequest.close();
            clientResponse.close();
            clientCon.close();
         }
      }
      catch (Exception e) {}
   }

   public static void main(String [] args)
   {
      Server test = new Server();
   }
}