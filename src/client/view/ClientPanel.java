/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.conn.Client;
import intf.MessageInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author IDEA Developers
 */
public class ClientPanel extends JFrame implements ActionListener, MessageInterface{
    
    private int width = 800;
    private int height = 600;
    
    private JPanel mainPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    
    private JTextArea outputMessages;
    private JScrollPane scrollMessages;
    private JTextField inputMessage;
    private JButton sendMessageBtn;
    private JButton connectServerBtn;
    
    public ClientPanel(){
        super("Client Panel");
        
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.BLACK);
        
        
        centerPanel = new JPanel(null);
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBounds(0, 0, width, height - 100);
        
        
        bottomPanel = new JPanel(null);
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBounds(0, centerPanel.getHeight(), width, 100);
        
        outputMessages = new JTextArea();
        outputMessages.setBackground(Color.BLACK);
        outputMessages.setForeground(Color.GREEN);
        scrollMessages = new JScrollPane(outputMessages);
        scrollMessages.setBounds(5, 5, centerPanel.getWidth() - 20, centerPanel.getHeight() - 20);
        centerPanel.add(scrollMessages);
        
        inputMessage = new JTextField();
        inputMessage.setBackground(Color.BLACK);
        inputMessage.setForeground(Color.WHITE);
        inputMessage.setBounds(10, 10, 450, 30);
        bottomPanel.add(inputMessage);
        
        sendMessageBtn = new JButton("Send");
        sendMessageBtn.setBackground(Color.GREEN);
        sendMessageBtn.setForeground(Color.WHITE);
        sendMessageBtn.setBounds(inputMessage.getWidth() + 10, 10, 100, 30);
        sendMessageBtn.addActionListener(this);
        bottomPanel.add(sendMessageBtn);
        
        
        connectServerBtn = new JButton("Connect");
        connectServerBtn.setBackground(Color.GREEN);
        connectServerBtn.setForeground(Color.WHITE);
        connectServerBtn.setBounds(bottomPanel.getWidth() - 200, 10, 100, 30);
        connectServerBtn.addActionListener(this);
        bottomPanel.add(connectServerBtn);
        
        mainPanel.add(centerPanel);
        mainPanel.add(bottomPanel);
        
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new ClientPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == connectServerBtn){
            try{
                Client.getInstance().connectToServer(this);
            }catch(Exception ex){
                System.err.println(ex.getMessage());
            }
        }
        
        if(e.getSource() == sendMessageBtn){
            try{
                Client.getInstance().sendMessage(inputMessage.getText());
            }catch(Exception ex){
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public void onMessageReceived(String message) {
        outputMessages.append("Server: " + message + "\n");
    }
}
