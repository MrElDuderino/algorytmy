import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Sorting
{
    public static int option = 0;

    private static  class Statistics
    {
        double swaps = 0;
        double comparisons = 0;
        double time = 0;

        public void reset()
        {
            this.swaps = 0;
            this.comparisons = 0;
            this.time = 0;
        }
    }

    public static void main(String[] args) throws IOException
    {
        if(args.length < 3)
        {
            System.out.println("Too few arguments.");
            System.exit(0);
        }
        else if(args[0].equals("--type"))
            option = 1;
        else if(args[0].equals("--stat"))
            option = 2;



        int k = 0;
        boolean order = true;
        int[] array;
        int n;
        int minElement = 0;
        int maxElement = 10000;

        if(option == 1)
        {
            if (args[2].equals("--asc"))
                order = true;
            else if (args[2].equals("--desc"))
                order = false;
            else
            {
                System.out.println("Wrong ordering.");
                System.exit(0);
            }

            Scanner reader = new Scanner(System.in);
            System.out.print("Enter number of elements: ");
            n = reader.nextInt();

            if (n < 1)
            {
                System.out.println("Wrong number.");
                System.exit(0);
            }

            array = new int[n];

            System.out.print("\nEnter elements: ");

            for (int i = 0; i < n; i++)
                array[i] = reader.nextInt();

            Statistics statistics = new Statistics();

            switch (args[1])
            {
                case "select":
                    selectSort(array, order, statistics);
                    break;
                case "insert":
                    insertionSort(array, order, statistics);
                    break;
                case "heap":
                    heapSort(array, order, statistics);
                    break;
                case "quick":
                    quickSort(array, order, statistics);
                    break;
                case "mquick":
                    modifiedQuickSort(array, order, statistics);
                    break;
                default:
                    System.out.println("Wrong name of algorithm.");
                    System.exit(0);
            }


            if (isSorted(array, order))
                System.out.println("\nArray sorted correctly.");
            else
                System.out.println("\nArray sorted incorrectly.");

            System.out.println("Number of sorted elements: " + n);
            System.out.println("Sorted array:");
            printArray(array);

            System.err.println("Comparisons: " + statistics.comparisons);
            System.err.println("Swaps: " + statistics.swaps);
            System.err.println("Time: " + statistics.time);
        }

        else if(option == 2)
        {
            Statistics selectStat = new Statistics();
            Statistics insertStat = new Statistics();
            Statistics heapStat = new Statistics();
            Statistics quickStat = new Statistics();
            Statistics mQuickStat = new Statistics();
            StringBuffer sbf = new StringBuffer();

            if(args.length > 2)
                k = Integer.parseInt(args[2]);
            else
                System.exit(0);

            String filename = args[1];
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            StringBuffer stf = new StringBuffer();

            System.out.println("K = " + k);

            int[] arrayCopy;

            for(n=100; n<=10000; n+=100)
            {
                System.out.println("n = " + n);
                array = new int[n];
                arrayCopy = new int[n];

                for(int i=0; i<k; i++)
                {
                    for (int j=0; j<n; j++)
                    {
                        array[j] = ThreadLocalRandom.current().nextInt(minElement, maxElement + 1);
                        arrayCopy[j] = array[j];
                    }

                    selectSort(arrayCopy, order, selectStat);
                    copyArray(array, arrayCopy);
                    insertionSort(arrayCopy, order, insertStat);
                    copyArray(array, arrayCopy);
                    heapSort(arrayCopy, order, heapStat);
                    copyArray(array, arrayCopy);
                    quickSort(arrayCopy, order, quickStat);
                    copyArray(array, arrayCopy);
                    modifiedQuickSort(arrayCopy, order, mQuickStat);
                }

                stf.append(n + " ");
                stf.append(selectStat.comparisons/k + " " + selectStat.swaps/k + " " + selectStat.time/k + " " + (selectStat.comparisons/k)/n + " " + (selectStat.swaps/k)/n + " ");
                stf.append(insertStat.comparisons/k + " " + insertStat.swaps/k + " " + insertStat.time/k + " " + (insertStat.comparisons/k)/n + " " + (insertStat.swaps/k)/n + " ");
                stf.append(heapStat.comparisons/k + " " + heapStat.swaps/k + " " + heapStat.time/k + " " + (heapStat.comparisons/k)/n + " " + (heapStat.swaps/k)/n + " ");
                stf.append(quickStat.comparisons/k + " " + quickStat.swaps/k + " " + quickStat.time/k + " " + (quickStat.comparisons/k)/n + " " + (quickStat.swaps/k)/n + " ");
                stf.append(mQuickStat.comparisons/k + " " + mQuickStat.swaps/k + " " + mQuickStat.time/k + " " + (mQuickStat.comparisons/k)/n + " " + (mQuickStat.swaps/k)/n + "\r\n");
                selectStat.reset();
                insertStat.reset();
                heapStat.reset();
                quickStat.reset();
                mQuickStat.reset();
            }

            writer.write(stf.toString());
            writer.close();
        }
    }


    public static void selectSort(int[] array, boolean order, Statistics statistics)
    {
        double startTime = System.nanoTime();
        int n = array.length;
        int index;

        for(int i=0; i<n-1; i++)
        {
            index = i;

            for(int j=i; j<n-1; j++)
            {
                if(compare(array[j+1], array[index], statistics) == order)
                    index = j+1;
            }

            swap(array, i, index, statistics);
        }

        statistics.time += System.nanoTime() - startTime;
    }

    public static void insertionSort(int[] array, boolean order, Statistics statistics)
    {
        double startTime = System.nanoTime();
        insertionSortCall(array, 0, array.length-1, order, statistics);
        statistics.time += System.nanoTime() - startTime;
    }

    public static void insertionSortCall(int[] array, int low, int high, boolean order, Statistics statistics)
    {
        int temp;
        int j;

        for(int i=low+1; i<=high; i++)
        {
            temp = array[i];
            j = i-1;

            while(compare(temp, array[j], statistics) == order)
            {
                if(option == 1)
                    System.err.printf("Array[%d] = array[%d] = %d\n", j+1, j, array[j]);
                statistics.swaps++;
                array[j+1] = array[j];
                if(option == 1)
                    printArray(array);
                j--;

                if(j < 0)
                    break;
            }

            statistics.swaps++;
            if(option == 1)
                System.err.printf("Array[%d] = %d\n", j+1, temp);
            array[j+1] = temp;
            if(option == 1)
                printArray(array);
        }
    }


    public static void heapSort(int array[], boolean order, Statistics statistics)
    {
        double startTime = System.nanoTime();
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(array, n, i, order, statistics);

        for (int i=n-1; i>=0; i--)
        {
            swap(array, 0, i, statistics);
            heapify(array, i, 0, order, statistics);
        }

        statistics.time += System.nanoTime() - startTime;
    }

    public static void heapify(int array[], int n, int i, boolean order, Statistics statistics)
    {
        int maximal = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if(l < n && compare(array[maximal], array[l], statistics) == order)
            maximal = l;

        if(r < n && compare(array[maximal], array[r], statistics) == order)
            maximal = r;

        if (maximal != i)
        {
            swap(array, i, maximal, statistics);
            heapify(array, n, maximal, order, statistics);
        }
    }

    public static void quickSort(int[] array, boolean order, Statistics statistics)
    {
        double startTime = System.nanoTime();
        quickSortCall(array, 0, array.length-1, order, statistics);
        statistics.time += System.nanoTime() - startTime;
    }

    public static void quickSortCall(int[] array, int low, int high, boolean order, Statistics statistics)
    {
        int pivotIndex;

        if(low < high)
        {
            pivotIndex = partition(array, low, high, order, statistics);
            quickSortCall(array, low, pivotIndex-1, order, statistics);
            quickSortCall(array, pivotIndex+1, high, order, statistics);
        }
    }

    public static int partition(int[] array, int low, int high, boolean order, Statistics statistics)
    {
        int pivot = array[high];

        int counter = low;
        if(option == 1)
            System.err.println("Pivot = " + array[high]);

        for(int i=low; i<high; i++)
        {
            if(compare(pivot, array[i], statistics) != order)
            {
                if(i != counter)
                    swap(array, i, counter, statistics);
                counter++;
            }
        }

        swap(array, counter, high, statistics);
        return counter;
    }

    public static void modifiedQuickSort(int [] array, boolean order, Statistics statistics)
    {
        double startTime = System.nanoTime();
        modifiedQuickSortCall(array, 0, array.length-1, order, statistics);
        statistics.time += System.nanoTime() - startTime;
    }

    public static void modifiedQuickSortCall(int[] array, int low, int high, boolean order, Statistics statistics)
    {
        int pivotIndex;

        if(low < high)
        {
            if(high - low + 1 <= 16)
                insertionSortCall(array, low, high, order, statistics);
            else
            {
                pivotIndex = modifiedPartition(array, low, high, order, statistics);
                if(pivotIndex-1 - low + 1 <= 16)
                    insertionSortCall(array, low, pivotIndex-1, order, statistics);
                else
                    modifiedQuickSortCall(array, low, pivotIndex-1, order, statistics);
                if(high - pivotIndex+1 +1 <= 16)
                    insertionSortCall(array, pivotIndex+1, high, order, statistics);
                else
                    modifiedQuickSortCall(array, pivotIndex+1, high, order, statistics);
            }
        }
    }

    public static int modifiedPartition(int[] array, int low, int high, boolean order, Statistics statistics)
    {
        int pivotIndex = findMedianOfThree(array, low, low + (high-low)/2, high, statistics);
        int pivot = array[pivotIndex];

        swap(array, pivotIndex, high, statistics);

        int counter = low;
        if(option == 1)
            System.err.println("Pivot = " + pivot);

        for(int i=low; i<high; i++)
        {
            if(compare(pivot, array[i], statistics) != order)
            {
                if(i != counter)
                    swap(array, i, counter, statistics);
                counter++;
            }
        }

        swap(array, counter, high, statistics);
        return counter;
    }


    public static void swap(int[] array, int a, int b, Statistics statistics)
    {
        if(option == 1)
            System.err.printf("Swapping elements: array[%d] = %d and array[%d] = %d\n", a, array[a], b, array[b]);
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
        statistics.swaps++;
        if(option == 1)
            printArray(array);
    }

    public static boolean compare(int a, int b, Statistics statistics)
    {
        if(option == 1)
            System.err.printf("Comparing elements: %d and %d\n", a, b);
        statistics.comparisons++;

        return a < b;
    }

    public static void printArray(int[] array)
    {
        int n = array.length;

        for(int i=0; i<n; i++)
            System.out.printf("%d, ", array[i]);

        System.out.println();
    }

    public static boolean isSorted(int[] array, boolean order)
    {
        boolean result = true;
        int n = array.length;

        for(int i=0; i<n-1; i++)
        {
            if(array[i] != array[i+1] && (array[i] < array[i+1]) != order)
                result = false;
        }

        return result;
    }

    public static void copyArray(int[] arr1, int[] arr2)
    {
        if(arr1.length != arr2.length)
            return;
        else
            for(int i=0; i<arr1.length; i++)
                arr2[i] = arr1[i];
    }


    public static int findMedianOfThree(int[] array, int a, int b, int c, Statistics statistics)
    {
        if(compare(array[a], array[b], statistics))
        {
            if(compare(array[b], array[c], statistics))
                return b;
            else if(compare(array[c], array[a], statistics))
                return a;
            else
                return c;
        }
        else
        {
            if(compare(array[c], array[b], statistics))
                return b;
            else if(compare(array[a], array[c], statistics))
                return a;
            else
                return c;
        }
    }
}
