import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PathTest {

    @Test
    void testPathNullArray() {
        assertEquals(-1, NumberUtils.countEvenPositives(null));
    }

    @Test
    void testPathEmptyArray() {
        assertEquals(0, NumberUtils.countEvenPositives(new int[]{}));
    }

    @Test
    void testPathAllConditionsEvaluated() {
        // -3 (x <= 0)
        // 5  (x > 0 nhưng x % 2 != 0)s
        // 4  (x > 0 và x % 2 == 0)
        int[] input = {-3, 5, 4};
        assertEquals(1, NumberUtils.countEvenPositives(input));
    }
}