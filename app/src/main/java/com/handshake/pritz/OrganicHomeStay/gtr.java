package com.handshake.pritz.OrganicHomeStay;

/**
 * Created by pritz on 20/1/18.
 */

public class gtr {
    private String Name;
    private String Homeaddress;
    private String Price;
    private String HomestayPic;
    public void gtr(String Name,String Homeaddress,String Price,String HomestayPic)
    {
        this.Name=Name;
        this.Homeaddress=Homeaddress;
        this.HomestayPic=HomestayPic;
        this.Price=Price;
    }

    public String getName() {
        return Name;
    }

    public String getHomeaddress() {
        return Homeaddress;
    }

    public String getPrice() {
        return Price;
    }

    public String getHomestayPic() {
        return HomestayPic;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public void setHomeaddress(String Homeaddress)
    {
        this.Homeaddress=Homeaddress;
    }
    public void setHomestayPic(String HomestayPic)
    {
        this.HomestayPic=HomestayPic;
    }
    public void setPrice(String Price)
    {
        this.Price=Price;
    }
}
