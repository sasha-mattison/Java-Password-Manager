package org.example;

import java.io.IOException;

public class Commands {

    private File accounts = new File("accounts.txt");

    public Commands() {}

    public void add(String account, String password) {
        try {


            accounts.create();
            accounts.Ewrite(formatAccData(account, password), "");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public String check(String account) {
        try {
            String content = accounts.Eread("");

            if (!content.contains(account))
                return null;

            int charOfAccount = content.indexOf(account) +  account.length() + 1;
            content = readUntil(content, charOfAccount, '}');
            return content;
        

        } 
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String formatAccData(String account, String password) {
        return "{" + account + ","+ password + "}";
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

    
}
