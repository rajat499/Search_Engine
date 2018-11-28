class CustomizedPageEntry{
	PageEntry page;
	int numberOfWords;

	public CustomizedPageEntry(PageEntry page){
		this.page = page;
		this.numberOfWords = 0;
	}

	void setNumberOfWords(int n){
		this.numberOfWords = n;
	}

	float getTermFrequency(){
		if(this.numberOfWords != 0){
			return (numberOfWords)*1.0f/(this.page.totalNumberOfWords*1.0f);
		}
		return 0.0f;
	}
	@Override
	public boolean equals(Object obj){
        return this.page.equals(((CustomizedPageEntry)obj).page);
    }

}