package operator;

public class Operator {
    // numbers라는 int형 배열이 있다.
    // 해당 해병에 들어있는 숫자들은 오직 한 숫자를 제외하고는 모두 두 번씩 들어있다.
    // 오직 한 번만 등장하는 숫자를 찾는 코드를 작성하라.

    public static void main(String[] args) {
        Operator operator = new Operator();
        int result = operator.solution(new int[]{5, 2, 4, 1, 2, 4, 5});
        System.out.println(result);
    }

    // XOR
    // 5 ^ 0 = 5
    // 5 ^ 5 = 0
    // 101
    // 101
    // ---
    // 000
    // 5 ^ 1 ^ 5 => (5 ^ 5) ^ 1 = 0 ^ 1 = 1
    private int solution(int[] numbers) {
        int result = 0;
        for (int number : numbers) {
            result ^= number;
        }
        return result;
    }
}
