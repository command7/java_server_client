/**
*Purpose:   This program creates a JFrame with a receiving area and a sending area.
*           Text that the client sends is sent to all other participants of the chat
*           At the same time, messages from other clients are received and displayed.
*                                                                 <br/>
*Date:      November 25, 2018
*@author    Vijay Raj Saravanan Radhakrishnan
*@version   1.1
*/

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class ChatClient extends JFrame implements ActionListener
{
   /** BufferedReader used to read input from server */
   private BufferedReader receive;
   /** PrintWriter used to send data to server */
   private PrintWriter send;
   /** Text area where messages from other clients are received */
   private JTextArea recvBlock;
   /** Text area to type in messages to be sent to other participants */
   private JTextArea sendBlock;
   /** Button used to send data to the server */
   private JButton sendButton;
   /** Button used to exit the chatroom */
   private JButton exitButton;
   
/**
*  Creates the GUI of chat room and starts listening for input from the server
*/
   public ChatClient()
   {
      createGUI();
      startReceiver();
   }
   
/**
*  Creates GUI of chat room
*/
   public void createGUI()
   {
      this.setLayout(new BorderLayout());
      
      recvBlock = new JTextArea("",10,50);
      recvBlock.setBorder(new EtchedBorder());
      sendBlock = new JTextArea("Type message to send",10,50);
      sendBlock.setBorder(new EtchedBorder());
      JScrollPane recvMsg = new JScrollPane(recvBlock);
      JScrollPane sendMsg = new JScrollPane(sendBlock);
      JPanel textAreas = new JPanel();
      textAreas.setLayout(new GridLayout(0,1));
      textAreas.add(recvMsg);
      textAreas.add(sendMsg);
      this.add(textAreas, BorderLayout.CENTER);
      
      JPanel buttons = new JPanel();
      buttons.setLayout(new GridLayout(1,2));
      
      sendButton = new JButton("Send");
      exitButton = new JButton("Exit");
      buttons.add(sendButton);
      buttons.add(exitButton);
      sendButton.addActionListener(this);
      exitButton.addActionListener(this);
      this.add(buttons, BorderLayout.SOUTH);
      
      this.setTitle("Chat screen");
      this.setSize(600,400);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
      
      //Receiver incomingMsgs = new Receiver();
   }

/**
*  Creates a thread to constantly listen for server input
*/
   public void startReceiver()
   {
      try
      {
         Socket s = new Socket("localhost", 16789);
         receive = new BufferedReader(new InputStreamReader(s.getInputStream()));
         send = new PrintWriter(s.getOutputStream());
         Receiver recv = new Receiver(receive);
         recv.start();
      }
      catch (Exception e)
      {
         System.out.println("startReceiver Exception");
      }
   }
   
   /**
   *  Purpose: This program is used to constantly listen to input from server.
   *  Date: November 25, 2018
   */
   protected class Receiver extends Thread
   {
      private BufferedReader reader;
      
   /**
   *  Assigns the BufferedReader to private variable
   */
      public Receiver(BufferedReader _reader)
      {
         this.reader = _reader;
      }
      
   /**
   *  Constantly listens for input from the server and writes the input to receiver text area
   */
      public void run()
      {
         while(true)
         {
            try
            {
               String input = reader.readLine();
               recvBlock.append(input + "\n");
            }
            catch(Exception e) {
            System.out.println("Reading Exception");}
         }
      }
   }
   
/**
*  Handles Action events of the GUI
*/
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getSource() == exitButton)
      {
         send.println("quit");
         send.flush();
         System.exit(1);
      }
      if(ae.getSource() == sendButton)
      {
         send.println(sendBlock.getText());
         send.flush();         
      }
   }
   
/**
*  Initializes a chat client
*  @param args None
*/
   public static void main(String [] args)
   {
      ChatClient test = new ChatClient();
   }
}