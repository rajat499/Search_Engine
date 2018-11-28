class MyHashTable{
	
	MySet<WordEntry>[][] hashMap = new MySet[26][26]; 

	@SuppressWarnings("unchecked")
	
	public MyHashTable(){
		for(int i=0;i<26;i++){
			for(int j=0;j<26;j++){
				hashMap[i][j] = new MySet();
			}
		}
	}
	private int getFirstHashIndex(String str){
		return Math.abs((str.toLowerCase().charAt(0) - 97))%26;
	}

	private int getSecondHashIndex(String str){
		return Math.abs((str.toLowerCase().charAt(str.length()-1) - 97))%26;
	}

	void addPositionsForWord(WordEntry w){
		int firstIndex = getFirstHashIndex(w.data);
		int secondIndex = getSecondHashIndex(w.data);
		MySet<WordEntry> l = hashMap[firstIndex][secondIndex];
		int i=0;
		for(i=0;i<l.size();i++){
			if(l.set.get(i).data.equals(w.data)){
				break;
			}
		}
		if(i>=l.size()){
			l.addElement(w);
		}
		else{
			l.set.get(i).addPositions(w.getAllPositionsForThisWord());
		}
	}

	WordEntry find(String w){
		int firstIndex = this.getFirstHashIndex(w);
		int secondIndex = this.getSecondHashIndex(w);
		MySet<WordEntry> l = hashMap[firstIndex][secondIndex];
		for(int i=0;i<l.size();i++){
			WordEntry find = l.set.get(i);
			if(find.data.equals(w))
				return find;
		}
		return null;
	}

}