public class WordEntry{
	AVL<Position> list;
	String data="";
	MyLinkedList<Position> listOfPositions;

	public WordEntry(String word){
		this.data = word;
		this.list = new AVL();
		listOfPositions = new MyLinkedList();
	}

	void addPosition(Position position){
		list.insert(position);
		listOfPositions.insertRear(position);
	}

	void addPositions(MyLinkedList<Position> positions){
		Node<Position> temp = positions.head;
		while(temp!=null){
			this.list.insert(temp.obj);
			temp = temp.right;
		}
		
		if(positions.head == null){
			return;
		}
		if(listOfPositions.head ==null){
			listOfPositions.head = positions.head;
			listOfPositions.tail = positions.tail;
		}
		else{
			positions.head.left = listOfPositions.tail;
			listOfPositions.tail.right = positions.head;
			listOfPositions.tail = positions.tail;
		}
		listOfPositions.size += positions.size;
	}

	MyLinkedList<Position> getAllPositionsForThisWord(){
		return this.listOfPositions;
	}

	@Override
	public boolean equals(Object obj){
        return this.data.equals(((WordEntry)obj).data);
    }

    AVL<Position> getPositionAVL(){
    	return this.list;
    }
}