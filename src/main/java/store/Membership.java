package store;

import java.text.NumberFormat;
import camp.nextstep.edu.missionutils.Console;

public class Membership {
    public int member(int total) {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        String answer = Console.readLine();
        
        try {
            if (answer.equalsIgnoreCase("Y")) {
                total *= 0.7; 
                System.out.println("멤버십 할인이 적용되었습니다.");
                String formatMoney = NumberFormat.getInstance().format(total);
                System.out.println("내실돈 " + formatMoney);
            }
            if (answer.equalsIgnoreCase("N")) {
                System.out.println("멤버십 할인이 적용되지 않았습니다.");
                String formatMoney = NumberFormat.getInstance().format(total);
                System.out.println("내실돈 " + formatMoney);
            }
           memberEx(answer);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return total;
    }
    public void memberEx(String answer) {
    	if(!(answer.equals("Y")||answer.equals("N")))
    		throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }
}
