import java.util.*;
import java.io.*;

class SearchEngine{
	InvertedPageIndex invertedPageIndex;
	String[] connectingWords = new String[]{"a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or", "and", "does", "will", "whose"}; 
	public SearchEngine(){
		invertedPageIndex = new InvertedPageIndex();
	}

	double inverseDocumentFrequency(MySet<PageEntry> list){
		return Math.log(this.invertedPageIndex.size()*1.0f/(list.set.size()*1.0f));
	}

	public void quickSort(int low, int high, MySet<CustomizedPageEntry> list){
		int i=low;
		int j=high;
		float pivot = (list.set.get(low+(high-low)/2)).getTermFrequency();
		while (i <= j) {
            while (list.set.get(i).getTermFrequency() < pivot) {
                i++;
            }
            while (list.set.get(j).getTermFrequency() > pivot) {
                j--;
            }
            if (i <= j) {
            	CustomizedPageEntry temp = list.set.get(i);
            	list.set.set(i,list.set.get(j));
            	list.set.set(j,temp);
                i++;
                j--;
            }
        }
        if (low < j) {
            quickSort(low, j, list);
        }

        if (i < high) {
            quickSort(i, high, list);
        }
	}

	String printList(ArrayList<SearchResult> list){
		String ans="";
		for(int i=0;i<list.size()-1;i++)
            ans+= list.get(i).getPageEntry().pageName+", ";
        ans+=list.get(list.size()-1).getPageEntry().pageName;
        return ans;
	}

	String parseWord(String word){
		word = word.toLowerCase();
		if(word.equals("stacks")||word.equals("structures")||word.equals("applications")){
			word = word.substring(0,word.length()-1);
		}
		return word;
	}

	String performAction(String actionMessage){
		try{
			String[] actionMessageArray = actionMessage.split(" ");
			if(actionMessageArray[0].equals("addPage")){
				if(actionMessageArray.length>2 || actionMessageArray.length<2){
					throw new ExceptionOccurred("Error- Illegal Number of arguments.");
				}
				PageEntry find = invertedPageIndex.findPage(actionMessageArray[1]);
				if(find != null){
					throw new ExceptionOccurred("Error- Webpage "+actionMessageArray[1]+" already exists.");
				}
				PageEntry page = new PageEntry(actionMessageArray[1]);
				BufferedReader br = new BufferedReader(new FileReader("./webpages/"+actionMessageArray[1]));
				String line="";
				int count = 0;
				int dummyCount = 0;
				while((line=br.readLine())!=null){
					if(line.isEmpty())
						continue;
					line = line.toLowerCase();
					String[] arrayOfWords = line.split("\\s++|\\{|}|<|>|\\(|\\)|\\.|,|;|'|\"|\\?|#|!|-|:");
					for(int i=0;i<arrayOfWords.length;i++){
						if(arrayOfWords[i].length()==0)
							continue;
						count++;
						String word = parseWord(arrayOfWords[i]);
						if(Arrays.stream(connectingWords).anyMatch(arrayOfWords[i]::equals)){
							continue;
						}
						dummyCount++;
						Position pos = new Position(page, count, dummyCount);
						page.pageIndex.addPositionForWord(word, pos);
						//System.out.println("added in page "+word);
						this.invertedPageIndex.addPositionForWord(word, pos);
					}
				}
				page.totalNumberOfWords = dummyCount;
				this.invertedPageIndex.addPage(page);
				return actionMessage+": Done.";	
			}

			else if(actionMessageArray[0].equals("queryFindPagesWhichContainWord")){
				if(actionMessageArray.length>2 || actionMessageArray.length<2){
					throw new ExceptionOccurred("Error- Illegal Number of arguments.");
				}
				String queryWord = actionMessageArray[1];
				String temp = parseWord(queryWord);
				MySet<CustomizedPageEntry> list = invertedPageIndex.getPagesWhichContainWord(temp);
				if(list.size()==0){
					throw new Exception("No webpage contains word "+queryWord);
				}else{
					quickSort(0, list.size()-1, list);
					CustomizedPageEntry p = list.set.get(list.size()-1);
					//System.out.println(p.page.pageName+" "+p.numberOfWords+" "+p.page.totalNumberOfWords);
					String ans = p.page.pageName;//+" "+p.getTermFrequency();
					for(int i=list.size()-2;i>=0;i--){
						p = list.set.get(i);
						//System.out.println(p.page.pageName+" "+p.numberOfWords+" "+p.page.totalNumberOfWords);
						ans+=", "+p.page.pageName;//+" "+p.getTermFrequency();
					}
					ans=actionMessage+": "+ans;
					return ans;
				}
			}

			else if(actionMessageArray[0].equals("queryFindPositionsOfWordInAPage")){
				if(actionMessageArray.length>3 || actionMessageArray.length<3){
					throw new ExceptionOccurred("Error- Illegal Number of arguments.");
				}
				String pageName = actionMessageArray[2];
				String word = actionMessageArray[1];
				PageEntry page = this.invertedPageIndex.findPage(pageName);
				if(page == null){
					throw new Exception("Error- Webpage "+pageName+" not found.");
				}
				word = parseWord(word);
				WordEntry wordEntry = page.pageIndex.findWord(word);
				if(wordEntry==null){
					throw new Exception("Error- Webpage "+pageName+" does not contain word "+word);
				}else{
					MyLinkedList<Position> list = wordEntry.getAllPositionsForThisWord();
					Node<Position> head = list.head;
					String ans = ""+head.obj.wordIndex;
					head = head.right;
					while(head!=null){
						ans+=", "+head.obj.wordIndex;
						head = head.right;
					}
					return actionMessage+": "+ans;
				}
			}

			else if(actionMessageArray[0].equals("queryFindPagesWhichContainAllWords")){
				String[] str = new String[actionMessageArray.length-1];
				for(int i=1;i<=str.length;i++)
					str[i-1] = parseWord(actionMessageArray[i]);

				MySet<SearchResult> list = this.invertedPageIndex.getPagesWhichContainAllWords(str);

				if(list.size()==0){
					return actionMessage+": No webpage Contains all these words together.";
				}

				MySort<SearchResult> sort = new MySort();

				ArrayList<SearchResult> sortedList = sort.sortThisList(list);

				return actionMessage +": "+ printList(sortedList);
			}

			else if(actionMessageArray[0].equals("queryFindPagesWhichContainAnyOfTheseWords")){
				String[] str = new String[actionMessageArray.length-1];
				for(int i=1;i<=str.length;i++)
					str[i-1] = parseWord(actionMessageArray[i]);

				MySet<SearchResult> list = this.invertedPageIndex.getPagesWhichContainAnyOfTheseWords(str);

				if(list.size()==0){
					return actionMessage+": No webpage Contains any of these words.";
				}

				MySort<SearchResult> sort = new MySort();

				ArrayList<SearchResult> sortedList = sort.sortThisList(list);

				return actionMessage +": "+ printList(sortedList);
			}

			else if(actionMessageArray[0].equals("queryFindPagesWhichContainPhrase")){
				String[] str = new String[actionMessageArray.length-1];
				for(int i=1;i<=str.length;i++)
					str[i-1] = parseWord(actionMessageArray[i]);
				
				MySet<SearchResult> list = this.invertedPageIndex.getPagesWhichContainPhrase(str);

				if(list.size()==0){
					return actionMessage+": No webpage Contains this phrase.";
				}

				MySort<SearchResult> sort = new MySort();

				ArrayList<SearchResult> sortedList = sort.sortThisList(list);

				return actionMessage +": "+ printList(sortedList);
			}

			else{
				System.out.println(this.invertedPageIndex.pageSet.size());
				throw new ExceptionOccurred("Error- Illegal Action Message.");
			}
		}catch(Exception e){
			return actionMessage+": "+e.getMessage();
		}
	}
}