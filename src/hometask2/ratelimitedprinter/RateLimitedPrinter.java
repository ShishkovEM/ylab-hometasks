package hometask2.ratelimitedprinter;

/**
 * Реализовать класс RateLimiterPrinter. Класс имеет конструктор, в который передается
 * interval и метод print(), в который передается строка. Класс функционирует по
 * следующему принципу: на объекте класса вызывается метод print(). Далее идет
 * проверка, когда был последний вывод в консоль. Если интервал (в миллисекундах)
 * между последним состоявшимся выводом и текущим выводом больше значения
 * interval, переданного в конструктор - то происходит вывод значения. Иначе - не
 * происходит, и сообщение отбрасывается. То есть класс ограничивает частоту вывода в
 * консоль. Другими словами, сообщение не будет выводится чаще чем 1 раз в interval
 * милисекунд. Реализовать описанный класс.
 */
public class RateLimitedPrinter implements RatedLimitedPrintable {
    int interval; // Интервал
    long lastCallTime; // Время последнего вывода в консоль

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
        this.lastCallTime = 0L;
    }

    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastCallTime > this.interval) {
            System.out.println(message);
            this.lastCallTime = currentTime;
        }
    }
}