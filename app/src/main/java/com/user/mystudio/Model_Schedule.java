package com.user.mystudio;

class Model_Schedule {
    private String date;
    private String time;
    private String lokasi;
    private String pemesan;
    private String status;

    Model_Schedule(String date, String time, String lokasi, String pemesan, String status) {
        this.date = date;
        this.time = time;
        this.lokasi = lokasi;
        this.pemesan = pemesan;
        this.status = status;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    String getTime() {
        return time;
    }

    void setTime(String time) {
        this.time = time;
    }

    String getLokasi() {
        return lokasi;
    }

    void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    String getPemesan() {
        return pemesan;
    }

    void setPemesan(String pemesan) {
        this.pemesan = pemesan;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }
}
