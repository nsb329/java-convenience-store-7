package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.NumberFormat;
import static org.junit.jupiter.api.Assertions.*;
public class MembershipTest {

    private Membership membership;
    private static final NumberFormat numberFormat = NumberFormat.getInstance();

    @BeforeEach
    public void setUp() {
        membership = new Membership();
    }
    @Test
    public void testMemberEx_WithInvalidInput() {
        String invalidInput = "X";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            membership.memberEx(invalidInput);
        });

        assertEquals("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.", exception.getMessage());
    }
}
