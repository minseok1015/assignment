package org.tukorea.atm.bank;

import org.tukorea.atm.helpdesk.CustomerSvc;
import org.tukorea.atm.util.Statistics;

import java.util.Scanner;
public class ATMachine {
    Scanner scanner=new Scanner(System.in);
    private Account[] accountArray;
    private int machineBalance;
    private int maxAccountNum;
    private int currentAccountNum=0;
    private String managerPassword;
    public static final int BASE_ACCOUNT_ID = 100;


    public ATMachine(int size, int balance, String password){
        this.maxAccountNum=size;
        this.machineBalance=balance;
        this.managerPassword=password;
        accountArray=new Account[size];
    }
    public void createAccount(){
        System.out.println("----------개설-----------");
        System.out.print("이름 입력: ");
        String name= scanner.next();
        System.out.print("암호 입력: ");
        String password=scanner.next();
        int id=BASE_ACCOUNT_ID + (int)(Math.random()*maxAccountNum*2);
        accountArray[currentAccountNum++]=new Account(id,0,name,password);
        //계정확인
        System.out.println(name+"님 "+id+"번 계좌번호가 정상적으로 개설되었습니다. 감사합니다.");
    }
    public void checkMoney(){
        System.out.println("----------조회-----------");
        System.out.print("계좌번호 입력: ");
        int id=scanner.nextInt();
        System.out.print("비밀번호 입력: ");
        String pwd= scanner.next();
        for(int i=0;i<currentAccountNum;i++){
            if(accountArray[i].authenticate(id,pwd)==true){    //계좌번호랑 비밀번호 같은지 확인
                System.out.println("계좌 잔액: "+accountArray[i].getBalance());
            }
        }
    }
    public void depositMoney(){
        boolean find =false;
        System.out.println("----------입금-----------");
        System.out.print("계좌번호 입력: ");
        int id=scanner.nextInt();
        System.out.print("비밀번호 입력: ");
        String pwd=scanner.next();
        System.out.print("입금액 입력: ");
        int money=scanner.nextInt();
        for(int i=0;i<currentAccountNum;i++) {
            if (accountArray[i].authenticate(id, pwd) == true) {    //계좌번호랑 비밀번호 같은지 확인
                System.out.println("입금 후 잔액: " + accountArray[i].deposit(money));
                machineBalance+=money;
                find=true;
                return;
            }
        }
        if(find==false){
            System.out.println("일치하는 계좌가 없습니다.");
        }
    }
    public void widrawMoney(){
        boolean find =false;
        System.out.println("----------출금-----------");
        System.out.print("계좌번호 입력: ");
        int id=scanner.nextInt();
        System.out.print("비밀번호 입력: ");
        String pwd=scanner.next();
        System.out.print("출금액 입력: ");
        int money=scanner.nextInt();
        for(int i=0;i<currentAccountNum;i++) {
            if (accountArray[i].authenticate(id, pwd) == true) {    //계좌번호랑 비밀번호 같은지 확인
                System.out.println("출금 후 잔액: " + accountArray[i].widraw(money));
                machineBalance-=money;
                find=true;
                return;
            }
        }
        if(find==false){
            System.out.println("일치하는 계좌가 없습니다.");
        }
    }
    public void transfer(){
        boolean findF=false,findT=false;
        System.out.println("----------이체-----------");
        int i,to=0,from=0;
        System.out.print("계좌번호 입력: ");
        int formId=scanner.nextInt();
        System.out.print("비밀번호 입력: ");
        String pwd=scanner.next();
        System.out.print("이체계좌 입력: ");
        int toId=scanner.nextInt();
        System.out.print("이체금액 입력: ");
        int money=scanner.nextInt();
        System.out.println("이체계좌를 다시 확인하세요.");
        for(i=0;i<currentAccountNum;i++) {
            if (accountArray[i].authenticate(formId, pwd) == true) {    //계좌번호랑 비밀번호 같은지 확인
                from=i;
                findT=true;
            }
            else if(accountArray[i].getAccountId() == toId){
                to=i;
                findF=true;
            }
        }
        if(findT&&findF){
            accountArray[to].deposit(money);
            System.out.println("현재 잔액: "+accountArray[from].widraw(money));
        }
        else{
            System.out.println("일치하는 계좌가 없습니다.");
        }


    }
    public void requestSvc (){
        CustomerSvc customerSvc = new CustomerSvc(accountArray,currentAccountNum);
        customerSvc.updatePasswdReq();
    }
    public void managerMode(){
        Account sortAccount[];
        System.out.println("----------고객관리-----------");
        System.out.print("관리자 비밀번호 입력: ");
        String adminPwd=scanner.next();
        if(adminPwd.equals(managerPassword))
        {
            System.out.println("ATM 현금 잔고:    "+machineBalance);
            System.out.println("고객 잔고 총액:    "+Statistics.sum(accountArray,currentAccountNum)+"("+currentAccountNum+"명)");
            System.out.println("고객 잔고 평균:    "+Statistics.average(accountArray,currentAccountNum));
            System.out.println("고객 잔고 최고:    "+Statistics.max(accountArray,currentAccountNum));
            sortAccount = Statistics.sort(accountArray,currentAccountNum);
            System.out.println("고객 계좌 현황(고객 잔고 내림 차순 정렬)");
            for(int i=0;i<currentAccountNum;i++){
                System.out.println(sortAccount[i].getAccountName()+"   "+sortAccount[i].getAccountId()+"   "+sortAccount[i].getBalance());
            }
        }
        else{
            System.out.println("관리자 비밀번호가 틀립니다.");
            return;
            }

        }

    public void displayMenu(){
        System.out.println("----------------------");
        System.out.println("-    TUKOREA BANK     -");
        System.out.println("----------------------");
        System.out.println("1.계좌 개설");
        System.out.println("2.계좌 조회");
        System.out.println("3.계좌 입금");
        System.out.println("4.계좌 출금");
        System.out.println("5.계좌 이체");
        System.out.println("7.고객 센터");
        System.out.println("8.고객 관리");
        System.out.println("9.업무 종료");

    }
    public void exit(){
        System.out.println("안녕히가세요!");
    }
}
