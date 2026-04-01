package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Commands {

    private static final String ENCRYPTION_KEY = "jpass_key";
    private File accounts = new File("accounts.txt");

    public Commands() {}

    public void add(String account, String password) {
        try {
            accounts.create();
            accounts.Ewrite(formatAccData(account, password), ENCRYPTION_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String check(String account) {
        try {
            String content = accounts.Eread(ENCRYPTION_KEY);

            if (content == null || !content.contains(account))
                return "Account not found.";

            int charOfAccount = content.indexOf(account) + account.length() + 1;
            return readUntil(content, charOfAccount, '}');

        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading accounts.";
        }
    }

    public void remove(String account) {
        try {
            String content = accounts.Eread(ENCRYPTION_KEY);

            if (content == null) {
                System.out.println("No accounts file found.");
                return;
            }

            String search = "{" + account + ",";
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

            System.out.println("Removed: " + account);

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
        Scanner scanner = new Scanner(System.in);
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