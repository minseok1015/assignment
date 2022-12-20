package org.tukorea.ticketbox.cinema;

import org.tukorea.ticketbox.payment.*;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Screen {
    Scanner scanner=new Scanner(System.in);
    protected int ticketPrice;
    protected int rowMax;
    protected int colMax;
    protected String movieName;
    protected String movieStory;
    protected MovieTicket[][] seatArray;
    public abstract void showMovieinfo();
    Screen(String name,String story, int price, int row,int col){
        this.movieName=name;
        this.movieStory=story;
        this.ticketPrice=price;
        this.rowMax=row;
        this.colMax=col;
        seatArray=new MovieTicket[row][col] ;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                seatArray[i][j]=new MovieTicket(i,j);
            }
        }

    }
    public void showScreenMenu(){
        System.out.println("--------------------");
        System.out.println("영화 메뉴 - "+ movieName);
        System.out.println("--------------------");
        System.out.println("1. 상영 영화 정보");
        System.out.println("2. 좌석 예약 현황");
        System.out.println("3. 좌석 예약 하기");
        System.out.println("4. 예약 취소 하기");
        System.out.println("5. 좌석 결제 하기");
        System.out.println("6. 티켓 출력 하기");
        System.out.println("7. 메인 메뉴 이동");

    }
    public void showSeatMap(){
        System.out.println("\t\t\t[좌석 예약 현황]");
        System.out.print("\t\t\t");
        for(int i=0;i<rowMax;i++){System.out.print("["+(i+1)+"] ");};
        System.out.println();
        for(int i=0;i<rowMax;i++){
            System.out.print("["+(i+1)+"]");
            System.out.print("\t\t\t");
            for(int j=0;j<colMax;j++){
                System.out.print("["+seatArray[i][j].getStatus()+"] ");
            }
            System.out.println();
        }

    }
    public void reserveTicket(){
        int inputRow;
        int inputCol;

        try{
            System.out.println("[ 좌석 예약 ]");
            System.out.print("좌석 행 번호 입력(1 - "+rowMax+") : ");
            inputRow= scanner.nextInt();
            System.out.print("좌석 열 번호 입력(1 - "+colMax+") : ");
            inputCol= scanner.nextInt();
            int resId=(int)((Math.random()*rowMax*colMax*4)+100);
            seatArray[inputRow-1][inputCol-1].setSeat(inputRow,inputCol);
            seatArray[inputRow-1][inputCol-1].setReservedId(resId);
            System.out.println("행["+inputRow+"] 열["+inputCol+"] "+resId+" 예약 번호로 접수되었습니다.");

        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("올바른 숫자를 입력해주세요.");
            scanner.nextLine();
        }

    }
    private MovieTicket checkReservedId(int id){
        boolean check=false;
        int tmpRow=0,tmpCol=0;
        for(int i=0;i<rowMax;i++){
            for(int j=0;j<colMax;j++){
                if(id==seatArray[i][j].getReservedId()){
                    check=true;
                    tmpRow=i;
                    tmpCol=j;
                }
            }
        }
        if(check==true){
            return seatArray[tmpRow][tmpCol];
        }
        else{
            return null;
        }
    }
    public void cancelReservation(){
        System.out.println("[ 좌석 예약 취소 ]");
        System.out.print("좌석 예약 번호 입력 : ");
        int inputRes=scanner.nextInt();
        MovieTicket check=checkReservedId(inputRes);
        if(check==null){
            System.out.println("예약 번호가 잘못되었습니다.");
            scanner.nextLine();
        }
        else{
            check.setStatus(MovieTicket.SEAT_EMPTYMARK);
        }

    }
    private HashMap<Integer, Receipt> receiptMap=new HashMap<Integer,Receipt>();

    public void payment(){
        System.out.println("[ 좌석 예약 결제 ]");
        System.out.print("예약 번호 입력 : ");
        int inputRes=scanner.nextInt();
        MovieTicket check=checkReservedId(inputRes);
        if(check==null){
            System.out.println("예약 번호가 잘못되었습니다.");
            scanner.nextLine();
        }
        else{
            System.out.println("----------------------");
            System.out.println("     결재 방식 선택      ");
            System.out.println("----------------------");
            System.out.println("1. 은행 이체");
            System.out.println("2. 카드 이체");
            System.out.println("3. 모바일 이체");
            System.out.print("결재 방식 입력(1~3) : ");
            int payType =scanner.nextInt();
            String name=null;
            String number=null;
            Receipt receipt;

            if(payType==Pay.BANK_TRANSFER_PAYMENT){
                System.out.println("[ 은행 이체 ]");
                System.out.print("이름 입력 : ");
                name=scanner.next();
                System.out.print("은행 번호 입력(8자리수) : ");
                number=scanner.next();
                if(number.length()!=8){
                    System.out.println("8자리 수가 아닙니다.");
                    scanner.nextLine();
                    return;
                }
                receipt=new BankTransfer().charge(this.movieName,this.ticketPrice,name,number);
                receiptMap.put(inputRes,receipt);
                System.out.println("은행 결제가 완료되었습니다. : "+ Math.round(receipt.getTotalAmount() * 10) / 10.0);
                check.setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
            }
            else if(payType==Pay.CREDIT_CARD_PAYMENT){
                System.out.println("[ 카드 결제 ]");
                System.out.print("이름 입력 : ");
                name=scanner.next();
                System.out.print("카드 번호 입력(8자리수) : ");
                number=scanner.next();
                if(number.length()!=8){
                    System.out.println("8자리 수가 아닙니다.");
                    scanner.nextLine();
                    return;
                }
                receipt=new CardPay().charge(this.movieName,this.ticketPrice,name,number);
                receiptMap.put(inputRes,receipt);
                System.out.println("카드 결제가 완료되었습니다. : "+ Math.round(receipt.getTotalAmount() * 10) / 10.0);
                check.setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
            }
            else if(payType==Pay.MOBILE_PHONE_PAYMENT){
                System.out.println("[ 모바일 결제 ]");
                System.out.print("이름 입력 : ");
                name=scanner.next();
                System.out.print("핸드폰 번호 입력(11자리수) : ");
                number=scanner.next();
                if(number.length()!=11){
                    System.out.println("11자리 수가 아닙니다.");
                    scanner.nextLine();
                    return;
                }
                receipt=new MobilePay().charge(this.movieName,this.ticketPrice,name,number);
                receiptMap.put(inputRes,receipt);
                System.out.println("모바일 결제가 완료되었습니다. : "+ Math.round(receipt.getTotalAmount() * 10) / 10.0);
                check.setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
            }
            else{
                System.out.println("올바른 숫자를 입력해주세요.");
                scanner.nextLine();
                return;
            }

        }
    }
    public void printTicket(){
        System.out.println("[ 좌석 예약 결제 ]");
        System.out.print("예약 번호 입력 : ");
        int inputRes=scanner.nextInt();
        MovieTicket check=checkReservedId(inputRes);
        if(check==null) {
            System.out.println("예약 번호가 잘못되었습니다.");
            scanner.nextLine();
        }else{
            System.out.print(receiptMap.get(check.getReservedId()));
        }
    }
    public HashMap<Integer,Receipt> getHash(){
        return receiptMap;
    }
}
