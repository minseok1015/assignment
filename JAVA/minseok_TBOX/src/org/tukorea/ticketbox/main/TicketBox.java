package org.tukorea.ticketbox.main;

import org.tukorea.ticketbox.cinema.AnimationScreen;
import org.tukorea.ticketbox.cinema.FamilyScreen;
import org.tukorea.ticketbox.cinema.PremiumScreen;
import org.tukorea.ticketbox.cinema.Screen;
import org.tukorea.ticketbox.util.Statistics;
import org.tukorea.ticketbox.util.ReceiptWriter;


import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketBox {
    private FamilyScreen familyScreen;
    private AnimationScreen animationScreen;
    private PremiumScreen premiumScreen;
    public static final String ADMIN_PASSWORD = "admin";
    Scanner scan=new Scanner(System.in);

    public void initScreen(){
        //Screen(영화제목,영화 줄거리, 티켓가격, 좌석(rowMax),좌석(ColMax)
        familyScreen=new FamilyScreen("7번방의 선물","최악의 흉악범들이 모인 교도소 7번방에 이상한 놈이 들어왔다! 그는 바로 6살 지능의 딸바보 '용구'! ", 12000, 10,10);
        animationScreen=new AnimationScreen("원피스 : 필름레드", "오직 목소리 하나로 전 세계를 사로잡은 디바 ‘우타’. 그녀가 모습을 드러내는 첫 라이브 콘서트가 음악의 섬 ‘엘레지아’", 20000, 10,10);
        premiumScreen=new PremiumScreen("트와일라잇","아버지와 살게 된 벨라 스완은 전학간 학교에서 신비한 분위기의 에드워드를 만나게 된다.", 50000, 5, 5);

    }
    public Screen selectScreen(){
        System.out.println("--------------------");
        System.out.println("-  상영관 선택 메인메뉴   -");
        System.out.println("--------------------");
        System.out.println("1. 가족 영화 1관");
        System.out.println("2. 애니메이션 고화질 영화 2관");
        System.out.println("3. 프리미엄 고화질 영화 3관(맥주, 피자 등 음식 제공)");
        System.out.println("9. 관리자 메뉴");
        System.out.println("   선택(1~3, 9)외 종료");
        System.out.println(" ");
        System.out.print("상영관 선택: ");
        try {
            int select= scan.nextInt();
            switch (select){
                case 1:
                    return familyScreen;
                case 2:
                    return animationScreen;
                case 3:
                    return premiumScreen;
                case 9:
                    managerMode();
                    return null;
                default:
                    System.out.print("올바르지 않은 선택으로 종료됩니다");
                    scan.nextLine();
                    return null;
            }
        } catch (InputMismatchException e) {
            System.out.println(" 정확하게 입력해주세요.");
            scan.nextLine();
            return null;
        }

    }
    void managerMode(){
        Screen scnFamily = familyScreen;
        Screen scnAni = animationScreen;
        Screen scnPremium = premiumScreen;
        ReceiptWriter receiptWriter=new ReceiptWriter();
        int ticketSum=scnFamily.getHash().size() + scnAni.getHash().size() + scnPremium.getHash().size();
        System.out.print("암호 입력 : ");
        String passWord=scan.next();
        if(passWord.equals(ADMIN_PASSWORD)){
            System.out.println("--------------------------");
            System.out.println("----     관리자 기능    ----");
            System.out.println("--------------------------");

            System.out.println("영화관 총 티켓 판매량 : "+ ticketSum);
            System.out.println("가족 영화관 결제 총액 : " + Statistics.sum(scnFamily.getHash()));
            System.out.println("애니메이션 영화관 결제 총액 : " + Statistics.sum(scnAni.getHash()));
            System.out.println("프리미엄 영화관 결제 총액 : " + Statistics.sum(scnPremium.getHash()));
            System.out.println("\n");
            System.out.println("가족 영화관 영수증 전체 출력");
            receiptWriter.printConsole(scnFamily.getHash());
            System.out.println("애니메이션 영화관 영수증 전체 출력");
            receiptWriter.printConsole(scnAni.getHash());
            System.out.println("프리미엄 영화관 영수증 전체 출력");
            receiptWriter.printConsole(scnPremium.getHash());
            System.out.println(" ");
        }
        else{
            System.out.println("비밀번호가 틀립니다.");
            scan.nextLine();
        }
    }
}
