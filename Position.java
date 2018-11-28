class Position implements Comparable<Position>{
	PageEntry p;
	int wordIndex;
	int dummyIndex;
	public Position(PageEntry p, int wordIndex, int dummyIndex){
		this.p = p;
		this.wordIndex = wordIndex;
		this.dummyIndex = dummyIndex;
	}

	PageEntry getPageEntry(){
		return p;
	}

	int getWordIndex(){
		return wordIndex;
	}

	@Override
	public int compareTo(Position obj){
		if(this.dummyIndex<obj.dummyIndex)
			return -1;
		else if(this.dummyIndex>obj.dummyIndex)
			return 1;

		return 0;
	}
}
