import java.io.IOException;
import java.util.Random;

public class HyperCube
{
    private int dimension;
    private int[][] nodes;
    StringBuffer data = new StringBuffer();


    public HyperCube(int k)
    {
        if(k<1 || k>16)
            System.out.println("Wrong dimension");

        this.dimension = k;
        int pow = (int)Math.pow(2, k);

        data.append("\nparam n := " + (pow-1) + ";\n");
        data.append("param : E : capacity := \n");

        nodes = new int [pow][k];

        for(int i=0; i<pow; i++)
        {
            for(int j=0; j<k; j++)
            {
                if(i < getNeighbour(i, j))
                {
                    int neighbour = getNeighbour(i, j);
                    int capacity = generateCapacity(i, neighbour);
                    data.append(i + " " + neighbour + " " + capacity + "\n");
                    nodes[i][j] = capacity;
                }
            }
        }

        data.append(";\nend;");
    }

    public int getNeighbour(int node, int bit)
    {
        return node^(1 << bit);
    }


    private int generateCapacity(int from, int to)
    {
        int uH = getHammingWeight(from);
        int vH = getHammingWeight(to);

        int l = Math.max(uH>dimension-uH?uH:dimension-uH, vH>dimension-vH?vH:dimension-vH);

        int random = new Random().nextInt();
        if(random < 0)
            random *= -1;

        return 1 + (random % (int)Math.pow(2, l));
    }

    private int getHammingWeight(int value)
    {
        int k =0;
        for(; value != 0; value >>= 1)
            k += value & 1;

        return k;
    }


    public int getBit(int u, int v)
    {
        for(int i=0; i<dimension; i++)
        {
            if(v == getNeighbour(u, i))
                return i;
        }
        return -1;
    }


    public int getDimension()
    {
        return dimension;
    }

    public int[][] getNodes()
    {
        return nodes;
    }


    public static void main(String[] args)
    {
        if(args.length > 0)
        {
            MaxFlow mf = new MaxFlow();

            switch(args[0])
            {
                case "--size":
                    int k = Integer.parseInt(args[1]);
                    long time = System.currentTimeMillis();
                    HyperCube cube = new HyperCube(k);

                    if(args.length>3 && args[2].equals("--glpk"))
                        ModelWriter.writeModelFile("maxflow_template.mod", args[3], cube.data.toString());

                    mf.edmondsKarp(cube, 0, cube.getNodes().length-1);
                    System.out.println("Max flow = " + mf.maxFlow);
                    System.out.println("Time in ms = " + (System.currentTimeMillis() - time));
                    System.out.println("Paths number = " + mf.paths);
                    break;

                case "--test":
                    try{mf.test();}catch(IOException e){e.printStackTrace();}
                    break;
            }
        }
    }
}