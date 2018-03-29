package com.example.red.magazine.topsellers.model;

/**
 * Created by Meseret on 1/13/2018.
 */

public class PaymentSupporterBanks {
    private String bank_name;
    private String logo;
    private String payment_link;

    public PaymentSupporterBanks(String bank_name, String logo, String payment_link) {
        this.bank_name = bank_name;
        this.logo = logo;
        this.payment_link = payment_link;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPayment_link() {
        return payment_link;
    }

    public void setPayment_link(String payment_link) {
        this.payment_link = payment_link;
    }
}
