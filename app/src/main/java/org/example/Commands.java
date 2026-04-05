package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Commands {

    private static String ENCRYPTION_KEY;
    private File accounts = new File(System.getProperty("user.home") + "/.config/jpass/accounts.txt");
    private File userData = new File(System.getProperty("user.home") + "/.config/jpass/userdata.txt");
    private final Scanner scanner = new Scanner(System.in);

    public Commands() {}

    void init() {
        try {
            //String[] content = userData.read().split(".");
            
            String content = userData.read();
            if  (content == null)
                content = "";
            ENCRYPTION_KEY = content;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void add(User user) {
        try {
            accounts.create();
            System.out.println("Enter Password: ");
            String password = scanner.next().strip();
            accounts.Ewrite(formatAccData(user.getAccount(), password), ENCRYPTION_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String check(User user) {
        try {
            String content = accounts.Eread(ENCRYPTION_KEY);

            if (content == null || !content.contains(user.getAccount()))
                return "Account not found";

            int charOfAccount = content.indexOf(user.getAccount()) + user.getAccount().length() + 1;
            return readUntil(content, charOfAccount, '}');

        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading accounts";
        }
    }

    void remove(User user) {
        try {
            String content = accounts.Eread(ENCRYPTION_KEY);

            if (content == null) {
                System.out.println("No accounts file found.");
                return;
            }

            String search = "{" + user.getAccount() + ",";
            int start = content.indexOf(search);

            if (start == -1) {
                System.out.println("Account not found.");
                return;
            }

            int end = content.indexOf("}", start);

            if (end == -1) {
                System.out.println("Corrupted data.");
                return;
            }

            if (!confirmation())
                return;

            String entry = content.substring(start, end + 1);
            content = content.replace(entry, "");

            accounts.decrypt(ENCRYPTION_KEY);
            accounts.writeAll(content);
            accounts.encrypt(ENCRYPTION_KEY);

            System.out.println("Removed: " + user.getAccount());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String[] list() {
        try {
            String content = accounts.Eread(ENCRYPTION_KEY);
            ArrayList<Integer>indices = new ArrayList<>();

            for (int i = 0; i < content.length(); i++) {
                if(content.charAt(i) == '{') {
                    indices.add(i);
                }
            }

            String[] accountList = new String[indices.size()];

            for (int i = 0; i < indices.size(); i++) {
                accountList[i] = readUntil(content, indices.get(i)+1, ',');
            }

            return accountList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void setNewEncryptionKey() {
        System.out.println("Enter new encryption key: ");
        String newKey = scanner.next().strip();
        
        if (!confirmation())
            return;

        try {
            String content = userData.read();
            content = content.replace(ENCRYPTION_KEY, newKey);
            userData.erase();
            userData.write(content);
            ENCRYPTION_KEY= newKey;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String formatAccData(String account, String password) {
        return "{" + account + "," + password + "}";
    }

    private String readUntil(String str, int start, char stopChar) {
        StringBuilder result = new StringBuilder();

        for (int i = start; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == stopChar) {
                break;
            }

            result.append(c);
        }

        return result.toString();
    }

    private boolean confirmation() {
        System.out.println("Confirm? (y|N): ");
        String reply = scanner.next();
        reply = reply.toLowerCase();

        switch (reply) {
            case "y":
                return true;
            case "yes":
                return true;
            default:
                return false;
        }
    }
}