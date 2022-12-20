import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

public class Calculate extends JFrame implements Runnable{
    public void run(){
        while(true){
            Calendar cal = Calendar.getInstance();
            String now =
                    cal.get(Calendar.HOUR_OF_DAY)+"시"+
                    cal.get(Calendar.MINUTE)+"분"+
                    cal.get(Calendar.SECOND);
            label.setText(now);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    private JTextField input;
    private String num="";
    private JLabel label;
    private Thread thread;
    private String prev_operation = "";
    private ArrayList<String> equation=new ArrayList<String>();

    public Calculate(){
        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new NorthPanel(),BorderLayout.NORTH);
        c.add(new CenterPanel(),BorderLayout.CENTER);
        c.add(new SouthPanel(),BorderLayout.SOUTH);


        setTitle("김민석-2019158005");
        setVisible(true);
        setSize(300,370);
        setLocationRelativeTo(null);        //화면의 가운데에 띄움
        setResizable(false);        //사이즈 조절 못하게함
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    class NorthPanel extends JPanel{
        public NorthPanel(){
            label = new JLabel();

            label.setFont(new Font("돋움",Font.BOLD,20));
            if(thread == null){
                thread = new Thread(Calculate.this::run);
                thread.start();
            }
            add(label);
        }
    }
    class CenterPanel extends JPanel{
        public CenterPanel(){
            input=new JTextField();
            input.setColumns(5);
            input.setEditable(false);
            input.setSize(270,70);
            input.setBackground(Color.WHITE);   //배경색
            input.setHorizontalAlignment(JTextField.RIGHT); //오른쪽 정렬
            input.setFont(new Font("Arial",Font.BOLD,50));
            add(input);
        }
    }

    class SouthPanel extends JPanel{
        public SouthPanel(){
            JPanel buttonPanel=new JPanel();
            buttonPanel.setLayout(new GridLayout(5,4,10,10));
            //5x4로 Grid 레이아웃으로 숫자랑 연산기호 버튼 설정

            String btn_name[]={"C","","%","/","7","8","9","x","4","5","6","-","1","2","3","+","","0",".","="};
            JButton btn[]=new JButton[btn_name.length];

            for(int i=0;i<btn_name.length;i++){
                btn[i]=new JButton(btn_name[i]);
                btn[i].setFont(new Font("Arial",Font.BOLD,20));
                if(i>=0&&i<=2) {            //C와 %부분 버튼 색깔
                    btn[i].setBackground(Color.LIGHT_GRAY);
                }else if(i==3||i==7||i==11||i==15||i==19) {            //연산 기호 버튼 색깔
                    btn[i].setBackground(Color.ORANGE);
                }else {            //숫자부분 버튼 색깔
                    btn[i].setBackground(Color.DARK_GRAY);
                }
                btn[i].setForeground(Color.BLACK);
                //글자색 지정
                btn[i].setBorderPainted(false); //테두리 지정 해제
                btn[i].addActionListener(new PadActionListener());
                buttonPanel.add(btn[i]);

            }
            add(buttonPanel);
        }
    }
    class PadActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String operation=e.getActionCommand();
            if(operation.equals("C")){      //C눌렀을때
                input.setText("");      //비워줌
            }else if(operation.equals("=")){
                String result=Double.toString(calculate(input.getText().trim()));
                input.setText(""+result);
                num="";
            }else if(operation.equals("%")){
                String result=Double.toString(calculate(input.getText())/100);
                input.setText(""+result);
                num="";
            }else if (operation.equals("+") || operation.equals("-") || operation.equals("x") || operation.equals("/")) {
                //첫 값을 음수로 입력할 수 있음
                if (input.getText().equals("") && operation.equals("-")) {
                    input.setText(input.getText() + e.getActionCommand());

                    //이전에 누른 버튼이 연산자가 아니고 && 위의 계산식이 비버있지 않을 때의 조건문
                } else if (!input.getText().equals("") && !prev_operation.equals("+") && !prev_operation.equals("-") && !prev_operation.equals("x") && !prev_operation.equals("/")) {
                    input.setText(input.getText() + e.getActionCommand());
                }
            }
            else{
                input.setText(input.getText()+e.getActionCommand());
            }
            prev_operation = e.getActionCommand();
        }
    }
    public void Parsing(String input){
        equation.clear();       //비워줌
        for(int i=0;i<input.length();i++){      //문자열 길이만큼 반복함
            char c=input.charAt(i);
            if(c=='-'||c=='x'||c=='/'||c=='+'){
                equation.add(num);
                num="";
                equation.add(c+"");
            }else{      //숫자(또는 .)가 나올경우
                num=num+c;
            }
        }
        equation.add(num);
        equation.remove("");
    }
    public double calculate(String input){
        Parsing(input);

        double prev=0;
        double current=0;
        String cal="";
        for (int i = 0; i < equation.size(); i++) {
            String s = equation.get(i);

            if (s.equals("+")) {
                cal = "add";
            } else if (s.equals("-")) {
                cal = "sub";
            } else if (s.equals("x")) {
                cal = "mul";
            } else if (s.equals("/")) {
                cal = "div";
            } else {
                if ((cal.equals("mul") || cal.equals("div")) && !s.equals("+") && !s.equals("-") && !s.equals("x") && !s.equals("/")) {
                    Double one = Double.parseDouble(equation.get(i - 2));
                    Double two = Double.parseDouble(equation.get(i));
                    Double result = 0.0;

                    if (cal.equals("mul")) {
                        result = one * two;
                    } else if (cal.equals("div")) {
                        result = one / two;
                    }
                    equation.add(i + 1, Double.toString(result));

                    for (int j = 0; j < 3; j++) {
                        equation.remove(i - 2);
                    }

                    i -= 2;
                }
            }
        }
        for(String s: equation){
            if(s.equals("+")){
                cal="add";
            }else if(s.equals("-")){
                cal="sub";
            }else {
                current = Double.parseDouble(s);

                if (cal.equals("add")) {
                    prev += current;
                } else if (cal.equals("sub")) {
                    prev -= current;
                } else {
                    prev = current;
                }
            }
            prev = Math.round(prev * 100000) / 100000.0;
        }
        return prev;

    }
    public static void main(String[] args){
        new Calculate();
    }
}