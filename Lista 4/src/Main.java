import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Tree tree = null;

        if(args != null && args.length > 1)
        {
            switch (args[1])
            {
                case "bst":
                    tree = new BinarySearchTree();
                    break;
                case "rbt":
                    tree = new RedBlackTree();
                    break;
                case "splay":
                    tree = new SplayTree();
                    break;
                default:
                    System.out.println("Incorrect tree type.");
                    System.exit(1);
            }
        }
        else
            System.exit(1);

        int n;
        String operation;
        String[] command;
        Scanner scan = new Scanner(System.in);

        long startTime;
        System.out.print("Enter number of operations: ");
        n = scan.nextInt();
        scan.nextLine();

        for(int i=0; i<n;  i++)
        {
            operation = scan.nextLine();
            command =  operation.split("\\s+");

            switch(command[0])
            {
                case "insert":
                    if(!Character.isLetter(command[1].charAt(0)))
                        command[1] = command[1].substring(1);
                    if(command[1].length() > 1)
                        if(!Character.isLetter(command[1].charAt(command[1].length() - 1)))
                                command[1] = command[1].substring(0, command[1].length() - 1);
                    if(command[1].length() < 1)
                    {
                        System.out.println("Wrong character type");
                        i--;
                        break;
                    }
                    startTime = System.currentTimeMillis();
                    tree.insert(command[1]);
                    tree.time += System.currentTimeMillis() - startTime;
                    break;

                case "delete":
                    if(command.length>1)
                    {
                        startTime = System.currentTimeMillis();
                        tree.delete(command[1]);
                        tree.time += System.currentTimeMillis() - startTime;
                    }
                    else
                        i--;
                    break;

                case "search":
                    if(command.length>1)
                    {
                        startTime = System.currentTimeMillis();
                        System.out.println(tree.search(command[1]));
                        tree.time += System.currentTimeMillis() - startTime;
                    }
                    else
                        i--;
                    break;

                case "inorder":
                    startTime = System.currentTimeMillis();
                    tree.inorder(tree.root);
                    tree.time += System.currentTimeMillis() - startTime;
                    System.out.println();
                    break;

                case "load":
                    if(command.length>1)
                    {
                        try
                        {
                            startTime = System.currentTimeMillis();
                            tree.load(command[1]);
                            tree.time += System.currentTimeMillis() - startTime;
                        }
                        catch (FileNotFoundException e)
                        {
                            System.out.println("File does not exist.");
                            i--;
                            break;
                        }
                    }
                    else
                        i--;
                    break;

                case "test":
                    if(command.length>1)
                    {
                        try
                        {
                            startTime = System.currentTimeMillis();
                            tree.test(command[1]);
                            tree.time += System.currentTimeMillis() - startTime;
                        }
                        catch (FileNotFoundException e)
                        {
                            System.out.println("File does not exist.");
                            i--;
                            break;
                        }
                    }
                    else
                        i--;
                    break;

                default:
                    i--;
                    System.out.println("Incorrect operation.");
            }
        }

        System.err.println("Comparisons number: " + tree.comparisonCounter);
        System.err.println("Shift number: " + tree.shiftCounter);
        System.err.println("Insertion number: " + tree.insertCounter);
        System.err.println("Deletion number: " + tree.deleteCounter);
        System.err.println("Searching number: " + tree.searchCounter);
        System.err.println("Inorder traversals number: "+ tree.inorderCounter);
        System.err.println("Loads: "+ tree.loadCounter);
        System.err.println("Maximal number of elements: " + tree.getMaxElementsAmountInTree());
        System.err.println("Current number of elements: " + tree.getCurrentElementsAmountInTree());
        System.err.println("Time: " + tree.time);
    }
}
