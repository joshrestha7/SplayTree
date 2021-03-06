package splaytree;

public interface SplayTree_Interface {
	public void insert(String s);
	public void remove(String s);
	public String findMin();
	public String findMax();
	public boolean empty();
	public boolean contains(String s);
	public int size();
	public int height();
	public Node getRoot();
}
