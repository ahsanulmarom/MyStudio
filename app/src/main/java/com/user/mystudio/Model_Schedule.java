package com.user.mystudio;

public class Model_Schedule {
    private String date;
    private String time;
    private String lokasi;
    private String pemesan;
    private String status;

    public Model_Schedule(String date, String time, String lokasi, String pemesan, String status) {
        this.date = date;
        this.time = time;
        this.lokasi = lokasi;
        this.pemesan = pemesan;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    void setTime(String time) {
        this.time = time;
    }

    public String getLokasi() {
        return lokasi;
    }

    void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getPemesan() {
        return pemesan;
    }

    void setPemesan(String pemesan) {
        this.pemesan = pemesan;
    }

    public String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }
}
