import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class ChatClient extends JFrame //implements ActionListener
{
   private BufferedReader receive;
   private PrintWriter send;
   private JTextArea recvMsg;
   private JTextArea sendMsg;
   private JButton sendButton;
   private JButton exitButton;
   
   public ChatClient()
   {
      createGUI();
      startReceiver();
   }
   
   public void createGUI()
   {
      this.setLayout(new BorderLayout());
      
      sendMsg = new JTextArea("Send text",10,50);
      recvMsg = new JTextArea("Recv Text",10,50);
      JPanel textAreas = new JPanel();
      textAreas.setLayout(new GridLayout(0,1));
      textAreas.add(sendMsg);
      textAreas.add(recvMsg);
      this.add(textAreas, BorderLayout.CENTER);
      
      JPanel buttons = new JPanel();
      buttons.setLayout(new GridLayout(1,2));
      
      sendButton = new JButton("Send");
      exitButton = new JButton("Exit");
      buttons.add(sendButton);
      buttons.add(exitButton);
      //sendButton.addActionListener(this);
      //exitButton.addActionListener(this);
      this.add(buttons, BorderLayout.SOUTH);
      
      this.setTitle("Chat screen");
      this.setSize(600,400);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
      
      //Receiver incomingMsgs = new Receiver();
   }
   
   public void startReceiver()
   {
      try
      {
         Socket s = new Socket("localhost", 16789);
         BufferedReader receive = new BufferedReader(new InputStreamReader(s.getInputStream()));
         PrintWriter send = new PrintWriter(s.getOutputStream());
         Receiver recv = new Receiver(receive);
         recv.start();
      }
      catch (Exception e)
      {
         System.out.println("startReceiver Exception");
      }
   }
   
   public class Receiver extends Thread
   {
      private BufferedReader reader;
      public Receiver(BufferedReader _reader)
      {
         this.reader = _reader;
      }
      public void run()
      {
         while(true)
         {
            try
            {
               System.out.println("Entered Run Loop");
               String input = reader.readLine();
               System.out.println(input);
            }
            catch(Exception e) {
            System.out.println("Reading Exception");}
         }
      }
   }
   
   // public void actionPerformed(ActionEvent ae)
//    {
//       try
//       {
//          Socket s = new Socket("localhost", 16789);
//          BufferedReader receive = new BufferedReader(new InputStreamReader(s.getInputStream()));
//          PrintWriter send = new PrintWriter(s.getOutputStream());
//       
//          //new Receiver(receive).start();
//          if(ae.getSource() == exitButton)
//          {
//             System.exit(1);
//          }
//          if(ae.getSource() == sendButton)
//          {
//             send.println(sendMsg.getText());
//             send.flush();
//             //recvMsg.setText(receive.readLine());           
//          }
//       }
//       catch(UnknownHostException uhe) {
// 			recvMsg.setText("Unable to connect to host.");
// 			return;
// 		}
// 		catch(IOException ie) {
// 			recvMsg.setText("IOException communicating with host.");
// 			return;
// 	   }
//       catch (Exception e)
//       {
//       }
//    }
   
   public static void main(String [] args)
   {
      ChatClient test = new ChatClient();
   }
}