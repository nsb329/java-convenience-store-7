package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FirstclassTest {

    private Firstclass firstclass;
    private Filein mockFilein;
    private PromotionHandler mockPromotionHandler;
    private Membership mockMembership;

    @BeforeEach
    public void setUp() {
        mockFilein = mock(Filein.class);
        mockPromotionHandler = mock(PromotionHandler.class);
        mockMembership = mock(Membership.class);

        firstclass = new Firstclass();
        firstclass.file = mockFilein;
        firstclass.promotionHandler = mockPromotionHandler;
    }

    @Test
    public void testProcessItems() {
        String[] itemArr = {"[사이다-2]", "[감자칩-1]"};
        List<String> itemNames = new ArrayList<>();
        List<Integer> itemQuantities = new ArrayList<>();

        firstclass.processItems(itemArr, itemNames, itemQuantities);

        assertEquals(2, itemNames.size());
        assertEquals(2, itemQuantities.size());
        assertEquals("사이다", itemNames.get(0));
        assertEquals(2, itemQuantities.get(0));
        assertEquals("감자칩", itemNames.get(1));
        assertEquals(1, itemQuantities.get(1));
    }

    @Test
    public void testCalc() {
        List<FileinVO> products = new ArrayList<>();
        FileinVO product1 = new FileinVO();
        product1.setName("사이다");
        product1.setPrice(1000);
        product1.setQuantity(10);
        products.add(product1);

        List<String> itemNames = new ArrayList<>();
        itemNames.add("사이다");
        List<Integer> itemQuantities = new ArrayList<>();
        itemQuantities.add(3);

        when(mockPromotionHandler.applyPromotion(product1, 3)).thenReturn(2000);

        int total = firstclass.calc(products, itemNames, itemQuantities);
        
        assertEquals(2000, total);
    }

    @Test
    public void testMembershipDiscount() {
        firstclass.fin = 5000;
        when(mockMembership.member(5000)).thenReturn(4500);

        int memberDiscount = mockMembership.member(firstclass.fin);
        
        assertEquals(4500, memberDiscount);
    }
    @Test
    public void testWrongEx_WithInvalidInput() {
    	String invalidInput = "X";
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
    			() -> { firstclass.wrongEx(invalidInput); });
    	assertEquals("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.", exception.getMessage()); }
}
