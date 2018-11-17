import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client
{
   public Client()
   {
      Scanner input = new Scanner(System.in);
      try
      {
         Socket serverCon = new Socket("192.168.1.4", 1234);
         BufferedReader serverResponse = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
         PrintWriter clientRequest = new PrintWriter(serverCon.getOutputStream());
         while (true)
         {
            String msg = input.nextLine();
            clientRequest.println(msg);
            clientRequest.flush();
            if (msg.equals("."))
            {
               String output = serverResponse.readLine();
               System.out.println(output);
               break;
            }
         }
      }
      catch (Exception e) {}

   }
   public static void main(String [] args)
   {
      Client test = new Client();
   }
}