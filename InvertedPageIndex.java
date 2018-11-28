import java.util.*;
class InvertedPageIndex{

	MySet<PageEntry> pageSet = new MySet();
	MyHashTable hashTable = new MyHashTable();

	void addPage(PageEntry p){
		pageSet.addElement(p);
	}

	int size(){
		return pageSet.set.size();
	}

	void addPositionForWord(String str, Position p){
		WordEntry w = new WordEntry(str);
		w.addPosition(p);
		this.hashTable.addPositionsForWord(w);
	}

	MySet<CustomizedPageEntry> getPagesWhichContainWord(String str){
		MySet<CustomizedPageEntry> pagesContainingWord = new MySet();
		WordEntry word = this.hashTable.find(str);
		if(word==null){
			return pagesContainingWord;
		}
		MyLinkedList<Position> list = word.getAllPositionsForThisWord();
		if(list.size()==0){
			return pagesContainingWord;
		}
		Node<Position> head = list.head;
		CustomizedPageEntry p;/* = new CustomizedPageEntry(head.obj.getPageEntry());*/
		//p.setNumberOfWords(1);
		//pagesContainingWord.addElement(p);
		//head = head.right;
		while(head!=null){
			p = new CustomizedPageEntry(head.obj.getPageEntry());
			//System.out.println(p.page.pageName);
			int find = pagesContainingWord.find(p);
			if(find != -1){
				pagesContainingWord.get(find).numberOfWords++;
			}
			else{
				p.setNumberOfWords(1);
				//System.out.println("added "+p.page.pageName);
				pagesContainingWord.addElement(p);
			}
			head = head.right;
		}
		return pagesContainingWord;
	}

	MySet<SearchResult> getPagesWhichContainAllWords(String str[]){
		if(str.length==0)
			return null;
		MySet<Integer> size = new MySet(); 
		
		MySet<CustomizedPageEntry> allPages = getPagesWhichContainWord(str[0]);
		size.addElement(allPages.set.size());
		for(int i=1;i<str.length;i++){
			MySet<CustomizedPageEntry> pageContainingWord = getPagesWhichContainWord(str[i]);
			size.addElement(pageContainingWord.set.size());
			allPages = allPages.intersection(pageContainingWord);
		}

		MySet<SearchResult> pagesContainingAllWord = new MySet();
		for(int x=0;x<allPages.set.size();x++){
			PageEntry page = allPages.get(x).page;
			float relevance = 0.0f;
			for(int i=0;i<str.length;i++){
				relevance += (page.getTermFrequency(str[i]))*(Math.log(this.size())-Math.log(size.get(i)));
			}

			pagesContainingAllWord.addElement(new SearchResult(page, relevance));
		}
		return pagesContainingAllWord;
	}

	MySet<SearchResult> getPagesWhichContainAnyOfTheseWords(String str[]){
		if(str.length==0)
			return null;
		MySet<Integer> size = new MySet(); 
		
		MySet<CustomizedPageEntry> allPages = getPagesWhichContainWord(str[0]);
		size.addElement(allPages.set.size());
		for(int i=1;i<str.length;i++){
			MySet<CustomizedPageEntry> pageContainingWord = getPagesWhichContainWord(str[i]);
			size.addElement(pageContainingWord.set.size());
			allPages = allPages.union(pageContainingWord);
		}

		MySet<SearchResult> pagesContainingAnyWord = new MySet();
		for(int x=0;x<allPages.set.size();x++){
			PageEntry page = allPages.get(x).page;
			float relevance = 0.0f;
			for(int i=0;i<str.length;i++){
				relevance += page.getTermFrequency(str[i])*(Math.log(this.size())-Math.log(size.get(i)));
			}

			pagesContainingAnyWord.addElement(new SearchResult(page, relevance));
		}
		return pagesContainingAnyWord;
	}


	PageEntry findPage(String pageName){
		for(int i=0;i<pageSet.size();i++){
			PageEntry pageEntry = pageSet.set.get(i);
			if(pageEntry.pageName.equals(pageName)){
				return pageEntry;
			}
		}
		return null;
	}

	public float getRelevanceOfPage(PageEntry page, String str[], boolean isPhrase)
    {
        if(isPhrase){
            Vector<Integer> phraseResult = page.phraseFirstIndices(str);
            if(phraseResult == null)
            	return 0;
            int m = phraseResult.size();
            int wp = page.totalNumberOfWords;

            return (m*1.0f)/(wp-(str.length-1)*m);
        }
        else{
            float relevance = 0.0f;
            for(int i=0;i<str.length;i++){
            	relevance += page.getTermFrequency(str[i]);
            }
            return relevance;
        }
    }

	MySet<SearchResult> getPagesWhichContainPhrase(String str[]){
        MySet<SearchResult> allPages = getPagesWhichContainAllWords(str);
        if(str.length==0)
        	return null;
        int len = str.length;
        Vector<SearchResult> pages = allPages.set;
        int l = pages.size();
        MySet<SearchResult> validPhrasePages = new MySet();

        for(int i=0;i<l;i++){
            SearchResult p = pages.get(i);
            //if(validPhrasePages.find(p.page)!=null)
            //	continue;
            Vector<Integer> phraseResult = p.page.phraseFirstIndices(str);
            if(phraseResult == null)
            	continue;
            int m = phraseResult.size();
            int wp = p.page.totalNumberOfWords;

            float relevance = (m*1.0f)/(wp-(len-1)*m);
          
            validPhrasePages.addElement(new SearchResult(p.page, relevance));
        }
        return validPhrasePages;           
    }
}