package lab2;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by Stephen on 9/18/2017.
 */
public class lab2 {

    /*convert raw btyes to hex*/
    public static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        /*enhanced for loop for looping through every raw byte and change to hex representation*/
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }



    public static String hmacSha1(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        //sign key with hmacsha1
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");

        // create mac instance for sha1
        Mac mac = Mac.getInstance("HmacSHA1");
        //init mac with the private signed key
        mac.init(signingKey);
        //hash message
        return toHexString(mac.doFinal(data.getBytes()));
    }

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {


        String key = "jsfhkucvhwdcnkhc";
        String m1 = JOptionPane.showInputDialog(
                "Enter a mesage to send");
        /*set socket data*/
        InetAddress currentIp = InetAddress.getByName("127.0.0.1");
        ServerSocket listener = new ServerSocket(9090,0,currentIp);
        try {
            while (true) {
                //open socket to send data through
                System.out.println(listener.getInetAddress());
                Socket socket = listener.accept();
                try {
                    //write data through socket

                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(key);
                    out.println(m1);

                    out.println(hmacSha1(m1,key));



                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (SignatureException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } finally {
                    //close socket
                    socket.close();
                }
            }
        }
        finally {
            //close server connection
            listener.close();
        }
    }
}
