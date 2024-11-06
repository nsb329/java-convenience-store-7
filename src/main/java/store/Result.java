package store;

import java.util.List;

public class Result {
    public static int eventDiscount = 0;

    public void printReceipt(List<FileinVO> products, List<String> itemNames, List<Integer> itemQuantities, int total, int membershipDiscount) {
        int finalAmount = total - eventDiscount - membershipDiscount;
        
        System.out.println("===========W 편의점=============");
        System.out.printf("%-10s%10s%10s\n", "상품명", "수량", "금액");
        for (int i = 0; i < itemNames.size(); i++) {
            FileinVO product = findProduct(products, itemNames.get(i));
            if (product != null) {
                int price = product.getPrice() * itemQuantities.get(i);
                System.out.printf("%-10s%10d%10s\n", itemNames.get(i), itemQuantities.get(i), formatPrice(price));
            }
        }
        System.out.println("===========증 정=============");
        // 예시로 증정상품 콜라 1개 추가
        System.out.println("콜라\t\t1");
        System.out.println("==============================");
        System.out.printf("총구매액%15d%15s\n", getTotalQuantity(itemQuantities), formatPrice(total));
        System.out.printf("행사할인%27s\n", formatPrice(-eventDiscount));
        System.out.printf("멤버십할인%25s\n", formatPrice(-finalAmount));
        System.out.printf("내실돈%27s\n", formatPrice(membershipDiscount));
    }

    private FileinVO findProduct(List<FileinVO> products, String itemName) {
        for (FileinVO product : products) {
            if (isMatchingProduct(product, itemName)) {
                return product;
            }
        }
        return null;
    }

    private boolean isMatchingProduct(FileinVO product, String itemName) {
        return product.getName().equals(itemName);
    }

    private int getTotalQuantity(List<Integer> itemQuantities) {
        int totalQuantity = 0;
        for (int quantity : itemQuantities) {
            totalQuantity += quantity;
        }
        return totalQuantity;
    }

    private String formatPrice(int price) {
        return String.format("%,d원", price);
    }
}
