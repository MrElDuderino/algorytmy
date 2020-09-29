public class BinarySearchTree extends Tree
{
    public void insert(String newKey)
    {
        this.insertCounter++;

        Node newNode = new Node(newKey);
        Node parentNode = null;
        Node currentNode = this.root;

        while(currentNode != null)
        {
            parentNode = currentNode;

            if(newNode.value.compareTo(currentNode.value) < 0)
            {
                this.comparisonCounter++;
                this.shiftCounter++;
                currentNode = currentNode.left;
            }
            else
            {
                currentNode = currentNode.right;
                this.comparisonCounter++;
                this.shiftCounter++;
            }
        }

        newNode.parent = parentNode;
        if(parentNode == null)
            this.root = newNode;

        else if(newNode.value.compareTo(parentNode.value) < 0)
        {
            parentNode.left = newNode;
            this.shiftCounter++;
        }
        else
        {
            parentNode.right = newNode;
            this.shiftCounter++;
        }
    }


    public void delete(String value)
    {
        this.deleteCounter++;

        Node nodeToDelete = find(value);

        if(nodeToDelete != null)
        {
            this.comparisonCounter++;
            this.shiftCounter++;

            if(nodeToDelete.left == null)
                transplant(nodeToDelete, nodeToDelete.right);

            else if(nodeToDelete.right == null)
                transplant(nodeToDelete, nodeToDelete.left);

            else
            {
                Node nodeToDeleteSuccessor = treeMinimum(nodeToDelete.right);

                this.comparisonCounter++;

                if(nodeToDeleteSuccessor.parent != nodeToDelete)
                {
                    transplant(nodeToDeleteSuccessor, nodeToDeleteSuccessor.right);
                    nodeToDeleteSuccessor.right = nodeToDelete.right;
                    nodeToDeleteSuccessor.right.parent = nodeToDeleteSuccessor;
                }

                this.shiftCounter++;
                transplant(nodeToDelete, nodeToDeleteSuccessor);
                nodeToDeleteSuccessor.left = nodeToDelete.left;
                nodeToDeleteSuccessor.left.parent = nodeToDeleteSuccessor;
            }
        }
    }


    public Node find(String value)
    {
        this.searchCounter++;
        this.comparisonCounter++;

        Node currentNode = this.root;

        while(currentNode != null && !value.equals(currentNode.value))
        {
            this.shiftCounter++;

            if(value.compareTo(currentNode.value) < 0)
                currentNode = currentNode.left;
            else
                currentNode = currentNode.right;
        }

        return currentNode;
    }

    private void transplant(Node nodeU, Node nodeV)
    {
        this.comparisonCounter++;
        this.shiftCounter++;

        if(nodeU.parent == null)
            this.root = nodeV;
        else if(nodeU == nodeU.parent.left)
            nodeU.parent.left = nodeV;
        else
            nodeU.parent.right = nodeV;

        if (nodeV != null)
            nodeV.parent = nodeU.parent;
    }


    private Node treeMinimum(Node currentNode)
    {
        while(currentNode.left != null)
        {
            currentNode = currentNode.left;
            this.comparisonCounter++;
        }
        return currentNode;
    }
}
