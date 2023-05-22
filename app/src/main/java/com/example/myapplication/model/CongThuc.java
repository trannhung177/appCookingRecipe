package com.example.myapplication.model;

public class CongThuc {
    private int ID;
    private String TenCT;
    private String NoiDung;
    public byte[] Anh;
    private int Solxem;

    public CongThuc(int ID, String tenCT, String noiDung, byte[] anh,int solxem ) {
        this.ID = ID;
        TenCT = tenCT;
        NoiDung = noiDung;
        Anh = anh;
        Solxem=solxem;
    }

    public CongThuc(String tenCT, String noiDung, byte[] anh, int solxem) {
        TenCT = tenCT;
        NoiDung = noiDung;
        Anh = anh;
        Solxem=solxem;
    }

    public CongThuc(int ID, String tenCT, String noiDung, byte[] anh) {
        this.ID = ID;
        TenCT = tenCT;
        NoiDung = noiDung;
        Anh = anh;
    }

    public CongThuc(String tenCT, String noiDung, byte[] anh) {
        TenCT = tenCT;
        NoiDung = noiDung;
        Anh = anh;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenCT() {
        return TenCT;
    }

    public void setTenCT(String tenCT) {
        TenCT = tenCT;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public byte[] getAnh() {
        return Anh;
    }

    public void setAnh(byte[] anh) {
        Anh = anh;
    }

    public int getSolxem() {
        return Solxem;
    }

    public void setSolxem(int solxem) {
        Solxem = solxem;
    }
}
