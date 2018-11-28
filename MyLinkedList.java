public class MyLinkedList<X>{
	Node<X> head = null;
	Node<X> tail = null;
	int size=0;

	public int size(){
		return this.size;
	}

	void insertFront(X element){
		if(head == null){
			head = new Node<X>(element);
			head.right = null;
			head.left = null;
			tail=head;
		}
		else{
			Node<X> n = new Node<X>(element);
			n.right = head;
			n.left = null;
			head.left = n;
			head = n;
		}
		size++;
	}
	
	void insertRear(X element){
		if(head==null){
			head = new Node<X>(element);
			head.right = null;
			head.left = null;
			tail=head;
		}
		else{
			Node<X> n = new Node<X>(element);
			tail.right = n;
			n.right = null;
			n.left = tail;
			tail = n;
		}
		size++;
	}

	public Node<X> find(X element){
		Node<X> temp = this.head;
		while(temp!=null){
			if(temp.obj.equals(element)){
				return temp;
			}
			temp = temp.right;
		}
		return temp;
	}

	public boolean contains(X element){
		if(this.find(element)!=null)
			return true;
		return false;
	}

	public void deleteFront()throws ExceptionOccurred{
		if(head==null){
			throw new ExceptionOccurred("Error- No Front exists in linked list.");
		}
		if(head == tail){
			head = null;
			tail = null;
			size--;
		}
		else{
			head = head.right;
			head.left.right = null;
			head.left = null;
			size--;
		}
	}

	public void deleteRear()throws ExceptionOccurred{
		if(tail==null){
			throw new ExceptionOccurred("Error- No Rear exists in linked list.");
		}
		if(head == tail){
			head = null;
			tail = null;
			size--;
		}
		else{
			tail = tail.left;
			tail.right.left = null;
			tail.right = null;
			size--;
		}
	}

	public void delete(X element)throws ExceptionOccurred{
		Node<X> delete = this.find(element);
		if(delete == null){
			throw new ExceptionOccurred("Error- No such element exists.");
		}
		if(delete == head)
			this.deleteFront();
		else if(delete == tail)
			this.deleteRear();
		else{
			Node<X> temp = delete.left;
			temp.right = delete.right;
			delete.right.left = temp;
			delete.left = null;
			delete.right = null;
			size--;
		}
	}
}