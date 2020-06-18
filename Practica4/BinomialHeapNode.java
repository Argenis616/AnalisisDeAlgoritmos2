/*
 * Java Program to Implement Binomial Heap
 */

/* Class BinomialHeapNode */
class BinomialHeapNode
{
    int llave, grado;
    BinomialHeapNode padre;
    BinomialHeapNode sHermano;
    BinomialHeapNode hijo;
 
    /* Constructor */
    public BinomialHeapNode(int k)
    {
        llave = k;
        grado = 0;
        padre = null;
        sHermano = null;
        hijo = null;
    }
    /* Function reverse */
    public BinomialHeapNode reverse(BinomialHeapNode sibl)
    {
            BinomialHeapNode ret;
            if (sHermano != null)
                ret = sHermano.reverse(this);
            else
                ret = this;
            sHermano = sibl;
            return ret;
    }
    /* Function to find min node */
    public BinomialHeapNode findMinNode()
    {
            BinomialHeapNode x = this, y = this;
            int min = x.llave;

            while (x != null) {
                if (x.llave < min) {
                    y = x;
                    min = x.llave;
                }
                x = x.sHermano;
            }

            return y;
    }
    /* Function to find node with llave value */
    public BinomialHeapNode findANodeWithKey(int value)
    {
            BinomialHeapNode temp = this, node = null;

            while (temp != null)
            {
                if (temp.llave == value)
                {
                    node = temp;
                    break;
                }
                if (temp.hijo == null)
                    temp = temp.sHermano;
                else
                {
                    node = temp.hijo.findANodeWithKey(value);
                    if (node == null)
                        temp = temp.sHermano;
                    else
                        break;
                }
            }

            return node;
    }
    /* Function to get size */
    public int getSize()
    {
        return (1 + ((hijo == null) ? 0 : hijo.getSize()) + ((sHermano == null) ? 0 : sHermano.getSize()));
    }
}

/* class BinomialHeap */
class BinomialHeap
{
    private BinomialHeapNode Nodes;
    private int size;

    /* Constructor */
    public BinomialHeap()
    {
        Nodes = null;
        size = 0;
    }
    /* Check if heap is empty */
    public boolean isEmpty()
    {
        return Nodes == null;
    }
    /* Function to get size */
    public int getSize()
    {
        return size;
    }
    /* clear heap */
    public void makeEmpty()
    {
        Nodes = null;
        size = 0;
    }
    /* Function to insert */
    public void insert(int value)
    {
        if (value > 0)
        {
            BinomialHeapNode temp = new BinomialHeapNode(value);
            if (Nodes == null)
            {
                Nodes = temp;
                size = 1;
            }
            else
            {
                unionNodes(temp);
                size++;
            }
        }
    }
    /* Function to unite two binomial heaps */
    private void merge(BinomialHeapNode binHeap)
    {
        BinomialHeapNode temp1 = Nodes, temp2 = binHeap;

        while ((temp1 != null) && (temp2 != null))
        {
            if (temp1.grado == temp2.grado)
            {
                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sHermano;
                tmp.sHermano = temp1.sHermano;
                temp1.sHermano = tmp;
                temp1 = tmp.sHermano;
            }
            else
            {
                if (temp1.grado < temp2.grado)
                {
                    if ((temp1.sHermano == null) || (temp1.sHermano.grado > temp2.grado))
                    {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sHermano;
                        tmp.sHermano = temp1.sHermano;
                        temp1.sHermano = tmp;
                        temp1 = tmp.sHermano;
                    }
                    else
                    {
                        temp1 = temp1.sHermano;
                    }
                }
                else
                {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sHermano;
                    temp1.sHermano = tmp;
                    if (tmp == Nodes)
                    {
                        Nodes = temp1;
                    }
                    else
                    {

                    }
                }
            }
        }
        if (temp1 == null)
        {
            temp1 = Nodes;
            while (temp1.sHermano != null)
            {
                temp1 = temp1.sHermano;
            }
            temp1.sHermano = temp2;
        }
        else
        {

        }
    }
    /* Function for union of nodes */
    private void unionNodes(BinomialHeapNode binHeap)
    {
        merge(binHeap);

        BinomialHeapNode prevTemp = null, temp = Nodes, nextTemp = Nodes.sHermano;

        while (nextTemp != null)
        {
            if ((temp.grado != nextTemp.grado) || ((nextTemp.sHermano != null) && (nextTemp.sHermano.grado == temp.grado)))
            {
                prevTemp = temp;
                temp = nextTemp;
            }
            else
            {
                if (temp.llave <= nextTemp.llave)
                {
                    temp.sHermano = nextTemp.sHermano;
                    nextTemp.padre = temp;
                    nextTemp.sHermano = temp.hijo;
                    temp.hijo = nextTemp;
                    temp.grado++;
                }
                else
                {
                    if (prevTemp == null)
                    {
                        Nodes = nextTemp;
                    }
                    else
                    {
                        prevTemp.sHermano = nextTemp;
                    }
                    temp.padre = nextTemp;
                    temp.sHermano = nextTemp.hijo;
                    nextTemp.hijo = temp;
                    nextTemp.grado++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sHermano;
        }
    }
    /* Function to return minimum llave */
    public int findMinimum()
    {
        return Nodes.findMinNode().llave;
    }
    /* Function to delete a particular element */
    public void delete(int value)
    {
        if ((Nodes != null) && (Nodes.findANodeWithKey(value) != null))
        {
            decreaseKeyValue(value, findMinimum() - 1);
            extractMin();
        }
    }
    /* Function to decrease llave with a given value */
    public void decreaseKeyValue(int old_value, int new_value)
    {
        BinomialHeapNode temp = Nodes.findANodeWithKey(old_value);
        if (temp == null)
            return;
        temp.llave = new_value;
        BinomialHeapNode tempParent = temp.padre;

        while ((tempParent != null) && (temp.llave < tempParent.llave))
        {
            int z = temp.llave;
            temp.llave = tempParent.llave;
            tempParent.llave = z;

            temp = tempParent;
            tempParent = tempParent.padre;
        }
    }
    /* Function to extract the node with the minimum llave */
    public int extractMin()
    {
        if (Nodes == null)
            return -1;

        BinomialHeapNode temp = Nodes, prevTemp = null;
        BinomialHeapNode minNode = Nodes.findMinNode();

        while (temp.llave != minNode.llave)
        {
            prevTemp = temp;
            temp = temp.sHermano;
        }

        if (prevTemp == null)
        {
            Nodes = temp.sHermano;
        }
        else
        {
            prevTemp.sHermano = temp.sHermano;
        }

        temp = temp.hijo;
        BinomialHeapNode fakeNode = temp;

        while (temp != null)
        {
            temp.padre = null;
            temp = temp.sHermano;
        }

        if ((Nodes == null) && (fakeNode == null))
        {
            size = 0;
        }
        else
        {
            if ((Nodes == null) && (fakeNode != null))
            {
                Nodes = fakeNode.reverse(null);
                size = Nodes.getSize();
            }
            else
            {
                if ((Nodes != null) && (fakeNode == null))
                {
                    size = Nodes.getSize();
                }
                else
                {
                    unionNodes(fakeNode.reverse(null));
                    size = Nodes.getSize();
                }
            }
        }

        return minNode.llave;
    }

    /* Function to display heap */
    public void displayHeap()
    {
        System.out.print("\nHeap : ");
        displayHeap(Nodes);
        System.out.println("\n");
    }
    private void displayHeap(BinomialHeapNode r)
    {
        if (r != null)
        {
            displayHeap(r.hijo);
            System.out.print(r.llave +" ");
            displayHeap(r.sHermano);
        }
    }
}
