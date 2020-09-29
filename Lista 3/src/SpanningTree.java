import java.util.*;
import java.lang.*;

class SpanningTree
{
    class Set
    {
        int label;
        int size;
    }

    int find(Set[] sets, int i)
    {
        if (sets[i].label != i)
            sets[i].label = find(sets, sets[i].label);

        return sets[i].label;
    }

    void Union(Set[] sets, int x, int y)
    {
        int xRepresentative = find(sets, x);
        int yRepresentative = find(sets, y);

        if (sets[xRepresentative].size < sets[yRepresentative].size)
            sets[xRepresentative].label = yRepresentative;
        else if(sets[xRepresentative].size > sets[yRepresentative].size)
            sets[yRepresentative].label = xRepresentative;
        else
        {
            sets[yRepresentative].label = xRepresentative;
            sets[xRepresentative].size++;
        }
    }

    void Kruskal(Graph graph)
    {
        int nodesNo = graph.getNodes().size();

        Edge result[] = new Edge[nodesNo];
        int e = 0;
        int i;

        for (i=0; i<nodesNo; ++i)
            result[i] = new Edge();

        Collections.sort(graph.getEdges());

        Set subsets[] = new Set[nodesNo];

        for(i=0; i<nodesNo; ++i)
            subsets[i]=new Set();

        for (int v = 0; v < nodesNo; ++v)
        {
            subsets[v].label = v;
            subsets[v].size = 0;
        }

        i = 0;

        while(e < nodesNo-1)
        {
            Edge edge;
            edge = graph.getEdges().get(i++);

            int x = find(subsets, edge.getStartNode().getId());
            int y = find(subsets, edge.getEndNode().getId());

            if(x != y)
            {
                result[e++] = edge;
                Union(subsets, x, y);
            }
        }

        int weightsSum = 0;

        for (i = 0; i < e; ++i)
        {
            System.out.println(result[i] + "  cost: " + result[i].getWeight());
            weightsSum += result[i].getWeight();
        }

        System.out.println("Weights sum = " + weightsSum);
    }

    void Prim(Graph graph)
    {
        for(Node n : graph.getNodes())
        {
            n.setVisited(false);
            n.setDistance(Integer.MAX_VALUE);
        }

        graph.getNodes().get(0).setVisited(true);
        graph.getNodes().get(0).setDistance(0);

        PriorityQueue<Node> queue = new PriorityQueue<>();

        for(Node n : graph.getNodes())
            queue.insert(n);

        Edge[] source = new Edge[graph.getNodes().size()];

        while(!queue.empty())
        {
            Node currentNode = queue.pop();
            currentNode.setVisited(true);
            Node reachableNode;

            for(Edge e : currentNode.getIncidentEdges())
            {
                if(e.getEndNode().getId() == currentNode.getId())
                    reachableNode = e.getStartNode();
                else
                    reachableNode = e.getEndNode();

                if(!reachableNode.isVisited())
                {
                    if(reachableNode.getDistance() > e.getWeight())
                    {
                        queue.remove(reachableNode);
                        reachableNode.setDistance(e.getWeight());
                        queue.insert(reachableNode);
                        source[reachableNode.getId()] = e;
                    }
                }
            }
        }

        int weightsSum = 0;

        for (int i = 1; i < graph.getNodes().size(); i++)
        {
            System.out.println(source[i] + "  cost: " + source[i].getWeight());
            weightsSum += source[i].getWeight();
        }

        System.out.println("Weights sum = " + weightsSum);
    }

    public static void main(String[] args)
    {
        SpanningTree st = new SpanningTree();
        Graph graph = Graph.createGraph(false, true);

        if(args.length > 0)
        {
            if(args[0].equals("-p"))
                st.Prim(graph);
            else if(args[0].equals("-k"))
                st.Kruskal(graph);
        }
    }
}