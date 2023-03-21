package hometask3.datedmap;

public class DatedMapTest {
    static DatedMap datedMap = new DatedMapImpl();

    public static void main(String[] args) throws InterruptedException {
        String keyOne = "Первый ключ";
        String keyTwo = "Второй ключ";
        String keyThree = "Третий ключ";
        String valueOne = "Первое значение";
        String valueTwo = "Второе значение";
        String valueThree = "Третье значение";
        String valueFour = "Четвёртое значение";

        datedMap.put(keyOne, valueOne);
        System.out.println(keyOne + " - " + datedMap.get(keyOne) + " - " + datedMap.getKeyLastInsertionDate(keyOne)); // Первый ключ - Первое значение - дата
        Thread.sleep(1000);

        datedMap.put(keyTwo, valueTwo);
        System.out.println(keyTwo + " - " + datedMap.get(keyTwo) + " - " + datedMap.getKeyLastInsertionDate(keyTwo)); // Второй ключ - Второе значение - дата + 1 сек.
        Thread.sleep(3000);

        datedMap.put(keyThree, valueThree);
        System.out.println(keyThree + " - " + datedMap.get(keyThree) + " - " + datedMap.getKeyLastInsertionDate(keyThree)); // Третий ключ - Третье значение - дата + 3 сек.
        Thread.sleep(5000);

        datedMap.put(keyTwo, valueFour);
        System.out.println(keyTwo + " - " + datedMap.get(keyTwo) + " - " + datedMap.getKeyLastInsertionDate(keyTwo)); // Второй ключ - Четвёртое значение - дата + 5 сек.

        datedMap.remove(keyOne);
        System.out.println(keyOne + " - " + datedMap.get(keyOne) + " - " + datedMap.getKeyLastInsertionDate(keyOne)); // Первый ключ - null - null

        System.out.println(datedMap.keySet()); // [Второй ключ, Третий ключ]

        System.out.println(datedMap.containsKey(keyOne)); // false

        System.out.println(datedMap.containsKey(keyTwo)); // true
    }

}
