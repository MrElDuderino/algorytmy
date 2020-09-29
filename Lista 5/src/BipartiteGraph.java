import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BipartiteGraph
{
    private int k;
    private int pow;
    private int i;
    private Node[] v1;
    private Node[] v2;
    private long matching;
    StringBuffer data = new StringBuffer();


    public BipartiteGraph(int k, int i)
    {
        this.k = k;
        this.i = i;
        this.pow = (int)Math.pow(2, k);

        v1 = new Node[pow];
        v2 = new Node[pow];

        data.append("\nparam n := " + (2*pow-1) + ";\n");
        data.append("param : E : w := \n");

        for (int j = 0; j < v1.length; j++)
        {
            v1[j] = new Node(j);
            v2[j] = new Node(j);
        }

        for(int j = 0; j < v1.length; j++)
            generateNeighbours(j);

        data.append(";\nend;");
    }


    private void generateNeighbours(int u)
    {
        boolean[] taken = new boolean[v2.length];
        int randomId;
        Random rand = new Random(System.nanoTime());
        int j = 0;


        while(j < this.i)
        {
            randomId = rand.nextInt() % v2.length;
            if(randomId < 0)
                randomId *= -1;

            if(!taken[randomId])
            {
                taken[randomId] = true;
                v1[u].neighbours.add(v2[randomId]);
                v2[randomId].neighbours.add(v1[u]);

                data.append(u + " " + (randomId+pow) + " " + 1 + "\n");
                j++;
            }
        }
    }

    public void HopcroftKarp()
    {
        boolean result;
        do
        {
            BFS();
            result = DFS();
        }
        while(result);

        for(Node n: v1)
            if(n.matched != null)
                this.matching++;
    }

    private void BFS()
    {
        for(Node n: v1)
            n.distance = -1;

        Queue<Integer> q = new LinkedList<>();

        for(Node n: v1)
        {
            if(n.matched == null)
            {
                q.offer(n.id);
                n.distance = 0;
            }
        }

        while(!q.isEmpty())
        {
            Node current = v1[q.poll()];

            for(Node n: current.neighbours)
            {
                if(n.matched != null && n.matched.distance == -1)
                {
                    n.matched.distance = current.distance + 1;
                    q.offer(n.matched.id);
                }
            }
        }
    }


    private boolean DFS()
    {
        for(Node n: v1)
            n.visited = false;

        boolean result = false;

        for(Node n: v1)
        {
            if(n.matched == null)
            {
                if(!result)
                    result = DFS(n);
                else
                    DFS(n);
            }
        }

        return result;
    }

    private boolean DFS(Node node)
    {
        node.visited = true;

        for(Node n: node.neighbours)
        {
            if(n.matched == null)
            {
                n.matched = node;
                node.matched = n;
                return true;
            }
            else
            {
                Node z = n.matched;
                if(!z.visited && z.distance == node.distance + 1)
                {
                    if(DFS(z))
                    {
                        n.matched = node;
                        node.matched = n;
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void test() throws IOException
    {
        StringBuffer data = new StringBuffer();
        BipartiteGraph graph;
        FileWriter writer = new FileWriter("matching_stat.txt");
        double time;
        int n = 1000;

        for(int k=3; k<=10; k++)
        {
            for(int i=1; i<=k; i++)
            {
                graph = new BipartiteGraph(k, i);
                time = System.nanoTime();

                for(int j=0; j<n; j++)
                    graph.HopcroftKarp();

                time = System.nanoTime() - time;
                data.append(k + " " + i + " " + graph.matching/n + " " + time/n + "\n");
                this.matching = 0;
            }
        }

        writer.write(data.toString());
        writer.close();
    }

    private class Node
    {
        private int id;
        private int distance;
        private boolean visited;
        private ArrayList<Node> neighbours;
        private Node matched;

        public Node(int id)
        {
            this.id = id;
            this.neighbours = new ArrayList<>();
        }
    }

    public static void main(String[] args)
    {
        if(args.length > 3)
        {
            BipartiteGraph bipartiteGraph = null;

            if(args[0].equals("--size") && args[2].equals("--degree"))
            {
                int k = Integer.parseInt(args[1]);
                int i = Integer.parseInt(args[3]);

                if(i>k)
                    System.exit(0);

                long time = System.nanoTime();
                bipartiteGraph = new BipartiteGraph(k, i);
                bipartiteGraph.HopcroftKarp();
//                bipartiteGraph.printEdges();
                System.out.println("Maximal matching = " + bipartiteGraph.matching);
                System.out.println("Time in ns = " + (System.nanoTime() - time));
            }
            if(args.length > 5 && args[4].equals("--glpk"))
            {
                ModelWriter.writeModelFile("match_template.mod", args[5], bipartiteGraph.data.toString());
            }
        }
    }
}