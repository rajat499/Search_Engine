import java.util.*;

public class MySort<Sortable extends Comparable<Sortable>> {
	
	ArrayList<Sortable> sortThisList(MySet<Sortable> listOfSortableEntries){
		ArrayList<Sortable> list = buildArrayList(listOfSortableEntries);

		quickSort(0, list.size()-1, list);

		Collections.reverse(list);

		return list;
	}

	ArrayList<Sortable> buildArrayList(MySet<Sortable> listOfSortableEntries){
		ArrayList<Sortable> list = new ArrayList<Sortable>();

		Vector<Sortable> set = listOfSortableEntries.set;

		for(int i=0;i<set.size();i++)
			list.add(set.get(i));

		return list;
	}

	public void quickSort(int low, int high, ArrayList<Sortable> list){
		int i=low;
		int j=high;
		Sortable pivot = list.get(low+(high-low)/2);
		while (i <= j) {
            while (list.get(i).compareTo(pivot) < 0) {
                i++;
            }
            while (list.get(j).compareTo(pivot) > 0) {
                j--;
            }
            if (i <= j) {
            	Sortable temp = list.get(i);
            	list.set(i,list.get(j));
            	list.set(j,temp);
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
}