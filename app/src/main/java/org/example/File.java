package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {

    private String filePath;
    private Path path;

    public File(String filePath) {
        this.filePath = filePath;
        this.path = Paths.get(filePath);
    }

    void create() throws IOException {
        if (checkFile())
            return;

        Files.createFile(this.path);
    }

    void write(String string) throws IOException {
        if (!checkFile())
            return;

        String content = read();
        content = content.concat(string);
        Files.write(this.path, content.getBytes(StandardCharsets.UTF_8));
    }

    void Ewrite(String string, String password) throws IOException {
        if (!checkFile())
            return;

        decrypt(password);
        String content = read();
        content = content.concat(string);
        Files.write(this.path, content.getBytes(StandardCharsets.UTF_8));
        encrypt(password);
    }

    String read() throws IOException {
        if (!checkFile())
            return null;

        return Files.readString(this.path);
    }

    void remove(String string) throws IOException {
        if (!checkFile())
            return;

        String content = Files.readString(this.path);
        content = content.replace(string, "");
        Files.write(this.path, content.getBytes(StandardCharsets.UTF_8));
    }

    void replace(String oldString, String newString) throws IOException {
        if (!checkFile())
            return;

        String content = Files.readString(this.path);
        content = content.replace(oldString, newString);
        Files.write(this.path, content.getBytes(StandardCharsets.UTF_8));
    }

    void writeAll(String content) throws IOException {
        Files.write(this.path, content.getBytes(StandardCharsets.UTF_8));
    }

    String Eread(String password) throws IOException {
        if (!checkFile())
            return null;

        decrypt(password);
        String content = Files.readString(this.path);
        encrypt(password);
        return content;
    }

    void erase() throws IOException {
        Files.write(this.path, "".getBytes(StandardCharsets.UTF_8));
    }

    private void delete() throws IOException {
        if (!checkFile())
            return;

        Files.delete(this.path);
    }

    void encrypt(String encryptionKey) throws IOException {
        if (!checkFile()) return;

        int totalShift = 0;
        for (char c : encryptionKey.toCharArray()) {
            totalShift += c;
        }
        totalShift %= 128;

        char[] contentArray = Files.readString(this.path).toCharArray();

        for (int i = 0; i < contentArray.length; i++) {
            contentArray[i] += totalShift;
        }

        Files.writeString(this.path, String.valueOf(contentArray));
    }

    void decrypt(String encryptionKey) throws IOException {
        if (!checkFile()) return;

        int totalShift = 0;
        for (char c : encryptionKey.toCharArray()) {
            totalShift += c;
        }
        totalShift %= 128;

        char[] contentArray = Files.readString(this.path).toCharArray();

        for (int i = 0; i < contentArray.length; i++) {
            contentArray[i] -= totalShift;
        }

        Files.writeString(this.path, String.valueOf(contentArray));
    }

    private boolean checkFile() {
        return Files.exists(this.path);
    }

    String getFilePath() {
        return filePath;
    }

    Path getPath() {
        return this.path;
    }
}