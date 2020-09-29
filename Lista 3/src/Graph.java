import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph
{

    private List<Node> nodes;
    private List<Edge> edges;
    private boolean directed;

    public Graph()
    {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Graph(List<Node> nodes, List<Edge> edges, boolean directed)
    {
        this.nodes = nodes;
        this.edges = edges;
        this.directed = directed;

        for(Edge e: edges)
        {
            nodes.get(e.getStartNode().getId()).getIncidentEdges().add(e);
            if(!directed)
                nodes.get(e.getEndNode().getId()).getIncidentEdges().add(e);

            nodes.get(e.getStartNode().getId()).addAdjacentNode(e.getEndNode());
            if(!directed)
                nodes.get(e.getEndNode().getId()).addAdjacentNode(e.getStartNode());
        }
    }

    public Graph getTransposedGraph()
    {
        List<Node> transposedNodes = new ArrayList<>();
        List<Edge> transposedEdges = new ArrayList<>();

        for(Node n : this.nodes)
        {
            Node node = new Node(n.getId());
            node.setVisited(n.isVisited());
            node.setComponentId(n.getComponentId());
            transposedNodes.add(node);
        }

        for(Edge e : this.edges)
        {
            Edge edge = new Edge();
            edge.setStartNode(transposedNodes.get(e.getEndNode().getId()));
            edge.setEndNode(transposedNodes.get(e.getStartNode().getId()));
            edge.setWeight(e.getWeight());
            transposedEdges.add(edge);
        }

        return new Graph(transposedNodes, transposedEdges, true);
    }

    public static Graph createGraph(boolean directed, boolean weight)
    {
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        int n, m;
        int u, v, w = 0;
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        n = scan.nextInt();

        for(int i=0; i<n; i++)
            nodes.add(new Node(i));

        System.out.print("\nEnter number of edges: ");
        m = scan.nextInt();

        for(int i=0; i<m; i++)
        {
            u = scan.nextInt();
            v = scan.nextInt();
            if(weight)
                w = scan.nextInt();

            if(u == v)
            {
                System.out.println("No self loops");
                i--;
                continue;
            }
            if(u >= n || v >= n || u < 0 || v < 0)
            {
                System.out.println("Incorrect ids of nodes.");
                i--;
                continue;
            }

            if(weight)
                edges.add(new Edge(nodes.get(u), nodes.get(v), w));
            else
                edges.add(new Edge(nodes.get(u), nodes.get(v)));
        }

        Graph graph = new Graph(nodes, edges, directed);
        return graph;
    }

    public void addNode(int id)
    {
        nodes.add(new Node(id));
    }

    public void addEdge(int u, int v, int w)
    {
        Edge e = new Edge(nodes.get(u), nodes.get(v), w);
        addEdge(e);
    }

    public void addEdge(int u, int v)
    {
        Edge e = new Edge(nodes.get(u), nodes.get(v));
        addEdge(e);
    }

    public void addEdge(Edge e)
    {
        nodes.get(e.getStartNode().getId()).getIncidentEdges().add(e);
        if(!directed)
            nodes.get(e.getEndNode().getId()).getIncidentEdges().add(e);

        nodes.get(e.getStartNode().getId()).addAdjacentNode(e.getEndNode());
        if(!directed)
            nodes.get(e.getEndNode().getId()).addAdjacentNode(e.getStartNode());

        edges.add(e);
    }

    public List<Node> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<Node> nodes)
    {
        this.nodes = nodes;
    }

    public List<Edge> getEdges()
    {
        return edges;
    }

    public void setEdges(List<Edge> edges)
    {
        this.edges = edges;
    }

    public boolean isDirected()
    {
        return directed;
    }

    public void setDirected(boolean directed)
    {
        this.directed = directed;
    }

    public Node getNodeById(int id)
    {
        for(Node n : nodes)
            if(n.getId() == id)
                return n;

        return null;
    }
}