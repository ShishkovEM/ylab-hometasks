package hometask2.statsaccumulator;

/**
 * Необходимо реализовать интерфейс StatsAccumulator.
 * Объект данного класса, будучи созданным, может принимать значения через метод
 * add. Приняв значение, объект меняет свое внутреннее состояние, чтобы в любой
 * момент времени предоставить данные о количестве переданных ему элементах,
 * минимальному их них, максимальному из них, а также о среднем арифметическом
 * всех переданных ему элементов.
 */
public class StatsAccumulatorImpl implements StatsAccumulator{
    int min; // Минимальное переданное значение
    int max; // Максимальное переданное значение
    int count; // Число переданных значений
    double avg; // Среднее арифметическое переданных значений

    {
        this.min = 0;
        this.max = 0;
        this.count = 0;
        this.avg = 0.0;
    }

    @Override
    public void add(int value) {
        if (value < this.min) {
            this.min = value;
        }
        if (value > this.max) {
            this.max = value;
        }
        this.avg = (this.avg * this.count + value) / (this.count + 1);
        this.count++;
    }

    @Override
    public int getMin() {
        return this.min;
    }

    @Override
    public int getMax() {
        return this.max;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public Double getAvg() {
        return this.avg;
    }

}