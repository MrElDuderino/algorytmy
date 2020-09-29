import java.util.*;


public class PriorityQueue<T extends Comparable<T>>
{
    private int heapSize = 0;
    private Map<T, TreeSet<Integer>> map = new HashMap<>();
    private Map<Integer, TreeSet<Integer>> qElementsMap = new HashMap<>();
    private List<T> heap;

    public PriorityQueue()
    {
        heap = new ArrayList<>(1);
    }


    public void insert(T element)
    {
        if(heapSize >= heap.size())
            heap.add(element);
        else
            heap.set(heapSize, element);

        mapAdd(element, heapSize);

        if(element instanceof QueueElement)
            qElementsMapAdd((QueueElement) element, heapSize);

        heapifyUp(heapSize);
        heapSize++;
    }


    private void mapAdd(T value, int index)
    {
        TreeSet<Integer> set = map.get(value);

        if(set == null)
        {
            set = new TreeSet<>();
            set.add(index);
            map.put(value, set);
        }
        else
            set.add(index);
    }

    private void qElementsMapAdd(QueueElement element, int index)
    {
        TreeSet<Integer> set = qElementsMap.get(element.value);

        if(set == null)
        {
            set = new TreeSet<>();
            set.add(index);
            qElementsMap.put(element.value, set);
        }
        else
            set.add(index);
    }

    public boolean empty()
    {
        return heapSize == 0;
    }

    public void top()
    {
        if (empty())
        {
            System.out.println();
            return;
        }

        System.out.println(heap.get(0));
    }

    public T pop()
    {
        return removeAt(0);
    }

    public void print()
    {
        for(int i=0; i<heapSize; i++)
            System.out.println(heap.get(i));
    }

    private void heapifyUp(int k)
    {
        int parent = (k-1) / 2;

        while(k > 0 && lowerPriority(k, parent))
        {
            swap(parent, k);
            k = parent;

            parent = (k-1) / 2;
        }
    }

    private void heapifyDown(int k)
    {
        while(true)
        {
            int left  = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = left;

            if(right < heapSize && lowerPriority(right, left))
                smallest = right;

            if(left >= heapSize || lowerPriority(k, smallest))
                break;

            swap(smallest, k);
            k = smallest;
        }
    }

    private void swap(int i, int j)
    {
        T i_elem = heap.get(i);
        T j_elem = heap.get(j);

        heap.set(i, j_elem);
        heap.set(j, i_elem);

        mapSwap(i_elem, j_elem, i, j);
        if(i_elem instanceof QueueElement)
            qElementsMapSwap((QueueElement) i_elem, (QueueElement) j_elem, i, j);
    }


    private boolean lowerPriority(int i, int j)
    {
        T node1 = heap.get(i);
        T node2 = heap.get(j);

        return node1.compareTo(node2) <0;
    }


    public void remove(T element)
    {
        Integer index = mapGet(element);
        if(index != null)
            removeAt(index);
    }


    private T removeAt(int i)
    {
        if(empty())
            return null;

        heapSize--;
        T removed_data = heap.get(i);
        swap(i, heapSize);

        heap.set(heapSize, null);
        mapRemove(removed_data, heapSize);

        if(removed_data instanceof QueueElement)
            qElementsMapRemove(((QueueElement) removed_data).value, heapSize);

        if(i == heapSize)
            return removed_data;

        T elem = heap.get(i);

        heapifyDown(i);

        if(heap.get(i).equals(elem))
            heapifyUp(i);

        return removed_data;
    }


    private void mapRemove(T value, int index)
    {
        TreeSet<Integer> set = map.get(value);
        set.remove(index);
        if(set.size() == 0)
            map.remove(value);
    }


    private void qElementsMapRemove(int value, int index)
    {
        TreeSet<Integer> set = qElementsMap.get(value);
        set.remove(index);
        if(set.size() == 0)
            qElementsMap.remove(value);
    }

    private Integer mapGet(T value)
    {
        TreeSet<Integer> set = map.get(value);
        if(set != null)
            return set.last();
        return null;
    }

    private TreeSet<Integer> qElementsMapGet(int value)
    {
        TreeSet<Integer> set = qElementsMap.get(value);
        if(set != null)
            return set;
        return null;
    }

    private void mapSwap(T val1, T val2, int val1Index, int val2Index)
    {
        Set<Integer> set1 = map.get(val1);
        Set<Integer> set2 = map.get(val2);

        set1.remove(val1Index);
        set2.remove(val2Index);

        set1.add(val2Index);
        set2.add(val1Index);
    }

    private void qElementsMapSwap(QueueElement val1, QueueElement val2, int val1Index, int val2Index)
    {
        Set<Integer> qeSet1 = qElementsMap.get(val1.value);
        Set<Integer> qeSet2 = qElementsMap.get(val2.value);

        qeSet1.remove(val1Index);
        qeSet2.remove(val2Index);

        qeSet1.add(val2Index);
        qeSet2.add(val1Index);
    }

    public void priority(int x, int p)
    {
        int[] indexes = new int[qElementsMapGet(x).size()];
        int j = 0;

        for(int i : qElementsMapGet(x))
        {
            indexes[j] = i;
            j++;
        }

        for(int i : indexes)
        {
            QueueElement qe = (QueueElement) heap.get(i);
            if(p < qe.priority)
            {
                qe.priority = p;
                heapifyUp(i);
            }
        }
    }

    static class QueueElement implements Comparable<QueueElement>
    {
        int value;
        int priority;

        public QueueElement(int value, int priority)
        {
            this.value = value;
            this.priority = priority;
        }

        @Override
        public int compareTo(QueueElement qe)
        {
            return this.priority - qe.priority;
        }

        public String toString()
        {
            return value + " " + priority;
        }
    }

    public static void main(String[] args)
    {
        PriorityQueue<QueueElement> queue = new PriorityQueue<>();

        int m, x, p;
        String operation, command;
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter number of operations: ");
        m = scan.nextInt();
        scan.nextLine();

        for(int i=0; i<m;  i++)
        {
            operation = scan.nextLine();
            command = operation.split("\\s+")[0];

            switch(command)
            {
                case "insert":
                    x = Integer.parseInt(operation.split("\\s+")[1]);
                    p = Integer.parseInt(operation.split("\\s+")[2]);
                    QueueElement qe = new QueueElement(x, p);
                    queue.insert(qe);
                    break;
                case "empty":
                    System.out.println(queue.empty());
                    break;
                case "top":
                    queue.top();
                    break;
                case "pop":
                    System.out.println(queue.pop());
                    break;
                case "priority":
                    x = Integer.parseInt(operation.split("\\s+")[1]);
                    p = Integer.parseInt(operation.split("\\s+")[2]);
                    queue.priority(x, p);
                    break;
                case "print":
                    queue.print();
                    break;
                default:
                    i--;
                    System.out.println("Incorrect operation.");
            }
        }
    }
}