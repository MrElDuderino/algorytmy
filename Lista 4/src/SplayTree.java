class SplayTree extends Tree
{
    public void insert(String newKey)
    {
        this.insertCounter++;

        Node currentNode = this.root;
        Node parentNode = null;

        while(currentNode != null)
        {
            this.shiftCounter++;
            this.comparisonCounter++;

            parentNode = currentNode;

            this.shiftCounter++;

            if(newKey.compareTo(parentNode.value) < 0)
                currentNode = currentNode.left;
            else
                currentNode = currentNode.right;
        }

        this.shiftCounter++;
        currentNode = new Node(newKey);
        currentNode.parent = parentNode;

        this.shiftCounter++;
        this.comparisonCounter++;

        if(parentNode == null)
            root = currentNode;
        else if(newKey.compareTo(parentNode.value) < 0)
            parentNode.left = currentNode;
        else
            parentNode.right = currentNode;

        Splay(currentNode);
    }


    private void Splay(Node nodeToSplay)
    {
        while(nodeToSplay.parent != null)
        {
            Node parent = nodeToSplay.parent;
            Node grandParent = parent.parent;

            this.comparisonCounter++;

            if(grandParent == null)
            {
                if (nodeToSplay == parent.left)
                    zig(nodeToSplay, parent);
                else
                    zag(nodeToSplay, parent);
            }
            else
            {
                this.comparisonCounter++;

                if(nodeToSplay == parent.left)
                {
                    if(parent == grandParent.left)
                    {
                        zig(parent, grandParent);
                        zig(nodeToSplay, parent);
                    }
                    else
                    {
                        zig(nodeToSplay, nodeToSplay.parent);
                        zag(nodeToSplay, nodeToSplay.parent);
                    }
                }

                else
                {
                    this.comparisonCounter++;
                    if(parent == grandParent.left)
                    {
                        zag(nodeToSplay, nodeToSplay.parent);
                        zig(nodeToSplay, nodeToSplay.parent);
                    }
                    else
                    {
                        zag(parent, grandParent);
                        zag(nodeToSplay, parent);
                    }
                }
            }
        }

        this.shiftCounter++;
        root = nodeToSplay;
    }

    private void zig(Node child, Node parentNode)
    {
        this.comparisonCounter++;

        if((child == null) || (parentNode == null) || (parentNode.left != child) || (child.parent != parentNode))
            throw new RuntimeException("null");

        if(parentNode.parent != null)
        {
            if (parentNode == parentNode.parent.left)
                parentNode.parent.left = child;
            else
                parentNode.parent.right = child;
        }

        if(child.right != null)
            child.right.parent = parentNode;

        this.shiftCounter+=3;
        this.comparisonCounter+=3;

        child.parent = parentNode.parent;
        parentNode.parent = child;
        parentNode.left = child.right;
        child.right = parentNode;
    }


    private void zag(Node child, Node parentNode)
    {
        this.comparisonCounter++;

        if ((child == null) || (parentNode == null) || (parentNode.right != child) || (child.parent != parentNode))
            throw new RuntimeException("null");


        if (parentNode.parent != null)
        {
            if (parentNode == parentNode.parent.left)
                parentNode.parent.left = child;
            else
                parentNode.parent.right = child;
        }


        if(child.left != null)
            child.left.parent = parentNode;

        this.shiftCounter+=3;
        this.comparisonCounter+=3;

        child.parent = parentNode.parent;
        parentNode.parent = child;
        parentNode.right = child.left;
        child.left = parentNode;
    }


    public void delete(String value)
    {
        Node nodeToDelete = find(value);

        if(nodeToDelete == null)
            return;

        this.deleteCounter++;

        this.comparisonCounter++;
        this.shiftCounter++;

        if((nodeToDelete.left != null) && (nodeToDelete.right !=null))
        {
            Node predecessor = nodeToDelete.left;

            while(predecessor.right!=null)
            {
                this.comparisonCounter++;
                predecessor = predecessor.right;
            }

            Splay(predecessor);

            predecessor.right = nodeToDelete.right;
            nodeToDelete.right.parent = predecessor;
        }

        else if(nodeToDelete.right != null)
        {
            nodeToDelete.right.parent = null;
            root = nodeToDelete.right;
        }
        else if(nodeToDelete.left !=null)
        {
            nodeToDelete.left.parent = null;
            root = nodeToDelete.left;
        }
        else
            root = null;

        nodeToDelete.parent = null;
        nodeToDelete.left = null;
        nodeToDelete.right = null;
    }


    public Node find(String value)
    {
        Node prevNode = null;
        Node currentNode = root;

        this.searchCounter++;

        while(currentNode != null)
        {
            prevNode = currentNode;

            this.comparisonCounter++;

            if(value.compareTo(currentNode.value) > 0)
            {
                this.shiftCounter++;
                currentNode = currentNode.right;
            }

            else if(value.compareTo(currentNode.value) < 0)
            {
                this.shiftCounter++;
                currentNode = currentNode.left;
            }
            else if(value.compareTo(currentNode.value) == 0)
            {
                Splay(currentNode);
                return currentNode;
            }
        }

        this.comparisonCounter++;
        if(prevNode != null)
        {
            Splay(prevNode);
            return null;
        }

        return null;
    }
}