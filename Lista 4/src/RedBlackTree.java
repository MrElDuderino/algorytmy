public class RedBlackTree extends Tree
{
    Node nil = new Node(null);

    RedBlackTree()
    {
        root = nil;
        root.parent = nil;
    }

    public void insert(String value)
    {
        this.insertCounter++;

        Node newNode = new Node(value);
        Node parentNode = nil;
        Node currentNode = this.root;

        while(currentNode != nil)
        {
            this.comparisonCounter++;
            this.shiftCounter++;

            parentNode = currentNode;

            if(newNode.value.compareTo(currentNode.value) < 0)
                currentNode = currentNode.left;
            else
                currentNode = currentNode.right;
        }

        newNode.parent = parentNode;

        if(parentNode == nil)
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


        newNode.left = nil;
        newNode.right = nil;
        newNode.color = Color.RED;
        insertFixUp(newNode);
    }


    private void insertFixUp(Node newNode)
    {
        while(newNode.parent.color == Color.RED)
        {
            this.shiftCounter++;
            this.comparisonCounter++;

            if(newNode.parent == newNode.parent.parent.left)
            {
                Node uncle = newNode.parent.parent.right;

                if(uncle.color == Color.RED)
                {
                    newNode.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    newNode = newNode.parent.parent;
                }
                else
                {
                    if(newNode == newNode.parent.right)
                    {
                        newNode = newNode.parent;
                        leftRotate(newNode);
                    }

                    newNode.parent.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    rightRotate(newNode.parent.parent);
                }
            }
            else
            {
                Node uncle = newNode.parent.parent.left;
                if(uncle.color == Color.RED)
                {
                    newNode.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    newNode = newNode.parent.parent;
                }
                else
                {
                    if(newNode == newNode.parent.left)
                    {
                        newNode = newNode.parent;
                        rightRotate(newNode);
                    }

                    newNode.parent.color = Color.BLACK;
                    newNode.parent.parent.color = Color.RED;
                    leftRotate(newNode.parent.parent);
                }
            }
        }

        this.root.color = Color.BLACK;
    }


    private void leftRotate(Node nodeToRotate)
    {
        Node helpNode = nodeToRotate.right;
        nodeToRotate.right = helpNode.left;

        this.shiftCounter++;
        this.comparisonCounter++;

        if(helpNode.left != nil)
            helpNode.left.parent = nodeToRotate;

        helpNode.parent = nodeToRotate.parent;

        if(nodeToRotate.parent == nil)
            this.root = helpNode;

        else if(nodeToRotate == nodeToRotate.parent.left)
            nodeToRotate.parent.left = helpNode;
        else
            nodeToRotate.parent.right = helpNode;

        helpNode.left = nodeToRotate;
        nodeToRotate.parent = helpNode;
    }

    private void rightRotate(Node nodeToRotate)
    {
        Node helpNode = nodeToRotate.left;
        nodeToRotate.left = helpNode.right;

        this.shiftCounter++;
        this.comparisonCounter++;

        if(helpNode.right != nil)
            helpNode.right.parent = nodeToRotate;

        helpNode.parent = nodeToRotate.parent;

        if(nodeToRotate.parent == nil)
            this.root = helpNode;
        else if(nodeToRotate == nodeToRotate.parent.left)
            nodeToRotate.parent.left = helpNode;
        else
            nodeToRotate.parent.right = helpNode;

        helpNode.right = nodeToRotate;
        nodeToRotate.parent = helpNode;
    }


    public void delete(String value)
    {
        this.deleteCounter++;
        Node nodeToDelete = find(value);

        if(nodeToDelete == nil)
            return;

        Node successorNode = nodeToDelete;
        Node replaceNode;
        Color successorNodeOriginalColor = successorNode.color;

        this.shiftCounter++;
        this.comparisonCounter++;

        if(nodeToDelete.left == nil)
        {
            replaceNode = nodeToDelete.right;
            transplant(nodeToDelete, nodeToDelete.right);
        }
        else if(nodeToDelete.right == nil)
        {
            replaceNode = nodeToDelete.left;
            transplant(nodeToDelete, nodeToDelete.left);
        }
        else
        {
            successorNode = treeMinimum(nodeToDelete.right);
            successorNodeOriginalColor = successorNode.color;
            replaceNode = successorNode.right;
            if(successorNode.parent == nodeToDelete)
            {
                replaceNode.parent = successorNode;
            }
            else
            {
                transplant(successorNode, successorNode.right);
                successorNode.right = nodeToDelete.right;
                successorNode.right.parent = successorNode;
            }

            transplant(nodeToDelete, successorNode);
            successorNode.left = nodeToDelete.left;
            successorNode.left.parent = successorNode;
            successorNode.color = nodeToDelete.color;
        }

        this.shiftCounter++;
        this.comparisonCounter++;

        if(successorNodeOriginalColor == Color.BLACK)
            deleteFixUp(replaceNode);
    }


    private void deleteFixUp(Node replaceNode)
    {
        Node brotherNode;

        while(replaceNode != this.root && replaceNode.color == Color.BLACK)
        {
            this.shiftCounter++;
            this.comparisonCounter++;

            if(replaceNode == replaceNode.parent.left)
            {
                brotherNode = replaceNode.parent.right;

                if(brotherNode.color == Color.RED)
                {
                    brotherNode.color = Color.BLACK;
                    replaceNode.parent.color = Color.RED;
                    leftRotate(replaceNode.parent);
                    brotherNode = replaceNode.parent.right;
                }
                if(brotherNode.left.color == Color.BLACK && brotherNode.right.color == Color.BLACK)
                {
                    brotherNode.color = Color.RED;
                    replaceNode = replaceNode.parent;
                }
                else
                {
                    if (brotherNode.right.color == Color.BLACK)
                    {
                        brotherNode.left.color = Color.BLACK;
                        brotherNode.color = Color.RED;
                        rightRotate(brotherNode);
                        brotherNode = replaceNode.parent.right;
                    }
                    brotherNode.color = replaceNode.parent.color;
                    replaceNode.parent.color = Color.BLACK;
                    brotherNode.right.color = Color.BLACK;
                    leftRotate(replaceNode.parent);
                    replaceNode = this.root;
                }
            }

            else
            {
                brotherNode = replaceNode.parent.left;

                if(brotherNode.color == Color.RED)
                {
                    brotherNode.color = Color.BLACK;
                    replaceNode.parent.color = Color.RED;
                    rightRotate(replaceNode.parent);
                    brotherNode = replaceNode.parent.left;
                }
                if(brotherNode.right.color == Color.BLACK && brotherNode.left.color == Color.BLACK)
                {
                    brotherNode.color = Color.RED;
                    replaceNode = replaceNode.parent;
                }
                else
                {
                    if (brotherNode.left.color == Color.BLACK)
                    {
                        brotherNode.right.color = Color.BLACK;
                        brotherNode.color = Color.RED;
                        leftRotate(brotherNode);
                        brotherNode = replaceNode.parent.left;
                    }

                    brotherNode.color = replaceNode.parent.color;
                    replaceNode.parent.color = Color.BLACK;
                    brotherNode.left.color = Color.BLACK;
                    rightRotate(replaceNode.parent);
                    replaceNode = this.root;
                }
            }
        }

        replaceNode.color = Color.BLACK;
    }

    public boolean search(String value)
    {
        Node nodeToFind = find(value);
        if (nodeToFind == nil)
            return false;
        return true;
    }


    public Node find(String value)
    {
        Node currentNode = this.root;

        this.searchCounter++;

        while(currentNode != nil && !value.equals(currentNode.value))
        {
            this.shiftCounter++;
            this.comparisonCounter++;

            if (value.compareTo(currentNode.value) < 0)
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

        if(nodeU.parent == nil)
            this.root = nodeV;
        else if(nodeU == nodeU.parent.left)
            nodeU.parent.left = nodeV;
        else
            nodeU.parent.right = nodeV;

        nodeV.parent = nodeU.parent;
    }

    private Node treeMinimum(Node currentNode)
    {
        while(currentNode.left != nil)
        {
            currentNode = currentNode.left;
            this.comparisonCounter++;
        }
        return currentNode;
    }

    public void inorder(Node root)
    {
        if(root != nil)
        {
            inorder(root.left);
            System.out.print(root.value + " ");
            inorder(root.right);
        }
    }
}
