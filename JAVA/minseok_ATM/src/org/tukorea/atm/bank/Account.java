package org.tukorea.atm.bank;

public class Account {
    private int accountId;      //계좌 번호
    private int balance;        //계좌 잔고
    private String accountName; //고객명
    private String password;       //계좌 비밀번호

    public Account(int id, int money, String name, String pwd){
        this.accountId=id;
        this.balance=money;
        this.accountName=name;
        this.password=pwd;
    }
    boolean authenticate(int id, String passwd){
        if(id==this.accountId&& passwd.equals(this.password)){
            return true;
        }
        return false;
    }
    public int getAccountId(){
        return accountId;
    }
    public int getBalance(){
        return balance;
    }
    int deposit(int money){
        this.balance=this.balance+money;
        return this.balance;
    }
    int widraw(int money){
        if(this.balance<money){
            System.out.println("돈이 부족합니다.");
        }
        else{
            this.balance=this.balance-money;
            return this.balance;
        }
        return this.balance;
    }
    public boolean updatePasswd(String oldPasswd, String newPasswd){
        if(authenticate(getAccountId(),oldPasswd)) {
            if (oldPasswd.equals(newPasswd)) {
                System.out.println("기존 비밀번호와 신규 비밀번호가 같습니다");
                return false;
            } else {
                this.password = newPasswd;
                return true;
            }
        }
        return false;
    }
    public String getAccountName(){
        return accountName;
    }
}
