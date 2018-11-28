import java.util.*;
class PageEntry{

	String pageName="";
	PageIndex pageIndex = new PageIndex();
	int totalNumberOfWords=0;
	
	public PageEntry(String pageName){
		this.pageName = pageName;
	}

	PageIndex getPageIndex(){
		return this.pageIndex;
	}

	float getTermFrequency(String word){
		WordEntry find = this.pageIndex.findWord(word);
		if(find!=null){
			return (find.list.size)*1.0f/(totalNumberOfWords*1.0f);
		}
		return 0.0f;
	}

    @Override
	public boolean equals(Object obj){
        return this.pageName.equals(((PageEntry)obj).pageName);
    }

    Vector<Integer> phraseFirstIndices(String str[]){
    	Vector<Integer> indices = new Vector<Integer>();
    	if(str.length == 0)
    		return null;
    	int len = str.length;
    	AVL<Position>[] positionAVLArray = new AVL[len];
    	for(int i=0;i<len;i++){
    		WordEntry w = this.pageIndex.findWord(str[i]);
    		if(w==null)
    			return null;
    		positionAVLArray[i] = w.getPositionAVL(); 
    	}
    	AVL<Position> firstAVL = positionAVLArray[0];

    	AVLNode<Position> traverser = firstAVL.minimum();

    	while(traverser!=null){
    		int pos = traverser.data.dummyIndex;
            boolean isPhrase = true;
            for(int i=1;i<len;i++){
                Position newPos = new Position(null,-1,pos+i);
                if(positionAVLArray[i].search(newPos) == null){
                    isPhrase = false;
                    break;
                }
            }
            if(isPhrase){
                indices.add(traverser.data.wordIndex);
            }

    		traverser = firstAVL.successor(traverser);
    	}

    	if(indices.size()==0)
    		return null;
    	return indices;
    }

    public boolean validatePhrase(String str[]){
    	if(phraseFirstIndices(str)==null)
    		return false;
        return true;
    }
}