import java.util.ArrayList;
import java.util.List;

public class Dijkstra
{
    public void dijkstra(Graph graph, Node source)
    {
        PriorityQueue<Node> pq =  new PriorityQueue<>();
        Edge path[];

        path = new Edge[graph.getNodes().size()];

        for(Node n : graph.getNodes())
            n.setDistance(Integer.MAX_VALUE);

        pq.insert(source);
        int newDistance;
        source.setDistance(0);

        int i = 0;
        while(i < graph.getNodes().size())
        {
            Node node = pq.pop();
            i++;

            if(node == null)
                continue;

            node.setVisited(true);

            Node reachableNode;

            for(Edge e : node.getIncidentEdges())
            {
                if(e.getEndNode().getId() == node.getId())
                    reachableNode = e.getStartNode();
                else
                    reachableNode = e.getEndNode();

                if(!reachableNode.isVisited())
                {
                    newDistance = node.getDistance() + e.getWeight();

                    if(newDistance < reachableNode.getDistance())
                    {
                        reachableNode.setDistance(newDistance);
                        path[reachableNode.getId()] = e;
                    }

                    pq.insert(reachableNode);
                }
            }
        }

        for(Node n : graph.getNodes())
        {
            System.out.println("Destination: node " + n);
            System.out.println("Distance: " + n.getDistance());
            System.out.println("Path: ");

            Edge currentEdge = path[n.getId()];

            while(currentEdge != null && currentEdge.getStartNode().getId() != n.getId())
            {
                System.out.print(currentEdge + "  " + "cost: " + currentEdge.getWeight() + "   ");
                currentEdge = path[currentEdge.getStartNode().getId()];
            }

            System.out.print("\n\n");
        }
    }


    public static void main(String arg[])
    {
        Dijkstra d = new Dijkstra();
        Graph graph = Graph.createGraph(true, true);

        long startTime = System.currentTimeMillis();
        d.dijkstra(graph, graph.getNodes().get(3));
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + elapsedTime);
    }
}