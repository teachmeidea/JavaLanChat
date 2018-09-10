/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.conn;

import intf.MessageInterface;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author IDEA Developers
 */
public class Server {
    
    public static Server instance;
    private ServerSocket serverSock;
    private Socket socket;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    
    private MessageInterface mi;
    
    //singletone pattern
    public static Server getInstance(){
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }
    
    public void startServer(MessageInterface mi) throws Exception{
        this.mi = mi;
        serverSock = new ServerSocket(3535);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        System.out.println("Server online...");
                        socket = serverSock.accept();
                        isr = new InputStreamReader(socket.getInputStream());
                        osw = new OutputStreamWriter(socket.getOutputStream());
                        System.out.println("Client Server Connection OK");
                        listenForMessages();
                        break;
                    }catch (Exception e){
                        System.err.println("Server Listening Error!");
                    }
                }
            }
        }).start();
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
