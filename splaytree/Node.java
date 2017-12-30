package splaytree;

public class Node {

	String data;
	Node left;
	Node right;
	Node par; 
	boolean justMade; //could be helpful if you change some of the return types on your BST_Node insert.
					  //I personally use it to indicate to my SPLT insert whether or not we increment size.

	Node(String data){ 
		this.data=data;
		this.justMade=true;
	}

	public String getData(){ return data; }
	public Node getLeft(){ return left; }
	public Node getRight(){ return right; }

	public Node containsNode(String s){ 
		if(data.equals(s)) {
			splay(this);
			return this;
		}
		
		if(data.compareTo(s)>0){
			if(left == null && par != null) {
				splay(this);
				return this;
			}
			if (left == null && par == null) {
				return this;
			}
			return left.containsNode(s);
		}

		if(data.compareTo(s)<0){
			if(right == null && par != null) {
				splay(this);
				return this;
			}
			if(right == null && par == null) {
				return this;
			}
			return right.containsNode(s);
		}
		return null;
	}

	public Node insertNode(String s){
		if(data.compareTo(s)>0){
			if(left==null){
				left=new Node(s);
				left.par = this;
				Node temp = left;
				splay(left);
				return temp;
			}
			return left.insertNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null){
				right=new Node(s);
				right.par = this;
				Node temp = right;
				splay(right);
				return temp;
			}
			return right.insertNode(s);
		}
		justMade = false;
		splay(this);
		return this;//ie we have a duplicate
	}

	public void removeNode(String s){}

	public Node findMin(){
		if(left!=null)return left.findMin();
		splay(this);
		return this;
	}

	public Node findMax(){
		if(right!=null)return right.findMax();
		splay(this);
		return this;
	}

	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}

	private void splay(Node x) { 

		while(x.par != null) { //won't stop until node is at the root

			if(x.par.par == null) { //no grandparents
				if (x == x.par.right) 
					zigR(x);
				else 
					zigL(x); 
			} else if (x.par.par.right != null && 
					x == x.par.par.right.right) {
				zigZigR(x);
			} else if (x.par.par.left != null && 
					x == x.par.par.left.left) {
				zigZigL(x);
			} else if (x.par.par.right != null &&
					x == x.par.par.right.left) {
				zigZagR(x);
			} else if (x.par.par.left != null && 
					x == x.par.par.left.right) {
				zigZagL(x);
			}
		}
	}


	/* Rotate Left Methods */
	private void zigR(Node x) { 	
		Node b = x.left; //store x's left child
		x.left = x.par; //x's left child becomes the root
		x.left.par = x;
		x.par = null;
		x.left.right = b;
		if (b != null) {
			b.par = x.left;
		}
	}
	
	private void zigZigR(Node x) {
		Node b = x.left;
		Node c = x.par.left;
		Node ggpar = x.par.par.par;
		if (ggpar != null) {

			if (x.par.par.par.right != null &&
					x.par.par.par.right.right.right == x)
				ggpar.right = x;
			else
				ggpar.left = x;
		}
		x.left = x.par;
		x.left.left = x.par.par;
		x.left.left.par = x.left;
		x.left.par = x;
		x.par = ggpar;
		x.left.right = b;
		x.left.left.right = c;
		if (b != null)
			b.par = x.left;
		if (c != null)
			c.par = x.left.left;
	}
	
	private void zigZagR(Node x) {
		Node b = x.left;
		Node c = x.right;
		Node ggpar = x.par.par.par;
		if (ggpar != null) {
			if(x.par.par.par.right != null && 
					x.par.par.par.right.right.left == x)
				ggpar.right = x;
			else
				ggpar.left = x;
		}
		x.right = x.par;
		x.left = x.par.par;
		x.right.par = x;
		x.left.par = x;
		x.par = ggpar;
		x.right.left = c;
		x.left.right = b;
		if (c != null)
			c.par = x.right;
		if (b != null)
			b.par = x.left;
	}
	

	/* Rotate Right Methods */
	public void zigL(Node x) {
		Node b = x.right;
		x.right = x.par;
		x.right.par = x;
		x.par = null;
		x.right.left = b;
		if (b != null) 
			b.par = x.right;
	}
	
	private void zigZigL(Node x) {
		Node b = x.right;
		Node c = x.par.right;
		Node ggpar = x.par.par.par;
		if (ggpar != null) {
			if (x.par.par.par.left != null && 
					x.par.par.par.left.left.left == x)
				ggpar.left = x;
			else
				ggpar.right = x;
		}
		x.right = x.par;
		x.right.right = x.par.par;
		x.right.right.par = x.right;
		x.right.par = x;
		x.par = ggpar;
		x.right.left = b;
		x.right.right.left = c;
		if (b != null)
			b.par = x.right;
		if (c != null)
			c.par = x.right.right;
	}

	private void zigZagL(Node x) {
		Node b = x.left;
		Node c = x.right;
		Node ggpar = x.par.par.par;
		if (ggpar != null) {
			if(x.par.par.par.left != null && 
					x.par.par.par.left.left.right == x) 
				ggpar.left = x;
			else 
				ggpar.right = x;
		}
		x.left = x.par;
		x.right = x.par.par;
		x.left.par = x;
		x.right.par = x;
		x.par = ggpar;
		x.left.right = b;
		x.right.left = c;
		if (b != null)
			b.par = x.left;
		if (c != null)
			c.par = x.right;
	}

}