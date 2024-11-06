package store;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class Firstclass {
	Filein file = new Filein();
	PromotionHandler promotionHandler = new PromotionHandler();
	int fin = 0;
	List<String> itemNames = new ArrayList<>();
	List<Integer> itemQuantities = new ArrayList<>();
	int memberDiscount = 0;

	public void First() {
		System.out.println("안녕하세요. w편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
		file.fileall(this);
		System.out.println("\n구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])");
		second();
		Membership member = new Membership();
		memberDiscount = member.member(fin);
		System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
		input();
	}
	
	public void input() {
		String go = Console.readLine();
		try {
			if (go.equals("Y")) {
				First();
			}
			if (go.equals("N")) {
				Result result = new Result();
				result.printReceipt(file.fileList, itemNames, itemQuantities, fin, memberDiscount);
			}
			wrongEx(go);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	
	public void wrongEx(String go) {
		if (!(go.equals("Y") || go.equals("N")))
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
	}

	public void second() {
		String buyitem = Console.readLine();
		try {
			if (buyitem.contains(",")) {
				String[] itemarr = buyitem.split(",");
				processItems(itemarr, itemNames, itemQuantities);
				calc(file.fileList, itemNames, itemQuantities);
			}
			if (!buyitem.contains(",")) {
				String[] itemarr = { buyitem };
				processItems(itemarr, itemNames, itemQuantities);
				calc(file.fileList, itemNames, itemQuantities);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void processItems(String[] itemarr, List<String> itemNames, List<Integer> itemQuantities) {
		for (String item : itemarr) {
			item = item.replace("[", "").replace("]", "");
			String[] parts = item.split("-");
			itemNames.add(parts[0].trim());
			itemQuantities.add(Integer.parseInt(parts[1].trim()));
		}
	}

	public int calc(List<FileinVO> products, List<String> itemNames, List<Integer> itemQuantities) {
		int total = 0;
		for (int i = 0; i < itemNames.size(); i++) {
			FileinVO product = findProduct(products, itemNames.get(i));
			if (product != null) {
				total = total + promotionHandler.applyPromotion(product, itemQuantities.get(i));
			}
			if (product == null) {
				throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
			}
		}
		System.out.println(total);
		fin = total;
		return total;
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
}
