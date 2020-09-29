import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Tree
{
    Node root = null;

    long insertCounter = 0;
    long deleteCounter = 0;
    long searchCounter = 0;
    long inorderCounter = 0;
    long loadCounter = 0;
    long time = 0;
    long comparisonCounter = 0;
    long shiftCounter = 0;

    public abstract void insert(String newKey);
    public abstract void delete(String value);
    public abstract Node find(String value);

    public void load(String filepath) throws FileNotFoundException
    {
        this.loadCounter++;

        File file = new File(filepath);
        Scanner input = null;
        input = new Scanner(file);

        System.out.println("Loading started");
        while(input.hasNext())
        {
            String word  = input.next();
            word = word.replaceAll("[ ,!?.:]+", "");

            insert(word);
        }

        System.out.println("Loading ended");
    }

    public void test(String filePath) throws FileNotFoundException
    {
        List<String> words = new ArrayList<>();

        this.loadCounter++;

        File file = new File(filePath);
        Scanner input;
        input = new Scanner(file);

        System.out.println("Test started");

        while(input.hasNext())
        {
            String word  = input.next();
            word = word.replaceAll("[ ,!?.:]+", "");

            words.add(word);
            insert(word);
        }

        double divisor = 0;

        for(int i=1; i<this.insertCounter; i++)
        {
            divisor += (Math.log(i) / Math.log(2));
        }

        System.out.println("Operations insert= " + this.comparisonCounter + this.shiftCounter);
        System.out.println("Result insert O(lgn) = " + (this.comparisonCounter + this.shiftCounter) /divisor);

        divisor = 0;

        for(int i=1; i<this.insertCounter; i++)
        {
            divisor += i;
        }

        System.out.println("Result insert O(n) = " + (this.comparisonCounter + this.shiftCounter) /divisor);

        this.comparisonCounter = 0;
        this.shiftCounter = 0;

        for(String s: words)
            search(s);

        divisor = 0;

        for(int i=1; i<this.insertCounter; i++)
        {
            divisor += (Math.log(i) / Math.log(2));
        }

        System.out.println("Operations search= " + this.comparisonCounter + this.shiftCounter);
        System.out.println("Result search O(lgn) = " + (this.comparisonCounter + this.shiftCounter) /divisor);

        divisor = 0;

        for(int i=1; i<this.insertCounter; i++)
        {
            divisor += i;
        }

        System.out.println("Result search O(n) = " + (this.comparisonCounter + this.shiftCounter) /divisor);

        this.comparisonCounter = 0;
        this.shiftCounter = 0;

        for (String s: words)
            delete(s);

        divisor = 0;

        for(int i=1; i<this.insertCounter; i++)
        {
            divisor += (Math.log(i) / Math.log(2));
        }

        System.out.println("Operations delete = " + this.comparisonCounter + this.shiftCounter);
        System.out.println("Result delete O(lgn) = " + (this.comparisonCounter + this.shiftCounter) /divisor);

        divisor = 0;

        for(int i=1; i<this.insertCounter; i++)
        {
            divisor += i;
        }

        System.out.println("Result delete O(n) = " + (this.comparisonCounter + this.shiftCounter) /divisor);

        System.out.println("Test ended");
    }


    public void inorder(Node root)
    {
        this.inorderCounter++;

        if(root != null)
        {
            inorder(root.left);
            System.out.print(root.value + " ");
            inorder(root.right);
        }
    }

    public boolean search(String value)
    {
        Node nodeToFind = find(value);

        if (nodeToFind == null)
            return false;

        return true;
    }

    public long getMaxElementsAmountInTree()
    {
        return insertCounter;
    }

    public long getCurrentElementsAmountInTree()
    {
        return insertCounter - deleteCounter;
    }
}
