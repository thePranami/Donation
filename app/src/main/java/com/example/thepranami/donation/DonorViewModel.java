package com.example.thepranami.donation;

public class DonorViewModel {
    String id, donorAmount, donorName, donorOther, donorAddress, donorMobile;

    public DonorViewModel(String id, String donorAmount, String donorName, String donorOther, String donorAddress, String donorMobile) {
        this.id = id;
        this.donorAmount = donorAmount;
        this.donorName = donorName;
        this.donorOther = donorOther;
        this.donorAddress = donorAddress;
        this.donorMobile = donorMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDonorAmount() {
        return donorAmount;
    }

    public void setDonorAmount(String donorAmount) {
        this.donorAmount = donorAmount;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorOther() {
        return donorOther;
    }

    public void setDonorOther(String donorOther) {
        this.donorOther = donorOther;
    }

    public String getDonorAddress() {
        return donorAddress;
    }

    public void setDonorAddress(String donorAddress) {
        this.donorAddress = donorAddress;
    }

    public String getDonorMobile() {
        return donorMobile;
    }

    public void setDonorMobile(String donorMobile) {
        this.donorMobile = donorMobile;
    }
}
