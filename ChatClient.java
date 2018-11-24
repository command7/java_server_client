import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class ChatClient extends JFrame
{
   private JTextArea recvMsg;
   private JTextArea sendMsg;
   private JButton sendButton;
   private JButton exitButton;
   
   public ChatClient()
   {
      createGUI();
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
      this.add(buttons, BorderLayout.SOUTH);
      
      this.setTitle("Chat screen");
      this.setSize(600,400);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
   }
   
   public static void main(String [] args)
   {
      ChatClient test = new ChatClient();
   }
}