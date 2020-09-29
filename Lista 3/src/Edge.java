public class Edge implements Comparable<Edge>
{
    private int weight;
    private Node startNode;
    private Node endNode;

    public Edge()
    {
    }

    public Edge(Node startNode, Node endNode)
    {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Edge(Node startNode, Node endNode, int weight)
    {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public Node getStartNode() { return startNode; }
    public void setStartNode(Node startNode) { this.startNode = startNode; }

    public Node getEndNode() { return endNode; }
    public void setEndNode(Node targetNode) { this.endNode = targetNode; }


    @Override
    public int compareTo(Edge edge)
    {
        if(this.weight - edge.getWeight() > 0)
            return 1;
        else if(this.weight - edge.getWeight() < 0)
            return -1;

        return 0;
    }

    public String toString()
    {
        return "" + startNode + " - " + endNode;
    }
}