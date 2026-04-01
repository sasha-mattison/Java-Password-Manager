package org.example;

public class JPass {

    private final static String versionNumber = "0.0.1 ALPHA";

    public static void main(String[] args) {
        Commands commands = new Commands();
        String account, password;

        if (args.length == 0) {
            returnUsage();
            System.exit(0);
        }

        switch (args[0]) {
            case "add":
                if (!checkArgs(3, args)) {
                    returnUsage();
                    return;
                }

                account = args[1];
                password = args[2];

                commands.add(account, password);
                break;
            case "check":
                if (!checkArgs(2, args)) {
                    returnUsage();
                    return;
                }
                account = args[1];
                System.out.println(commands.check(account));
                break;
            case "remove":
                if (!checkArgs(2, args)) {
                    returnUsage();
                    return;
                }
                account = args[1];
                commands.remove(account);
                break;
            case "help":
                if (!checkArgs(1, args)) {
                    returnUsage();
                    return;
                }
                System.out.println(
                    "JPass Version: " + versionNumber
                    + "\n\nCommand List:\n"
                    + "add - Saves an account and password\n"
                    + "check - Checks for a password linked to an account and returns it\n"
                    + "remove - Removes an account and related password\n");
                break;
            default:
                returnUsage();
        }
    }

    private static void returnUsage() {
        System.out.println("Usage: jpass <command> <account> <password>\n" + "Run: 'jpass help' for more info");
    }

    private static boolean checkArgs(int argsNum, String[] args) {
        if (args.length != argsNum) {
            returnUsage();
            return false;
        }
        return true;
    }
}