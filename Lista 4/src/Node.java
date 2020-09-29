public class Node
{
    String value;
    Node left, right;
    Node parent;
    Color color;

    Node(String value)
    {
        this.value = value;
        left = right = parent = null;
        color = Color.BLACK;
    }
}
