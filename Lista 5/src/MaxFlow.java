import java.io.*;
import java.lang.*;
import java.util.LinkedList;

class MaxFlow
{
    double paths = 0;
    double maxFlow = 0;

    boolean bfs(int rGraph[][], int s, int t, int parent[], int k)
    {
        int pow  = (int)Math.pow(2, k);

        boolean visited[] = new boolean[pow];
        for(int i=0; i<pow; ++i)
            visited[i]=false;

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        while(queue.size()!=0)
        {
            int u = queue.poll();

            for (int v=0; v<k; v++)
            {
                int index = u^(1 << v);
                if (visited[index]==false && rGraph[u][v] > 0)
                {
                    queue.add(index);
                    parent[index] = u;
                    visited[index] = true;
                }
            }
        }

        return (visited[t] == true);
    }


    double edmondsKarp(HyperCube cube, int s, int t)
    {
        int u, v;
        int k = cube.getDimension();


        int pow  = (int)Math.pow(2, k);

        int rGraph[][] = new int[pow][k];

        for (u = 0; u < pow; u++)
            for (v = 0; v < k; v++)
                rGraph[u][v] = cube.getNodes()[u][v];

        int parent[] = new int[pow];

        while(bfs(rGraph, s, t, parent, k))
        {
            paths++;

            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                int index = log2(u^v);
                path_flow = Math.min(path_flow, rGraph[u][index]);
            }

            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];

                int index = log2(u^v);

                rGraph[u][index] -= path_flow;
                rGraph[v][index] += path_flow;
            }

            maxFlow += path_flow;
        }

        return maxFlow;
    }

    public void test() throws IOException
    {
        double n = 250;
        double time;
        int pow;
        StringBuffer data = new StringBuffer();
        BufferedWriter writer = new BufferedWriter(new FileWriter("maxflow_stat.txt", true));
        HyperCube cube;

        for(int k=1; k<=16; k++)
        {
            System.out.println("k = " + k);

            time = System.currentTimeMillis();
            cube = new HyperCube(k);
            pow = (int)Math.pow(2, k);

            for(int i=0; i<n; i++)
                edmondsKarp(cube, 0, pow-1);

            time = System.currentTimeMillis() - time;

            data.append(k + " " + this.maxFlow/n + " " + this.paths/n + " " + time/n + "\n");
            reset();
        }

        writer.write(data.toString());
        writer.close();
    }

    public void reset()
    {
        this.maxFlow = 0;
        this.paths = 0;
    }


    private static int log2(int n)
    {
        return (int)(Math.log(n) / Math.log(2));
    }
}
