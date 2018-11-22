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
            //serverSocket = server.accept();
//             while(true)
//             {
//                try
//                {
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
                        // if(input.equals(""))
   //                      {
   //                         serverResponse.println("");
   //                         serverResponse.flush();
   //                         continue;
   //                      }
   //                      else
                        //{
                           System.out.println(input);
                           //System.out.println(decryptText(input));
                           String decrypt = decryptText(input);
                           System.out.println(decrypt);
                           serverResponse.println(decrypt);
                           serverResponse.flush();
                        //}
                        //System.out.println(input);
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
 //               }
//                catch (Exception e) {
//                   e.printStackTrace();
//                }
//            }
//          try{
//          serverSocket.close();
//          }
//          catch (Exception e) {}

            
         }
      }
      catch (Exception ex) {}
      // try
//       {
//          Socket serverSocket;
//          new ServerThread(serverSocket).start();
// 
//       }
//       catch (Exception ex) {}
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
         shiftNumber = indexOfChar + 3;
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
         if(indexOfChar >= 3)
         {
            shiftNumber = indexOfChar - 3;
            msg += alphabets[shiftNumber];
         }
         else
         {
            shiftNumber = indexOfChar - 3;
            shiftNumber = shiftNumber + 26;
            msg += alphabets[shiftNumber];
         }
      }
         return msg;
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
         //serverSocket = server.accept();
         while(true)
         {
            try
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
                     // if(input.equals(""))
//                      {
//                         serverResponse.println("");
//                         serverResponse.flush();
//                         continue;
//                      }
//                      else
                     //{
                        System.out.println(input);
                        //System.out.println(decryptText(input));
                        String decrypt = decryptText(input);
                        System.out.println(decrypt);
                        serverResponse.println(decrypt);
                        serverResponse.flush();
                     //}
                     //System.out.println(input);
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
               break;
               //serverSocket.close();
            }
            catch (Exception e) {
               e.printStackTrace();
            }
         }
         try{
         serverSocket.close();
         }
         catch (Exception e) {}
      }


   }

   public static void main(String [] args)
   {
      CeaserServer test = new CeaserServer();
   }
}
