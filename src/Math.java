import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.lang.Math.max;

class Math {
    private long min = 0;
    private long max = 0;
    private long mean = 0;
    private double avg = 0.0;
    private List<Long> incrementSequence;
    private List<Long> decrementSequence;

    public void doMath(List<Long> numbers) {
        findSequences(numbers);

        Collections.sort(numbers);

        Thread thread = new Thread(() -> avg = average(numbers));
        thread.start();
        max = numbers.getLast();
        min = numbers.getFirst();
        mean = median(numbers);
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private long median(List<Long> numbers) {
        int size = numbers.size();
        if (size % 2 == 1)
            return numbers.get(size / 2);
        else
            return (numbers.get(size / 2 - 1) + numbers.get(size / 2)) / 2;
    }

    private double average(List<Long> numbers) {

        return (double) numbers.stream().reduce(0L, Long::sum) / numbers.size();
    }

//    private void findSequences(List<Long> numbers) {
//        List<Long> incSeq = new ArrayList<>();
//        List<Long> decSeq = new ArrayList<>();
//
//        long firstNum = numbers.getFirst();
//        incSeq.add(firstNum);
//        decSeq.add(firstNum);
//
//        for (int i = 1; i < numbers.size(); i++) {
//            long currentNum = numbers.get(i);
//            long prevNum = numbers.get(i - 1);
//
//
//            if (currentNum > prevNum) {
//                // Inc
//                incSeq.add(currentNum);
//                // Dec
//                if (decrementSequence.size() < decSeq.size())
//                    decrementSequence = List.copyOf(decSeq);
//                decSeq.clear();
//            } else if (currentNum < prevNum) {
//                // Dec
//                decSeq.add(currentNum);
//                // Inc
//                if (incrementSequence.size() < incSeq.size())
//                    incrementSequence = List.copyOf(incSeq);
//                incSeq.clear();
//            }
//        }
//        if (decrementSequence.size() < decSeq.size())
//            decrementSequence = List.copyOf(decSeq);
//        if (incrementSequence.size() < incSeq.size())
//            incrementSequence = List.copyOf(incSeq);
//    }

    private void findSequences(List<Long> numbers) {
        int incMax = 1;
        int incStart = 0;
        int incEnd = 1;
        int decMax = 1;
        int decStart = 0;
        int decEnd = 1;

        for (int i = 1, currentDecMax = 1, currentIncMax = 1; i < numbers.size(); i++) {
            long currentNum = numbers.get(i);
            long prevNum = numbers.get(i - 1);

            if (currentNum > prevNum) {
                currentIncMax++;
                currentDecMax = 0;
            } else if (currentNum < prevNum) {
                currentDecMax++;
                currentIncMax = 0;
            }

            if (currentIncMax > incMax) {
                incEnd = i + 1;
                incStart = incEnd - currentIncMax;
                incMax = currentIncMax;
            }

            if (currentDecMax > decMax) {
                decEnd = i + 1;
                decStart = decEnd - currentDecMax;
                decMax = currentDecMax;
            }
        }

        incrementSequence = new ArrayList<>(numbers.subList(max(incStart - 1, 0), incEnd));
        decrementSequence = new ArrayList<>(numbers.subList(max(decStart - 1, 0), decEnd));
    }

    public void mergeSort(List<Long> numbers) {
        if (numbers == null || numbers.size() <= 1)
            return;

        int mid = numbers.size() / 2;
        List<Long> left = new ArrayList<>(numbers.subList(0, mid));
        List<Long> right = new ArrayList<>(numbers.subList(mid, numbers.size()));

        mergeSort(left);
        mergeSort(right);
        mergeArrays(left, right, numbers);
    }

    private void mergeArrays(List<Long> left, List<Long> right, List<Long> numbers) {
        int i = 0, j = 0, k = 0;

        while (j < left.size() && k < right.size()) {
            if (left.get(j) <= right.get(k))
                numbers.set(i++, left.get(j++));
            else
                numbers.set(i++, right.get(k++));
        }

        while (j < left.size())
            numbers.set(i++, left.get(j++));

        while (k < right.size()) {
            numbers.set(i++, right.get(k++));
        }

    }

    @Override
    public String toString() {
        return String.format("Max value: %d%n", max) +
                String.format("Min value: %d%n", min) +
                String.format("Mean value: %d%n", mean) +
                String.format("Average value: %f%n", avg) +
                String.format("Increment Sequence: %s%n", incrementSequence) +
                String.format("Decrement Sequence: %s%n", decrementSequence);
    }
}
