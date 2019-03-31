//CSE 522: Assignment 1, Part 2

  class BST_Part2 {

	  public static void main(String[] args) {
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);

		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
		
		}
  }
  
  abstract class AbsTree {

	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
		parent = null;
	}

	public void insert(int n) {
		if (value == n)
			count_duplicates();
		else if (value < n)
			if (right == null) {
				right = add_node(n);
				this.right.parent = this;
			}
			else {
				this.right.parent = this;
				right.insert(n);
			}
		else if (left == null) {
			left = add_node(n);
			this.left.parent = this;
		}
		else {
			this.left.parent = this;
			left.insert(n);
		}
	}
	
	public void delete(int n) {  		
	// adapt Part 1 solution and use here
		
		AbsTree t = find(n);
		
		boolean is_duplicate = (t == null) ? false : t.get_duplicates();
		
		if(!is_duplicate) {
			if (t == null) { // n is not in the tree
				System.out.println("Unable to delete " + n + " -- not in the tree!");
				return;
			} else if (t.left == null && t.right == null) { // n is at a leaf position
				if (t != this) // if t is not the root of the tree
					case1(t);
				else
					System.out.println("Unable to delete " + n + " -- tree will become empty!");
				t = null;
				return;
			} else if (t.left == null || t.right == null) {
				// t has one subtree only
				if (t != this) { // if t is not the root of the tree
					case2(t);
					return;
				} else { // t is the root of the tree with one subtree
					if (t.left != null) {
						case3L(t);
					}
					else
						case3R(t);
					t = null;
					return;
				}
			} else {
				// t has two subtrees; replace n with the smallest value in t's right subtree
				case3R(t);
				t = null;
			}
		}else {
			t.delete_duplicate_node();
		}
	}
	
	protected void case1(AbsTree t) {  
	// adapt Part 1 solution and use here
		
		if(t.parent != null) {
			if(t.parent.left !=null && t.parent.left.value == t.value) {
				t.parent.left = null;
			}
			else if(t.parent.right !=null && t.parent.right.value == t.value) {
				t.parent.right = null;
			}
		}
		
		t.parent = null;
		t.left = null;
		t.right = null;
	}
	
	protected void case2(AbsTree t) { 
	// adapt Part 1 solution and use here
		
		AbsTree childNode = t.left != null ? t.left : t.right;
		
		if(t.parent != null) {
			if(t.parent.left !=null && t.parent.left.value == t.value) {
				t.parent.left = childNode;
				childNode.parent = t.parent;
			}
			else if(t.parent.right !=null && t.parent.right.value == t.value) {
				t.parent.right = childNode;
				childNode.parent = t.parent;
			}
		}
		
		t.parent = null;
		t.left = null;
		t.right = null;
	}
	
	protected void case3L(AbsTree t) { 
	// adapt Part 1 solution and use here
		
		 
		AbsTree maxNode = t.left.max();
		int countMaxNode = maxNode.get_count();
		
		t.value = maxNode.value;
		
		if (maxNode.left != null)
			maxNode.left.parent = maxNode.parent;
		t.update_count(countMaxNode);
		
		if(maxNode.left == null && maxNode.right == null)
			case1(maxNode);
		else
			case2(maxNode);
		
	}

	protected void case3R(AbsTree t) {  
	// adapt Part 1 solution and use here
		
		AbsTree minNode = t.right.min();
        int countMinNode = minNode.get_count();
		
		t.value = minNode.value;
		
		if (minNode.right != null)
			minNode.right.parent = minNode.parent;
		t.update_count(countMinNode);
		
		if(minNode.left == null && minNode.right == null)
			case1(minNode);
		else
			case2(minNode);
	}

	private AbsTree find(int n) {
	// adapt Part 1 solution and use here
		
		if(this.value == n) {
			return this;
		}
		else if(this.value > n && this.left != null) {
			return this.left.find(n);
		}
		else if(this.value < n && this.right != null) {
			return this.right.find(n);
		}
		else {
			return null;
		}
	}
	
	public AbsTree min() {
	// adapt Part 1 solution and use here
		
		if(this.left != null) {
			return this.left.min();
		}else {
			return this;
		}
	}

	public AbsTree max() {
	// adapt Part 1 solution and use here
		
		if(this.right != null) {
			return this.right.max();
		}else {
			return this;
		}
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	// Protected Abstract Methods
	
	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();

	// Additional protected abstract methods, as needed
	
	protected abstract void delete_duplicate_node();
	protected abstract boolean get_duplicates(); 
	protected abstract int get_count();
	protected abstract void update_count(int newCount);
}


class Tree extends AbsTree {

	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}
	
	// define additional protected methods here, as needed
	
	protected void delete_duplicate_node() {
		;
	}
	
	protected boolean get_duplicates() {
		return false;
	}
	
    protected int get_count() {
    	return 0;
		
	}
    protected  void update_count(int newCount) {
    	
    }

}

class DupTree extends AbsTree {

	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++;
	}

	// define additional protected methods here, as needed
	
	protected void delete_duplicate_node() {
		count = count - 1;
	}

	protected boolean get_duplicates() {
		return count > 1 ? true : false;
	}
	
	
	protected int get_count() {
		return count;
		
	}
	
   protected  void update_count(int newCount) {
    	this.count = newCount;
    }
   
   protected int count;
}