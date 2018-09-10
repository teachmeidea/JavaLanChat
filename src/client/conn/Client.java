/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.conn;

import intf.MessageInterface;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author IDEA Developers
 */
public class Client {
    private static Client instance;
    private Socket socketConn;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    
    private MessageInterface mi;
    
    public static Client getInstance(){
        if(instance == null){
            instance = new Client();
        }
        return instance;
    }
    
    public void connectToServer(MessageInterface mi) throws Exception{
        this.mi = mi;
        System.out.println("Connection to server...");
        socketConn = new Socket("localhost", 3535);
        isr = new InputStreamReader(socketConn.getInputStream());
        osw = new OutputStreamWriter(socketConn.getOutputStream());
        System.out.println("Conneted to server");
        listenForMessages();
    }
    
    public void listenForMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        char[] charMessage = new char[1024];
                        if(isr.read(charMessage, 0, charMessage.length) != -1){
                            String message = new String(charMessage);
                            mi.onMessageReceived(message);
                            System.out.println(message);
                        }
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        }).start();
    }
    
    public void sendMessage(String message)throws Exception{
        osw.write(message);
        osw.flush();
    }
}
