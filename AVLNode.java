public class AVLNode<X>{
 	
 	AVLNode parent;
    AVLNode leftChild;
    AVLNode rightChild;
 
    X data;
    int height;

    public AVLNode(X data){
        this.data = data;
        this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
        this.height = 0;
    }
}