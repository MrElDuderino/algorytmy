import java.util.*;

class StrongConnectedComponent
{
    void dfs(Node node)
    {
        node.setVisited(true);
        System.out.print(node + " ");

        for(Node n : node.getAdjacencyList())
        {
            if(!n.isVisited())
                dfs(n);
        }
    }

    void orderNodes(Node node, Stack<Integer> stack)
    {
        node.setVisited(true);

        for(Node n : node.getAdjacencyList())
        {
            if(n.isVisited() == false)
                orderNodes(n, stack);
        }

        stack.push(node.getId());
    }

    void findComponents(Graph graph)
    {
        Stack<Integer> stack = new Stack();

        for(Node n : graph.getNodes())
            n.setVisited(false);

        for(Node n : graph.getNodes())
            if(n.isVisited() == false)
                orderNodes(n, stack);

        Graph transposed = graph.getTransposedGraph();

        for(Node n : transposed.getNodes())
            n.setVisited(false);

        while(!stack.empty())
        {
            int nodeId = stack.pop();

            if(!transposed.getNodeById(nodeId).isVisited())
            {
                dfs(transposed.getNodeById(nodeId));
                System.out.println();
            }
        }
    }


    public static void main(String args[])
    {
        StrongConnectedComponent scc = new StrongConnectedComponent();
        Graph graph = Graph.createGraph(true, false);

        long startTime = System.currentTimeMillis();
        scc.findComponents(graph);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + elapsedTime);
    }
}