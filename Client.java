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
         Socket serverCon = new Socket("localhost", 1234);
         BufferedReader serverResponse = new BufferedReader(new InputStreamReader(serverCon.getInputStream()));
         PrintWriter clientRequest = new PrintWriter(serverCon.getOutputStream());
         String output = "";
         System.out.println("Enter datas to be sent to server");
         while (true)
         {
            String msg = input.nextLine();
            String line;
            clientRequest.println(msg);
            clientRequest.flush();
            if (msg.equals("."))
            {
               while((line = serverResponse.readLine()) != null)
               {
                  output = output + line + "\n";
               }
               System.out.println(output);
               break;
            }
         }
         clientRequest.close();
         serverResponse.close();
         serverCon.close();
      }
      catch (Exception e) {}

   }
   public static void main(String [] args)
   {
      Client test = new Client();
   }
}