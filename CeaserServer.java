import java.io.*;
import java.net.*;
public class CeaserServer
{
   String [] alphabets = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
   int shiftKey = 3;
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
   
   public String encryptText(String _msg)
   {
      String msg = _msg.toLowerCase();
      String cipher = "";
      for(int index=0; index<msg.length(); index++)
      {
         int shiftNumber = -1;
         int indexOfChar = -1;
         for(int i=0; i < alphabets.length; i++)
         {
            if(String.valueOf(msg.charAt(index)).equals(alphabets[i]))
            {
               indexOfChar = i;
               break;
            }
         }
         shiftNumber = indexOfChar + 3;
//          if(shiftNumber < 26)
//          {
            cipher += alphabets[shiftNumber];
//          }
         // else
//          {
//             shiftNumber = shiftNumber%26;
//          }
      }
      return cipher;
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
                  serverResponse.println(encryptText(message));
                  serverResponse.flush();
               }
               else if (inputCommand.equals("DECRYPT"))
               {
                  serverResponse.println("OK");
                  serverResponse.flush();
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
      
      
      
      public void decryptText()
      {
      }
   }

   public static void main(String [] args)
   {
      CeaserServer test = new CeaserServer();
   }
}