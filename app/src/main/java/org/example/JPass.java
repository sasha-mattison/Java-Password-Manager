package org.example;

public class JPass {

    private final static String versionNumber = "0.0.2 ALPHA";

    public static void main(String[] args) {
        Commands commands = new Commands();
        String account;

        if (args.length == 0) {
            returnUsage();
            System.exit(0);
        }

        commands.init();

        switch (args[0]) {
            case "add":
                if (!checkArgs(2, args)) {
                    returnUsage();
                    return;
                }

                account = args[1];

                commands.add(account);
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
            case "list":
                if (!checkArgs(1, args)) {
                    returnUsage();
                    return;
                }

                for (String x : commands.list()) {
                    System.out.println("    " + x);
                }
                break;
            case "newkey":
                if (!checkArgs(1, args))
                    return;

                commands.setNewEncryptionKey();
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
                    + "remove - Removes an account and related password\n"
                    + "list - Lists all accounts saved. Passwords are not listed\n");
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