import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class ChatClient extends JFrame implements ActionListener
{
   private BufferedReader receive;
   private PrintWriter send;
   private JTextArea recvBlock;
   private JTextArea sendBlock;
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
               String input = reader.readLine();
               recvBlock.append(input + "\n");
            }
            catch(Exception e) {
            System.out.println("Reading Exception");}
         }
      }
   }
   
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
   
   public static void main(String [] args)
   {
      ChatClient test = new ChatClient();
   }
}