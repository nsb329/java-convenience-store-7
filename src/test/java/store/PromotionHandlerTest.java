package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionHandlerTest {

    private PromotionHandler promotionHandler;
    private List<FileinVO> products;

    @BeforeEach
    public void setUp() {
        products = new ArrayList<>();
        products.add(new FileinVO());
        products.add(new FileinVO());
        products.add(new FileinVO());
        promotionHandler = new PromotionHandler(products);
    }

    @Test
    public void testOverEx_ThrowsException_WhenQuantityExceedsStock() {
        // 수량이 재고를 초과했을 때 예외가 발생하는지 테스트
        FileinVO product = products.get(0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            promotionHandler.overEx(product, 15);
        });

        assertEquals("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.", exception.getMessage());
    }
}
