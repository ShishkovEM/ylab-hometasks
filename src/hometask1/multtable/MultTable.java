package hometask1.multtable;

/**
 * На вход ничего не подается, необходимо распечатать таблицу умножения чисел от 1 до 9 (включая)
 */
public class MultTable {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.println(i + " x " + j + " = " + i * j);
            }

            System.out.println("--------");
        }
    }
}
