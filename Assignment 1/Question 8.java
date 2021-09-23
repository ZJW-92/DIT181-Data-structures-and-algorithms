import java.util.*;

public class Bench {

  public static void main(final String[] args) {
    executionTimeReport();
  }

  
  public static int[] gSample(int size, int randomness) {
    int[] sample = new int[size];

    Random random = new Random();
    int previousElement = 0;
    for (int i = 0; i < size; i++) {
      if (random.nextInt(100) >= randomness) {
        int randomOffset = random.nextInt(3);
        int currentElement = previousElement + randomOffset;
        sample[i] = currentElement;
        previousElement = currentElement;
      } else {
        sample[i] = random.nextInt(size);
      }
    }

    return sample;
  }

  public static int[] gSortedAscending(int size) {
    int[] arr = new int[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i + 1;
    }
    return arr;
  }

  public static int[] gSortedDescending(int size) {
    int[] arr = new int[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = arr.length - (i + 1);
    }
    return arr;
  }

  public static int gInt(int size) {
    Random random = new Random();
    return random.nextInt(size);
  }


  private static int[] SAMPLE_SIZES = new int[] { 100, 100000 };

  private static void executionTimeReport() {
    for (int size : SAMPLE_SIZES) {
      executionTimeReport(size);
    }
  }

  public static interface Function<A, B> {
    public B apply(A arg);
  }

  public static Function<int[], int[]> bubbleSort = new Function<int[], int[]>() {
    @Override
    public int[] apply(int[] array) {
      LabSorting.bubbleSort(array);
      return array;
    }
  };

  public static Function<int[], int[]> mergeSort = new Function<int[], int[]>() {
    @Override
    public int[] apply(int[] array) {
      LabSorting.mergeSort(array);
      return array;
    }
  };

  public static Function<int[], int[]> insertionSort = new Function<int[], int[]>() {
    @Override
    public int[] apply(int[] array) {
      LabSorting.insertionSort(array);
      return array;
    }
  };

  public static Function<int[], int[]> quickSortMedian = new Function<int[], int[]>() {
    @Override
    public int[] apply(int[] array) {
      LabSorting.quickSortMedian(array);
      return array;
    }
  };

  public static Function<int[], int[]> quickSort = new Function<int[], int[]>() {
    @Override
    public int[] apply(int[] array) {
      LabSorting.quickSort(array);
      return array;
    }
  };


  private static String execute(Function<int[], int[]> algorithm, int[] input) {
  
    final long target = 10000000;
    final int MAX_LIVES = 3;
    int[] result = {};
    int repetitions = 1;
 
    long runtime = Long.MAX_VALUE;

    int lives = MAX_LIVES;
    try {
      while (true) {
        int[][] inputs = new int[repetitions][];
        for (int i = 0; i < repetitions; i++)
          inputs[i] = Arrays.copyOf(input, input.length);
        System.gc();
        Thread.yield();

        long startTime = System.nanoTime();
        for (int i = 0; i < repetitions; i++)
          result = algorithm.apply(inputs[i]);
        long endTime = System.nanoTime();
        runtime = Math.min(runtime, endTime - startTime);

        if (repetitions == 1 && runtime >= 30 * target)
          break;
        if (runtime >= target) {
          if (lives == 0)
            break;
          else
            lives--;
        } else {
          if (runtime == 0)
            repetitions *= 2;
          else
            repetitions *= 2 * target / runtime;
          runtime = Long.MAX_VALUE;
          lives = MAX_LIVES;
        }
      }
    } catch (UnsupportedOperationException uop) {
      return "-";
    } catch (Exception e) {
      return "EXCEPTION";
    } catch (StackOverflowError e) {
      return "STACK OVERFLOW";
    }
    int[] reference = Arrays.copyOf(input, input.length);
    Arrays.sort(reference);
    if (Arrays.equals(result, reference)) {
      return String.format("%6f", (double) runtime / ((long) repetitions * 1000000));
    } else {
      return "INCORRECT";
    }
  }

  private static void executionTimeReport(int size) {
    int[] randomSample = gSample(size, 100);
    int[] ascendingSample = gSortedAscending(size);
    int[] descendingSample = gSortedDescending(size);

    System.out.println(String.format(
        "Arrays of length %d\n" 
            + "=================================================================\n"
            + "Algorithm      | %14s | %14s | %14s\n" 
            + "Bubble sort    | %14s | %14s | %14s\n"
            + "Merge sort     | %14s | %14s | %14s\n" 
            + "Insertion sort | %14s | %14s | %14s\n"
            + "Quicksort (m)  | %14s | %14s | %14s\n" 
            + "Quicksort (0)  | %14s | %14s | %14s\n",
        size, "Random [ms]", "Ascending [ms]", "Descending [ms]", 
        execute(bubbleSort, randomSample),
        execute(bubbleSort, ascendingSample), 
        execute(bubbleSort, descendingSample), 
        execute(mergeSort, randomSample),
        execute(mergeSort, ascendingSample), 
        execute(mergeSort, descendingSample),
        execute(insertionSort, randomSample), 
        execute(insertionSort, ascendingSample),
        execute(insertionSort, descendingSample), 
        execute(quickSortMedian, randomSample),
        execute(quickSortMedian, ascendingSample), 
        execute(quickSortMedian, descendingSample),
        execute(quickSort, randomSample), 
        execute(quickSort, ascendingSample), 
        execute(quickSort, descendingSample)));
  }
}
