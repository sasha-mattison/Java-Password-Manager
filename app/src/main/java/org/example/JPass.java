
package org.example;

import java.io.IOException;

public class JPass {

    public static void main(String[] args) {
        File passwordFile = new File("passwords.txt");
        Commands commands = new Commands();
        String account, password;
     

        switch(args[0]) {
            case "add":
                if (args.length < 3) {
                    returnUsage();
                    return;
                }

                account = args[1];
                password = args[2];

                commands.add(account, password);
                account = null;
                password = null;
                break;
            case "check":
                if (args.length < 2) {
                    returnUsage();
                    return;
                }
                account = args[1];
                System.out.println(commands.check(account));
                account = null;
                break;
        }
        

        // try {
        //     passwordFile.create();
        //     passwordFile.Ewrite("test", "testkey");
        //     System.out.println(passwordFile.Eread("testkey"));
        //     passwordFile.encrypt("testkey");        
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

    }

    private static void returnUsage() {
        System.out.println("Usage: jpass add <account> <password>");
    }
}
