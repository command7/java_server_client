import java.io.*;
import java.net.*;
public class CeaserServer
{
   String [] alphabets = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
   private int shiftKey;
   private ServerSocket server;
   public CeaserServer(int _shiftKey)
   {
      this.shiftKey = _shiftKey;
      startServer();
   }

   public void startServer()
   {
      try
      {
         server = new ServerSocket(16789);
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
         if (String.valueOf(msg.charAt(index)).equals(" "))
         {
            cipher += " ";
            continue;
         }
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
         shiftNumber = indexOfChar + shiftKey;
         if(shiftNumber < 26)
         {
            cipher += alphabets[shiftNumber];
         }
         else
         {
            shiftNumber = shiftNumber%26;
            cipher += alphabets[shiftNumber];
         }
      }
      return cipher;
   }

   public String decryptText(String _cipher)
   {
      String cipher = _cipher.toLowerCase();
      String msg = "";
      for(int index=0; index<cipher.length(); index++)
      {
         if (String.valueOf(cipher.charAt(index)).equals(" "))
         {
            msg += " ";
            continue;
         }
         int shiftNumber = -1;
         int indexOfChar = -1;
         for(int i=0; i < alphabets.length; i++)
         {
            if(String.valueOf(cipher.charAt(index)).equals(alphabets[i]))
            {
               indexOfChar = i;
               break;
            }
         }
         if(indexOfChar >= shiftKey)
         {
            shiftNumber = indexOfChar - shiftKey;
            msg += alphabets[shiftNumber];
         }
         else
         {
            shiftNumber = indexOfChar - shiftKey;
            shiftNumber = shiftNumber + 26;
            msg += alphabets[shiftNumber];
         }
      }
         return msg;
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
            while(true)
            { 
               BufferedReader clientRequest = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
               PrintWriter serverResponse = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
               String inputCommand = clientRequest.readLine();
               System.out.println("Client Request: " + inputCommand);
               String input;
               String temp;
               if (inputCommand.equals("ENCRYPT"))
               {
                  System.out.println("Server Response: OK");
                  serverResponse.println("OK");
                  serverResponse.flush();
                  while((input = clientRequest.readLine()) != null)
                  {
                     serverResponse.println(encryptText(input));
                     serverResponse.flush();
                  }
               }
               else if (inputCommand.equals("DECRYPT"))
               {
                  System.out.println("Server Response: OK");
                  serverResponse.println("OK");
                  serverResponse.flush();
                  while((input = clientRequest.readLine()) != null)
                  {
                     String decrypt = decryptText(input);
                     serverResponse.println(decrypt);
                     serverResponse.flush();
                  }
               }
               else
               {
                  System.out.println("Server Response: Invalid command");
                  serverResponse.println("Invalid Command");
                  serverResponse.flush();
               }
               clientRequest.close();
               serverResponse.close();
               serverSocket.close(); 
            }
         }
         catch (Exception e) {}
      }

   }   
   public static void main(String [] args)
   {
      try
      {
         int key = Integer.parseInt(args[0]);
         if(args.length == 0)
         {
            CeaserServer test = new CeaserServer(3);
         }
         else if(key > 0 && key < 26)
         {
            CeaserServer test = new CeaserServer(key);
         }
         else
         {
            System.out.println("Key should be between 1 and 25 inclusive");
         }
      }
      catch (NumberFormatException nfe)
      {
         System.out.println("Enter a number between 1 and 25");
      }
   }
}
