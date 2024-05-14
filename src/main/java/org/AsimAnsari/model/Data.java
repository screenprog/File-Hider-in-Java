package org.AsimAnsari.model;
public record Data(int id, String fileName, String path, String email) {

    public Data(int id, String fileName, String path) {
        this(id, fileName, path, null);
    }

    public Data(String fileName, String path, String email) {
        this(0, fileName, path, email);
    }
}
