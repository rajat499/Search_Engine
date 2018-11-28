class PageIndex{
	MyLinkedList<WordEntry> list = new MyLinkedList<WordEntry>();

	void addPositionForWord(String str, Position p){
		WordEntry entry = findWord(str);
		if(entry==null){
			WordEntry newEntry = new WordEntry(str);
			newEntry.addPosition(p);
			list.insertRear(newEntry);
		}
		else{
			//System.out.println("coming "+entry.data);
			entry.addPosition(p);
		}
	}

	WordEntry findWord(String str){
		Node<WordEntry> temp = list.head;
		while(temp!=null){
			if(temp.obj.data.equals(str)){
				return temp.obj;
			}
			temp = temp.right;
		}
		if(temp!=null){
			return temp.obj;
		}
		return null;
	}

	MyLinkedList<WordEntry> getWordEntries(){
		return list;
	}
}