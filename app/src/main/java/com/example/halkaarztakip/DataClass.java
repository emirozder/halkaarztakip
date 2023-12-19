package com.example.halkaarztakip;

public class DataClass {

    private String hisseTitle;
    private String hissePrice;
    private String hisseLot;
    private String hisseNot;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getHisseTitle() {
        return hisseTitle;
    }

    public String getHissePrice() {
        return hissePrice;
    }

    public String getHisseLot() {
        return hisseLot;
    }

    public String getHisseNot() {
        return hisseNot;
    }

    public DataClass(String hisseTitle, String hissePrice, String hisseLot, String hisseNot) {
        this.hisseTitle = hisseTitle;
        this.hissePrice = hissePrice;
        this.hisseLot = hisseLot;
        this.hisseNot = hisseNot;
    }

    public DataClass(){

    }
}
