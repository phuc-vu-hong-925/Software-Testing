import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatementTest {

    @Test
    void testNullArrayCoversBranchNull() {
        assertEquals(-1, NumberUtils.countEvenPositives(null));
    }

    @Test
    void testArrayWithPositiveEvenCoversLoopAndCount() {
        int[] input = {2};
        assertEquals(1, NumberUtils.countEvenPositives(input));
    }
}