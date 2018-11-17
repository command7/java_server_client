import java.io.*
public class Client
{
   public Client()
   {
      Socket serverCon = new Socket(IP, Port);
      BufferedReader serverResponse = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
      PrintWriter clientRequest = new PrintWriter(serverCon.getOutputStream());
   }
}