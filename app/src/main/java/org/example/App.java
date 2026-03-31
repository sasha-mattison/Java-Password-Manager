
package org.example;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        File passwordFile = new File("passwords.psw");
        String passwords;

        try {
            passwordFile.create();
            passwordFile.Ewrite("test", "testkey");
            System.out.println(passwordFile.Eread("testkey"));
            //passwordFile.encrypt("testkey");        
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
