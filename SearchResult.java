public class SearchResult implements Comparable<SearchResult>
{
    PageEntry page;
    float relevance;
    
    public SearchResult(PageEntry p, float r){
        this.page = p;
        this.relevance = r;
    }

    public PageEntry getPageEntry(){
        return page;
    }
    
    public float getRelevance(){
        return relevance;
    }

    @Override
    public int compareTo(SearchResult obj){
        if(this.relevance>obj.relevance)
            return 1;
        else if(this.relevance<obj.relevance)
            return -1;
        return 0;
    }
}
