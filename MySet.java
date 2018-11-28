import java.util.*;
public class MySet<X>{

	Vector<X> set;

	public MySet(){
		set = new Vector<X>();
	}

	int size(){
		return this.set.size();
	}

	void addElement(X element){
		set.add(element);
	}

	X get(int i){
		return this.set.get(i);
	}

	MySet<X> union(MySet<X> otherSet){
		MySet<X> unionSet = new MySet<X>(); 
		for(int i=0;i<otherSet.size();i++){
			unionSet.addElement(otherSet.set.get(i));
		}
		for(int i=0;i<this.set.size();i++){
			X element = this.set.get(i);
			if(otherSet.find(element)==-1){
				unionSet.addElement(element);
			}
		}
		return unionSet;
	}

	public int find(X element){
		for(int i=0;i<this.size();i++){
			if(set.get(i).equals(element)){
				return i;
			}
		}
		return -1;
	}

	MySet<X> intersection(MySet<X> otherSet){
		MySet<X> intersectionSet = new MySet<X>();
		for(int i=0;i<this.set.size();i++){
			X element = this.set.get(i);
			if(otherSet.find(element)!=-1){
				intersectionSet.addElement(element);
			}
		}
		return intersectionSet;
	}
}