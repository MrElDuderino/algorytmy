import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>
{
    private int id;
    private List<Node> adjacencyList;
    private List<Edge> incidentEdges;
    private boolean visited = false;
    private int componentId;
    private int distance;

    public Node(int id)
    {
      this.id = id;
      this.adjacencyList = new ArrayList<>();
      this.incidentEdges = new ArrayList<>();
    }

    public void addAdjacentNode(Node node)
    {
      this.adjacencyList.add(node);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id) { this.id = id; }
    public int getComponentId() { return componentId; }
    public void setComponentId(int componentId) { this.componentId = componentId; }
    public List<Node> getAdjacencyList() { return adjacencyList; }
    public void setAdjacencyList(List<Node> adjacencyList) { this.adjacencyList = adjacencyList; }
    public List<Edge> getIncidentEdges()
    {
      return incidentEdges;
    }

    public void setIncidentEdges(List<Edge> incidentEdges)
    {
      this.incidentEdges = incidentEdges;
    }
    public boolean isVisited() { return visited; }
    public void setVisited(boolean visited) { this.visited = visited; }
    public int getDistance() {return distance;}
    public void setDistance(int distance){this.distance = distance;}

    public String toString()
    {
        return "" + getId();
    }


    @Override
    public int compareTo(Node node)
    {
        return this.distance - node.getDistance();
    }
}
