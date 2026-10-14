public class NumberUtils {
    public static int countEvenPositives(int[] arr) {
        if (arr == null) {
            return -1;
        }

        int count = 0;
        for (int x : arr) {
            if (x > 0 && x % 2 == 0) {
                count++;
            }
        }
        return count;
    }
}