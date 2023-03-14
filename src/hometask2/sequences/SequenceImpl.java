package hometask2.sequences;

/**
 * Последовательности A - J заданы в виде нескольких значений следующим образом
 * A. 2, 4, 6, 8, 10...
 * B. 1, 3, 5, 7, 9...
 * C. 1, 4, 9, 16, 25...
 * D. 1, 8, 27, 64, 125...
 * E. 1, -1, 1, -1, 1, -1...
 * F. 1, -2, 3, -4, 5, -6...
 * G. 1, -4, 9, -16, 25....
 * H. 1, 0, 2, 0, 3, 0, 4....
 * I. 1, 2, 6, 24, 120, 720...
 * J. 1, 1, 2, 3, 5, 8, 13, 21…
 * Необходимо найти закономерности, по которым эти последовательности
 * сформированы, и реализовать следующий интерфейс, каждый метод которого
 * принимает число N и выводит в консоль N элементов соответствующей
 * последовательности. Каждый элемент можно выводить с новой строки
 */
public class SequenceImpl implements SequenceGenerator {

    @Override
    public void a(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * 2);
        }
    }

    @Override
    public void b(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i + (i - 1));
        }
    }

    @Override
    public void c(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i);
        }
    }

    @Override
    public void d(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i * i);
        }
    }

    @Override
    public void e(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i % 2 == 0 ? -1 : 1);
        }
    }

    @Override
    public void f(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i % 2 == 0 ? -i : i);
        }
    }

    @Override
    public void g(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i % 2 == 0 ? - (i * i) : (i * i));
        }
    }

    @Override
    public void h(int n) {
        for (int i = 1, j = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.println(0);
            } else {
                System.out.println(j);
                j++;
            }
        }
    }

    @Override
    public void i(int n) {
        int result = 1;

        for (int i = 1; i <= n; i++) {
            System.out.println(result * i);
            result *= i;
        }
    }

    @Override
    public void j(int n) {

        if (n == 1) {
            System.out.println(1);
        } else if (n == 2) {
            System.out.println(1 + System.lineSeparator() + 1);
        } else if (n >= 3) {
            int prePrevious = 1;
            int previous = 1;
            int current;

            System.out.println(1 + System.lineSeparator() + 1);

            for (int i = 3; i <= n; i++) {
                current = prePrevious + previous;
                System.out.println(current);
                prePrevious = previous;
                previous = current;
            }
        }
    }
}