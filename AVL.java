import java.util.*;
public class AVL<X extends Comparable<X>>{
	
	AVLNode<X> root;
	int size = 0;

	public AVL(){
		this.root = null;
		this.size = 0;
	}

	public AVL(AVLNode<X> root) {
		this.root = root;
	}
	
	AVLNode<X> root(){
		return this.root;
	}

	AVLNode<X> predecessor(AVLNode<X> node){

		if(node.leftChild==null){
			AVLNode<X> predecessor = node;
			AVLNode<X> temp =node;
	        predecessor = predecessor.parent;
	        while(predecessor != null && predecessor.rightChild != temp){
	            temp = predecessor;
	            predecessor = predecessor.parent;
	        }
	        return predecessor;
		}

		else{
			AVLNode<X> predecessor = node.leftChild;
			while(predecessor.rightChild!=null){
				predecessor = predecessor.rightChild;
			}
			return predecessor;
		}

	}

	AVLNode<X> successor(AVLNode<X> node){
		
		if(node.rightChild==null){
			AVLNode<X> successor = node;
			AVLNode<X> temp =node;
	        successor = successor.parent;
	        while(successor != null && successor.leftChild != temp){
	            temp = successor;
	            successor = successor.parent;
	        }
	        return successor;
		}

		else{
			AVLNode<X> successor = node.rightChild;
			while(successor.leftChild!=null){
				successor = successor.leftChild;
			}
			return successor;
		}

	}

	AVLNode<X> minimum(){
		AVLNode<X> minimum = this.root();
		if(minimum == null)
			return null;
		AVLNode<X> temp = predecessor(minimum);
		while(temp!=null){
			minimum=temp;
			temp = predecessor(minimum);
		}
		return minimum;
	}

	int leftHeight(AVLNode<X> node){
		int leftHeight;
		if(node.leftChild == null)
			leftHeight = -1;
		else
			leftHeight = node.leftChild.height;

		return leftHeight;
	}

	int rightHeight(AVLNode<X> node){
		int rightHeight;
		if(node.rightChild == null)
			rightHeight = -1; 
		else
			rightHeight = node.rightChild.height;

		return rightHeight;
	}

	void connect(AVLNode<X> parent, AVLNode<X> child){
        if(child != null){
            if(parent.data.compareTo(child.data)<0)
                parent.rightChild = child;
            else
                parent.leftChild = child;
            child.parent = parent;
        }
    }

	boolean checkBalance(AVLNode<X> node){
		return (Math.abs(leftHeight(node) - rightHeight(node)) <= 1);
	}

	AVLNode<X> unbalancedChild(AVLNode<X> ancestor, AVLNode<X> parent){
		if(leftHeight(parent)>rightHeight(parent))
			return parent.leftChild;
		else if(leftHeight(parent)<rightHeight(parent)){
			return parent.rightChild;
		}
		else{
			if(ancestor.leftChild == parent)
				return parent.leftChild;
			else
				return parent.rightChild;
		}
	}

	void updateHeight(AVLNode<X> node){
		node.height = 1+ Math.max(leftHeight(node), rightHeight(node));
	}

	AVLNode<X> search(X obj){
        AVLNode<X> search = this.root;
        while(search != null && search.data.compareTo(obj) != 0){
            if(search.data.compareTo(obj) >=0 ){
                search = search.leftChild;
            }
            else{
                search = search.rightChild;
            }
        }
        return search;
    }

    boolean contains(X obj){
    	return (search(obj) != null);
    }

    MyLinkedList<X> getElements(){
    	MyLinkedList<X> list = new MyLinkedList();
    	AVLNode<X> temp = minimum();
    	while(temp!=null){
    		list.insertRear(temp.data);
    		temp = successor(temp);
    	}
    	return list;
    }

    public void rotate(AVLNode<X> parent, AVLNode<X> child){
        AVLNode<X> transferChild;
        if(parent.leftChild == child){
            transferChild = child.rightChild;
            child.rightChild = null;
            parent.leftChild = null;
        }
        else{
            transferChild = child.leftChild;
            child.leftChild = null;
            parent.rightChild = null;
        }

        child.parent = parent.parent;
        if(child.parent == null)
            root = child;
        else{
            if(child.parent.leftChild == parent)
                child.parent.leftChild = child;
            else
                child.parent.rightChild = child;
        }
        connect(child,parent);
        connect(parent,transferChild);
        updateHeight(parent);
        updateHeight(child);
    }

    void insert(X data){
        if (this.root == null)
            root = new AVLNode(data);

        else{
            AVLNode<X> temp = this.root;
            AVLNode<X> prev = temp;
            AVLNode<X> newNode = new AVLNode(data);

            while(temp != null && temp.data.compareTo(data) != 0){
                prev = temp;
                if(temp.data.compareTo(data)<0)
                    temp = temp.rightChild;
                else
                    temp = temp.leftChild;
            }

            if(temp!=null)
            	prev=temp;

            temp = newNode;
            connect(prev,newNode);
            updateHeight(prev);

            while(prev != null && checkBalance(prev)){
                updateHeight(prev);
                temp = prev;
                prev = prev.parent;
            }

            if(prev == null)
                root = temp;

            else
            {
                AVLNode<X> x = prev;
                AVLNode<X> y = temp;
                AVLNode<X> z = unbalancedChild(x,y);

                AVLNode<X> newRoot;
                if(z!=null){
                    if((z.data.compareTo(x.data)>0 && z.data.compareTo(y.data)<0)||(z.data.compareTo(x.data)<0 && z.data.compareTo(y.data)>0)){
                        rotate(y,z);
                        rotate(x,z);
                        newRoot = z;
                    }
                    else{
                        rotate(x,y);
                        newRoot = y;
                    }
                }
                else{
                    rotate(x,y);
                    newRoot = y;
                }

                if(newRoot.parent == null)
                    root = newRoot;
            }
        }
        this.size++;
    }
/*
	void insert(X obj){
		if(this.root==null){
			this.root = new AVLNode(obj);
			return;
		}
		else{
			AVLNode<X> temp = root;
            AVLNode<X> prev = temp;
            AVLNode<X> newNode = new AVLNode(obj);
            while(temp != null){
                prev = temp;
                if(temp.data.compareTo(obj) == -1){
                    temp = temp.rightChild;
                }
                else{
                    temp = temp.leftChild;
                }
            }

            newNode.parent = prev;
            if(prev.data.compareTo(obj) >= 0)
            	prev.leftChild = newNode;
            else
            	prev.rightChild = newNode;

            temp = prev;
            AVLNode<X> temp2 = temp;
            updateHeight(temp);
            while(temp != null && checkBalance(temp)){
                updateHeight(temp);
                temp2 = temp;
                temp = temp.parent;
            }

            if(temp==null)
            	return;
            else{
            	AVLNode<X> first = temp;
                AVLNode<X> second = temp2;
                AVLNode<X> third = unbalancedChild(first, second);
                AVLNode<X> a,b,c,d,grandTemp;

		       	grandTemp = first.parent;
		       	
		       	if(second == first.rightChild && third == second.rightChild){
		            b = second.leftChild;

		            second.leftChild = first;
		            first.parent = second;
		            first.rightChild = b;
		            if(b!=null)
		            	b.parent = first;
		            	
		            second.parent = grandTemp;
		            if(grandTemp!=null){
		            	if(grandTemp.leftChild==first)
		            		grandTemp.leftChild=second;
		            	else
		            		grandTemp.rightChild=second;
		            } 
		        }
		      	else if(second == first.rightChild && third == second.leftChild){
		            b = third.leftChild;
		            c = third.rightChild;

		            third.leftChild = first;
		            first.parent = third;
		            third.rightChild = second;
		            second.parent = third;
		            
		            first.rightChild = b;
		            if(b!=null)
		            	b.parent = first;
		            
		            second.leftChild = c;
		            if(c!=null)
		            	c.parent = second;
		            
		            third.parent = grandTemp;
		            if(grandTemp!=null){
		            	if(grandTemp.leftChild==first)
		            		grandTemp.leftChild=third;
		            	else
		            		grandTemp.rightChild=third;
		            }
		        }
		        else if(second == first.leftChild && third == second.leftChild){
		            c = second.rightChild;
		            
		            second.rightChild = first;
		            first.parent = second;
		            first.leftChild = c;
		            if(c!=null)
		            	c.parent = first;
		            	
		            second.parent = grandTemp;
		            if(grandTemp!=null){
		            	if(grandTemp.leftChild==first)
		            		grandTemp.leftChild=second;
		            	else
		            		grandTemp.rightChild=second;
		            }
		        }
		        else{
		            b = third.leftChild;
		            c = third.rightChild;

		            third.leftChild = second;
		            second.parent = third;
		            third.rightChild = first;
		            first.parent = third;
		            
		            second.rightChild = b;
		            if(b!=null)
		            	b.parent = second;
		            
		            first.leftChild = c;
		            if(c!=null)
		            	c.parent = first;
		            
		            third.parent = grandTemp;
		            if(grandTemp!=null){
		            	if(grandTemp.leftChild==first)
		            		grandTemp.leftChild=third;
		            	else
		            		grandTemp.rightChild=third;
		            }
		            updateHeight(third);
		        }
		        updateHeight(first);
		        updateHeight(second);
		        updateHeight(third);
	    	}
	    }
	    this.size++;
	}
	*/
}