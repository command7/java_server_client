/**
*Purpose:   This program is used to initiate a server that can accept multiple lines of text,
*           encrypt/decrypt the text and send it back. It listens at port 16789 and by default
*           uses 3 as the shift key. Custom shift keys can be supplied as input to the program.
*                                                                  <br/>
*Caveats:   It converts text to lowercase and encrypts/decrypts it.<br/>
*           Only alphabets can be processed. <br/>
*Date:      November 25, 2018
*@author    Vijay Raj Saravanan Radhakrishnan
*@version   1.1
*/
import java.io.*;
import java.net.*;
public class CaeserServer implements CaesarConstants
{
   /** Contains all the alphabets in order */
   private final String [] ALPHABETS = {"a","b","c","d","e","f","g",
   "h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
   /**Number of characters to be shifted */
   private int shiftKey;
   /** ServerSocket that will be used to listen */
   private ServerSocket server;
   
/**
*  Constructor takes in the shift key and assigns it to variable
*  @param _shiftKey Number of characters to be shifted
*/
   public CaeserServer(int _shiftKey)
   {
      this.shiftKey = _shiftKey;
      startServer();
   }//end of constructor

/**
*  Starts listening for incoming messages at port 16789. Once a client connects,
*  It creates a thread for the client.
*/
   public void startServer()
   {
      try
      {
         server = new ServerSocket(PORT_NUMBER);
         while(true)
         {
            Socket serverSocket = server.accept();
            new ServerThread(serverSocket).start();
         }
      }
      catch (Exception ex) {}
   }//end of startServer()

/**
*  Encrypts the text by shifting each character by the shift key
*  @param _msg Message to be encrypted
*  @return Encrypted text
*/
   public String encryptText(String _msg)
   {
      String msg = _msg.toLowerCase();
      String cipher = "";
      for(int index=0; index<msg.length(); index++)
      {
         boolean isNumber = true;
         if (String.valueOf(msg.charAt(index)).equals(" "))
         {
            cipher += " ";
            continue;
         }
         int shiftNumber = -1;
         int indexOfChar = -1;
         for(int i=0; i < ALPHABETS.length; i++)
         {
            if(String.valueOf(msg.charAt(index)).equals(ALPHABETS[i]))
            {
               indexOfChar = i;
               isNumber = false;
               break;
            }
         }
         if(isNumber)
         {
            cipher += String.valueOf(msg.charAt(index));
            continue;
         }
         else
         {
            shiftNumber = indexOfChar + shiftKey;
            if(shiftNumber < 26)
            {
               cipher += ALPHABETS[shiftNumber];
            }
            else
            {
               shiftNumber = shiftNumber%26;
               cipher += ALPHABETS[shiftNumber];
            }
         }
      }
      return cipher;
   }//end of encryptText()

/**
*  Decrypts the text by shifting each character by the shift key
*  @param _cipher Cipher to be decrypted
*  @return Decrypted text
*/
   public String decryptText(String _cipher)
   {
      String cipher = _cipher.toLowerCase();
      String msg = "";
      for(int index=0; index<cipher.length(); index++)
      {
         boolean isNumber = true;
         if (String.valueOf(cipher.charAt(index)).equals(" "))
         {
            msg += " ";
            continue;
         }
         int shiftNumber = -1;
         int indexOfChar = -1;
         for(int i=0; i < ALPHABETS.length; i++)
         {
            if(String.valueOf(cipher.charAt(index)).equals(ALPHABETS[i]))
            {
               indexOfChar = i;
               isNumber = false;
               break;
            }
         }
         if(isNumber)
         {
            msg += String.valueOf(cipher.charAt(index));
            continue;
         }
         else
         {
            if(indexOfChar >= shiftKey)
            {
               shiftNumber = indexOfChar - shiftKey;
               msg += ALPHABETS[shiftNumber];
            }
            else
            {
               shiftNumber = indexOfChar - shiftKey;
               shiftNumber = shiftNumber + 26;
               msg += ALPHABETS[shiftNumber];
            }
         }
      }
         return msg;
   }//end of decryptText()
   
   /**
   *  Creates a thread that handles a connection with a client by responding with
   *  encrypted or decrypted text based on the input command.
   */
   public class ServerThread extends Thread
   {
      /** Socket at which a client is connected */
      private Socket serverSocket;
      
   /**
   *  Constructor takes in the socket to which a client is connected and assigns it to
   *  private variable.
   *  @param _s Socket
   */
      public ServerThread(Socket _s)
      {
         serverSocket = _s;
      }//end of constructor
      
   /**
   *  Constantly listens to the client and responds with encrypted or decrypted
   *  content based on the input command.
   **/
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
      }//end of run() 

   }//end of class ServerThread
/**
*  Initializes a server instance and starts listening for incoming connections
*  @param args Shift key
*/   
   public static void main(String [] args)
   {
      try
      {
         if(args.length == 0)
         {
            CaeserServer test = new CaeserServer(DEFAULT_SHIFT);
         }
         else 
         {
            int key = Integer.parseInt(args[0]);
            if(key > 0 && key < 26)
            {
               CaeserServer test = new CaeserServer(key);
            }
            else
            {
               System.out.println("Key should be between 1 and 25 inclusive");
            }
         }
      }
      catch (NumberFormatException nfe)
      {
         System.out.println("Enter a number between 1 and 25");
      }
   }//end of main class
}//end of class CaeserServer
