/* This class is used to get unrepeatable random number */

import java.util.Random;

public class RandomNumber {

    private static Random random = new Random(0);

    public int[] randomArray(int max) {

        // set number into an array
        int[] numbers = new int[max];
        for (int i = 0; i < max; i++) {
            numbers[i] = i;
        }

        // choose a random position and replace the number at that position with the current last number
        int temp;
        for (int i = max - 1; i > 0; i--) {
            int j = random.nextInt(i);
            temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
        return numbers;
    }

}
