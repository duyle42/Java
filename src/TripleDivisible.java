import java.util.Arrays;
import java.util.List;

public class TripleDivisible {

    public static int bruteForce(List<Integer> arr, int d) {
        System.out.println("Brute force solution");
        int n =  arr.size();
        int result = 0;
        for (int i = 0; i < n - 2; i++) {
            int firstNum = arr.get(i) % d;
            for (int j = i + 1; j < n - 1; j++) {
                int secondNum = arr.get(j) % d;
                for (int k = j + 1; k < n; k++) {
                    int thirdNum = arr.get(k) % d;
                    if ((firstNum + secondNum + thirdNum) % d == 0) {
                        // System.out.println(String.format("%d %d %d", arr.get(i), arr.get(j), arr.get(k)));
                        result++;
                    }
                }
            }
        }

        return result;
    }

    public static int n2Solution(List<Integer> arr, int d) {
        System.out.println("N2 solution");
        int n  = arr.size();

        for (int i = 0; i < n; i++) {
            arr.set(i, arr.get(i) % d);
        }

        arr.sort((a, b) -> { return a - b; });

        int sumToZero = countTripleSumToANumber(arr, 0);

        int sumToD = countTripleSumToANumber(arr, d);

        int sumTo2D = countTripleSumToANumber(arr, 2 * d);

        return sumToZero + sumToD + sumTo2D;
    }

    public static int countTripleSumToANumber(List<Integer> sortedNumbers, int target) {
        int n = sortedNumbers.size();
        int count = 0;
        for (int i = 0; i < n - 2; i++) {
            int firstNum = sortedNumbers.get(i);
            int l = i + 1;
            int r = n - 1;
            while (l < r) {
                int secondNum = sortedNumbers.get(l);
                int thirdNum = sortedNumbers.get(r);
                int sum = firstNum + secondNum + thirdNum;
                if (sum == target) {
                    if (secondNum == thirdNum) {
                        count += combination(r - l + 1, 2);
                        l = r + 1;
                    } else {
                        int lCount = 1;
                        while (l < r && sortedNumbers.get(l) == sortedNumbers.get(l + 1)) {
                            l++;
                            lCount++;
                        }

                        int rCount = 1;
                        while (r > l && sortedNumbers.get(r) == sortedNumbers.get(r - 1)) {
                            r--;
                            rCount++;
                        }

                        l++;
                        r--;

                        count += lCount * rCount;
                    }
                } else if (sum < target) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return count;
    }

    public static int combination(int n, int r) {
        return (factorial(n) / (factorial(r) * factorial(n - r)));
    }

    public static int factorial(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    public static void main(String args[]) {
        List<Integer> test_1 = Arrays.asList(4, 3, 2, 8);
        System.out.println(bruteForce(test_1, 5));
        System.out.println(n2Solution(test_1, 5));

        List<Integer> test_2 = Arrays.asList(0, 2, 2, 3);
        System.out.println(bruteForce(test_2, 5));
        System.out.println(n2Solution(test_2, 5));

        List<Integer> test_3 = Arrays.asList(0, 2, 2, 2, 3, 3);
        System.out.println(bruteForce(test_3, 5));
        System.out.println(n2Solution(test_3, 5));

        List<Integer> test_4 = Arrays.asList(1, 2, 2, 2, 2, 2, 2);
        System.out.println(bruteForce(test_4, 5));
        System.out.println(n2Solution(test_4, 5));

        List<Integer> test_5 = Arrays.asList(1, 1, 1, 2, 2, 4, 7, 8, 8, 9);
        System.out.println(bruteForce(test_5, 5));
        System.out.println(n2Solution(test_5, 5));
    }
}
