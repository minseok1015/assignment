package org.tukorea.atm.main;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.tukorea.atm.bank.ATMachine;

public class AtmMain {
    public static void main(String[] args) {
        ATMachine atm=new ATMachine(1000, 500000, "admin");
        Scanner scan = new Scanner(System.in);
        while(true){
            atm.displayMenu();
            System.out.println(" 메뉴를 선택하세요 >> " );
            try{
                int select = scan.nextInt();
                switch(select) {
                    case 1:
                        atm.createAccount();
                        break;
                    case 2:
                        atm.checkMoney();
                        break;
                    case 3:
                        atm.depositMoney();
                        break;
                    case 4:
                        atm.widrawMoney();
                        break;
                    case 5:
                        atm.transfer();
                        break;
                    case 7:
                        atm.requestSvc();
                        break;
                    case 8:
                        atm.managerMode();
                        break;
                    case 9:
                        atm.exit();
                        scan.close();
                        return;
                }
            }catch (InputMismatchException e){
                System.out.println(" 정확하게 입력해주세요.");
                scan.nextLine();
                continue;
            }
        }

    }
}