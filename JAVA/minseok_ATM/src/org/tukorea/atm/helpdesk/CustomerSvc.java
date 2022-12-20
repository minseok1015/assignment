package org.tukorea.atm.helpdesk;
import org.tukorea.atm.bank.Account;
import java.util.Scanner;

public class CustomerSvc {
    private Account acctArray[];
    private int currentAcctNum;
    Scanner scanner=new Scanner(System.in);
    public CustomerSvc(Account [] acctArray, int currentAcctNum){
        this.acctArray=acctArray;
        this.currentAcctNum=currentAcctNum;
    }

    public void updatePasswdReq(){
        System.out.println("----------암호변경-----------");
        System.out.print("계좌번호 입력: ");
        int id=scanner.nextInt();
        System.out.print("기존 비밀번호 입력: ");
        String oldpw=scanner.next();
        System.out.print("신규 비밀번호 입력: ");
        String newpw=scanner.next();
        for(int i=0;i<currentAcctNum;i++) {
            if(acctArray[i].getAccountId()==id){        //id가 일치하는지 확인
                if(acctArray[i].updatePasswd(oldpw,newpw)){
                    System.out.println("비밀번호를 수정하였습니다.");
                    return;
                }
            }
        }
        System.out.println("일치하는 계좌와 비밀번호가 없습니다.");
    }
}