import java.io.*;
import 
public class Server
{
   public Server()
   {
      ServerSocket server = new ServerSocket(80);
      Socket client = server.accept();
      BufferedReader clientRequest = new BufferedReader(new InputStreamReader(client.getInputStream()));
      PrintWriter clientResponse = new PrintWriter(new getOutputStream());
      String response = "";
      String msg = clientRequest.readLine();
      response += msg;
      clientResponse.println(response); 
   }
}