package com.handshake.pritz.organichome;

/**
 * Created by pritz on 12/1/18.
 */

public class AdminGettersetter {
    String Name,Address,Price,HomestayPic,Effi;



    public AdminGettersetter() {
    }

    public AdminGettersetter(String name, String address, String price, String homestayPic, String effi) {
        Name = name;
        Address = address;
        Price = price;
        HomestayPic = homestayPic;
        Effi = effi;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getHomestayPic() {
        return HomestayPic;
    }

    public void setHomestayPic(String homestayPic) {
        HomestayPic = homestayPic;
    }

    public String getEffi() {
        return Effi;
    }

    public void setEffi(String effi) {
        Effi = effi;
    }
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
