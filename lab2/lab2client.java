package lab2;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * Created by Stephen on 9/18/2017.
 */
public class lab2client  {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        String[] theArgs = new String[3];
        String serverAddress = JOptionPane.showInputDialog(
                "Enter IP Address of a machine that is\n" +
                        "running the date service on port 9090:");
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        //String line = null;
        int counter = 0;
        String answer;
        while (counter < 3) {
            // keep appending last line read to buffer
            answer = input.readLine();
            theArgs[counter] = answer;
            counter++;
        }


        //JOptionPane.showMessageDialog(null, answer);
        lab2 veryfyier = new lab2();

        String rhamc = veryfyier.hmacSha1(theArgs[1],theArgs[0]);
       System.out.println(rhamc.equals(theArgs[2]));
        System.exit(0);
    }
}
