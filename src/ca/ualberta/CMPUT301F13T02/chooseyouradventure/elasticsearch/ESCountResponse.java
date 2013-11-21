package ca.ualberta.CMPUT301F13T02.chooseyouradventure.elasticsearch;

public class ESCountResponse<T> {
	int count;
	transient Object _shards;
	
	public int getCount() {
		return count;
	}
		
}
