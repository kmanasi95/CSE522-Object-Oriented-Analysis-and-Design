import java.util.*;

// ============  THE MAIN METHOD WITH TWO TESTS.  ==============
// ============  DON'T MODIFY THE TEST METHODS   ===============
// ============  COMPLETE ONLY THE containedIn METHOD ==========

public class GenericIterators {

public static void main(String[] args) {
	test1();
	System.out.println();
	test2();
}

static void test1() {
	
	AbsTree<Integer> set1 = new Tree<Integer>(100);
	set1.insert(50);
	set1.insert(50);
	set1.insert(25);
	set1.insert(75);
	set1.insert(75);
	set1.insert(150);
	set1.insert(125);
	set1.insert(200);
	set1.insert(100);
	
	AbsTree<Integer> set2 = new Tree<Integer>(100);
	set2.insert(150);
	set2.insert(125);
	set2.insert(50);
	set2.insert(50);
	set2.insert(26);
	set2.insert(25);
	set2.insert(27);
	set2.insert(75);
	set2.insert(75);
	set2.insert(76);
	set2.insert(150);
	set2.insert(125);
	set2.insert(200);
	
	System.out.print("set1 = "); print(set1);
	System.out.print("set2 = "); print(set2);
	
	if (containedIn(set1, set2))
		System.out.println("set1 is contained in set2.");
	else
		System.out.println("set1 is not contained in set2.");
}


static void test2() {
	
	AbsTree<Integer> bag1 = new DupTree<Integer>(100);
	bag1.insert(50);
	bag1.insert(50);
	bag1.insert(25);
	bag1.insert(75);
	bag1.insert(75);
	bag1.insert(150);
	bag1.insert(125);
	bag1.insert(200);
	bag1.insert(100);
	
	AbsTree<Integer> bag2 = new DupTree<Integer>(100);
	bag2.insert(150);
	bag2.insert(125);
	bag2.insert(50);
	bag2.insert(50);
	bag2.insert(26);
	bag2.insert(25);
	bag2.insert(27);
	bag2.insert(75);
	bag2.insert(75);
	bag2.insert(76);
	bag2.insert(150);
	bag2.insert(125);
	bag2.insert(200);
	
	System.out.print("bag1 = "); print(bag1);
	System.out.print("bag2 = "); print(bag2);
	
	System.out.print("bag1 = "); print(bag1);
	System.out.print("bag2 = "); print(bag2);
	
	if (containedIn(bag1, bag2))
		System.out.println("bag1 is contained in bag2.");
	else
		System.out.println("bag1 is not contained in bag2.");	
}


static void print(AbsTree<Integer> bs) {
	System.out.print("{ ");
	for (int x : bs) 
		System.out.print(x + " ");
	System.out.println("}");
}


static <T extends Comparable<T>> boolean containedIn(AbsTree<T> tr1, AbsTree<T> tr2) {
	Iterator<T> iter1 = tr1.iterator();
	Iterator<T> iter2 = tr2.iterator();
	
	T value1,value2;
	value1 = iter1.next();
	value2 = iter2.next();
	
	int equality_status = 0;
	boolean contained = false;
	// fill in rest of the code here
	while(iter2.hasNext()){
		if(equality_status == 2 && iter1.hasNext()) {
			value1 = iter1.next();
			value2 = iter2.next();
		}else if(equality_status == 1) {
			value2 = iter2.next();
		}
		
		if(value1.equals(value2)){
			equality_status = 2;
			System.out.println(value1 + " = " + value2);
			contained = true;
	
		}else {
			equality_status = 1;
			if(value1.compareTo(value2) > 0) {
				System.out.println(value1 + " > " + value2);
			}
			else if(value1.compareTo(value2) < 0) {
				System.out.println(value1 + " < " + value2);
			}
			contained = false;
		}	
	}
	
	if(iter1.hasNext() || iter2.hasNext() || !contained) {
		return false;
	}
	return true;
}

}


//========= GENERIC ABSTREE, TREE, AND DUPTREE (DON'T MODIFY THESE CLASSES)


abstract class AbsTree<T extends Comparable<T>> implements Iterable<T> {

	public AbsTree(T v) {
		value = v;
		left = null;
		right = null;
	}

	public void insert(T v) {
		if (value.compareTo(v) == 0)
			count_duplicates();
		if (value.compareTo(v) > 0)
			if (left == null)
				left = add_node(v);
			else
				left.insert(v);
		else if (value.compareTo(v) < 0)
			if (right == null)
				right = add_node(v);
			else
				right.insert(v);
	}

	public Iterator<T> iterator() {
		return create_iterator();
	}

	protected abstract AbsTree<T> add_node(T n);
	protected abstract void count_duplicates();
	protected abstract int get_count();
	protected abstract Iterator<T> create_iterator();
	protected abstract void update_count();
	
	protected T value;
	protected AbsTree<T> left;
	protected AbsTree<T> right;
}


class Tree<T extends Comparable<T>> extends AbsTree<T> {
	public Tree(T n) {
		super(n);
	}
	
	public Iterator<T> create_iterator() {
		return new AbsTreeIterator<T>(this);
	}

	protected AbsTree<T> add_node(T n) {
		return new Tree<T>(n);
	}

	protected void count_duplicates() {
		;
	}
	
	protected int get_count() {
		return 1;
	}
	
	protected void update_count() {
		
	}
}


class DupTree<T extends Comparable<T>> extends AbsTree<T> {
	public DupTree(T n) {
		super(n);
		count = 1;
	};

	public Iterator<T> create_iterator() {
		return new AbsTreeIterator<T>(this);   // to do
	}
	
	protected AbsTree<T> add_node(T n) {
		return new DupTree<T>(n);
	}

	protected void count_duplicates() {
		count++;
	}
	
	protected int get_count() {
		return count;
	}

	protected int count;
	
	protected void update_count() {
		count = count-1;
	}
}




// ========  GENERIC TREE ITERATORS (COMPLETE THE OUTLINES) =========

 

class AbsTreeIterator<T extends Comparable<T>> implements Iterator<T> {

public AbsTreeIterator(AbsTree<T> root) {
		
	// fill in code here
	stack.push(new Pair(root));
	AbsTree<T> node = root;
	stack_left_spine(node);
}

public boolean hasNext() {

	// fill in code here
	return !stack.isEmpty();
}

public T next() {
	
	// fill in code here
	
	if (stack.lastElement().get_count() > 1) {
		stack.lastElement().decrement_count();
		return stack.lastElement().node.value;
	}else {
		AbsTree<T> node = stack.pop().node;
		T value = node.value;
		if (node.right != null) {
			stack.push(new Pair(node.right));
			node = node.right;
			stack_left_spine(node);
		}
		return value;
	}
}

private void stack_left_spine(AbsTree<T> node) {
	
	// fill in code here
	while (node.left != null) {
		node = node.left;
		stack.push(new Pair(node));
	}
}

private Stack<Pair<T>> stack = new Stack<Pair<T>>();

}

class TreeIterator<T extends Comparable<T>> extends AbsTreeIterator<T> {
	
	// fill in code here
	
	public TreeIterator(AbsTree<T> root) {
		super(root);
	}

}

class DupTreeIterator<T extends Comparable<T>> extends AbsTreeIterator<T> {
	
	// fill in code here
	
	public DupTreeIterator(AbsTree<T> root) {
		super(root);
	}
}

class Pair<T extends Comparable<T>>{
	AbsTree<T> node;
	int count;
	
	public Pair(AbsTree<T> tree) {
		node = tree;
		count = node.get_count();
	}
	
	protected int get_count() {
		return count;
	}
	
	protected void decrement_count() {
		count = count-1;
	}
}



