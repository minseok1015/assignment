package org.tukorea.ticketbox.payment;

public class Receipt {
    String client;
    String productName;
    String payMethod;
    String payNumber;
    double subTotalAmount;
    double totalAmount;

    public Receipt(String client,String productName,String payMethod, String payNumber, double subTotalAmount,double totalAmount){
        this.client=client;
        this.productName=productName;
        this.payMethod=payMethod;
        this.payNumber=payNumber;
        this.subTotalAmount=subTotalAmount;
        this.totalAmount=totalAmount;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    @Override
    public String toString(){
        return "---------------------\n" +
                "\tRecipt\n"+
                "---------------------\n" +
                "[ Received by : "+client+"]\n"+
                "[ Product : "+ productName+"]\n"+
                "[ PayMethod : "+payMethod+" ]\n"+
                "[ PayNumber : "+payNumber+" ]\n"+
                "[ SubTotal : "+subTotalAmount+" ]\n"+
                "[ Total : "+totalAmount+" ]\n";
    }
    public String toBackupString(){
        return  client+","+productName+","+payMethod+","+payNumber+","+subTotalAmount+","+Math.round(totalAmount * 10) / 10.0;
    }
}
