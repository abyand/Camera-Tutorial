package com.example.abyandafa.kameraku;

/**
 * Created by Abyan Dafa on 11/10/2017.
 */

public class Foto {
    String nama, path;

    public Foto(String nama, String path) {
        this.nama = nama;
        this.path = path;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
