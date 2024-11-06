package store;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;

public class PromotionHandler {
    private List<FileinVO> products;

    public PromotionHandler() {}

    public PromotionHandler(List<FileinVO> products) {
        this.products = products;
    }

    public int applyPromotion(FileinVO product, int quantity) {
        int total = 0;
        LocalDateTime current = DateTimes.now();
        LocalDate currentDate = current.toLocalDate();
        if (isPromotionAvailable(product) && PromotionEnum.CARBONATED_2PLUS1.isDateValid(currentDate)) {
            total = applyPro1(product, quantity);
            System.out.println(total);
        }
        if (isPromotionAvailable2(product) && PromotionEnum.MD_RECOMMENDED.isDateValid(currentDate)) {
            total = applyPro2(product, quantity);
            System.out.println(total);
        } 
        if (isPromotionAvailable3(product) && PromotionEnum.FLASH_SALE.isDateValid(currentDate)) {
            total = applyPro3(product, quantity);
            System.out.println(total);
        }
            total = product.getPrice() * quantity;
        return total;
    }

    private int applyPro1(FileinVO product, int quantity) {
        int total = 0;
        int discount = 0;
        if (product.getQuantity() - (product.getQuantity() % 3) >= quantity) {
            if (quantity % 3 == 0) {
                total = handlePromotionExactQuantity(product, quantity);
                discount = product.getPrice() * (quantity / 3);
            } else if (quantity % 3 == 1) {
                total = handlePromotionPlusOne(product, quantity);
                discount = product.getPrice() * (quantity / 3);
            } else if (quantity % 3 == 2) {
                total = handlePromotionPlusTwo(product, quantity);
                discount = product.getPrice() * (quantity / 3);
            }
        }
        Result.eventDiscount += discount;
        return total;
    }

    private int applyPro2(FileinVO product, int quantity) {
        int total = 0;
        try {
            overEx(product, quantity);
        } catch (IllegalArgumentException e2) {
            System.out.println(e2.getMessage());
        }
        if (product.getQuantity() - (product.getQuantity() % 3) >= quantity) {
            if (quantity % 2 == 0) {
                total = handlePromotionExactQuantity2(product, quantity);
            } else if (quantity % 2 == 1) {
                total = handlePromotionPlusOne(product, quantity);
            }
        }
        return total;
    }

    public void overEx(FileinVO product, int quantity) {
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    private int applyPro3(FileinVO product, int quantity) {
        int total = 0;
        if (product.getQuantity() - (product.getQuantity() % 2) >= quantity) {
            if (quantity % 2 == 0) {
                total = handlePromotionExactQuantity2(product, quantity);
            }
            if (quantity % 2 == 1) {
                total = handlePromotionPlusOne(product, quantity);
            }
        }
        return total;
    }

    private int handlePromotionExactQuantity2(FileinVO product, int quantity) {
        adjustProductQuantity(product, quantity);
        return product.getPrice() * (quantity / 2) * 2;
    }

    private int handlePromotionExactQuantity(FileinVO product, int quantity) {
        adjustProductQuantity(product, quantity);
        return product.getPrice() * (quantity / 3) * 2;
    }

    private int handlePromotionPlusOne(FileinVO product, int quantity) {
        adjustProductQuantity(product, quantity);
        return (product.getPrice() * (quantity / 3) * 2)
                + (product.getPrice() * (quantity - ((quantity / 3) * 2) - (quantity / 3)));
    }

    private int handlePromotionPlusTwo(FileinVO product, int quantity) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", product.getName());
        String answer = Console.readLine();
        if (answer.equals("Y")) {
            return handlePromotionPlusTwoAccepted(product, quantity);
        }
        if (answer.equals("N")) {
            handlePromotionPlusTwoDeclined(product, quantity);
        }
        return 0;
    }

    private int handlePromotionPlusTwoAccepted(FileinVO product, int quantity) {
        adjustProductQuantity(product, quantity + 1);
        return (product.getPrice() * (quantity / 3) * 3)
                + (product.getPrice() * (quantity - ((quantity / 3) * 3) - (quantity / 3)));
    }

    private int handlePromotionPlusTwoDeclined(FileinVO product, int quantity) {
        adjustProductQuantity(product, quantity);
        return (product.getPrice() * (quantity / 3) * 2)
                + (product.getPrice() * (quantity - ((quantity / 3) * 2) - (quantity / 3)));
    }

    public boolean isPromotionAvailable(FileinVO product) {
        return (product.getPromotion() != null && !product.getPromotion().isEmpty())
                && product.getPromotion().equals("탄산2+1");
    }

    public boolean isPromotionAvailable2(FileinVO product) {
        return (product.getPromotion() != null && !product.getPromotion().isEmpty())
                && product.getPromotion().equals("MD추천상품");
    }

    public boolean isPromotionAvailable3(FileinVO product) {
        return (product.getPromotion() != null && !product.getPromotion().isEmpty())
                && product.getPromotion().equals("반짝할인");
    }

    public void adjustProductQuantity(FileinVO product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
    }
}
