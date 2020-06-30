package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		
		return containHelper(root,t);
	}
	
	public boolean containHelper(BSTNode<T> nn, T value) {
		if(value == null) 
			{throw new NullPointerException("empty");}
		
		
		if(nn == null) return false;
		 
		//if(value.compareTo(nn.getData()) == 0) return true;
		
	 if(value.compareTo(nn.getData()) > 0){
			return containHelper(nn.getRight(),value); }
		
		else if(value.compareTo(nn.getData())<0) {
			return containHelper(nn.getLeft(),value);
		}
		
		else return true;
		
	}

	public boolean remove(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) {
		
		return getHelper(root,t);
	}
	
	public T getHelper(BSTNode<T> nn, T value) {
		if(value == null) {
			throw new NullPointerException();
		}
		if(nn == null) {
			return null;
		}
		
		if(value.compareTo(nn.getData())> 0) {
			return getHelper(nn.getRight(),value);
		}
		
		else if (value.compareTo(nn.getData())< 0) {
			return getHelper(nn.getLeft(),value);
		}
		
		else return nn.getData();
	}


	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}

	@Override
	public T getMinimum() {
		
		return minHelper(root);
	}
	
	public T minHelper(BSTNode<T> nn) {
		if(nn == null) {
			return null;
		}
		
		if(nn.getLeft() == null) return nn.getData();
		
		else return minHelper(nn.getLeft());
		
	}


	@Override
	public T getMaximum() {
		
		return maxHelper(root);
	}
   
	public T maxHelper(BSTNode<T> nn) {
		if(nn == null) {
			return null;
		}
		if(nn.getRight() == null) {
			return nn.getData();
		}
		
		else return maxHelper(nn.getRight());
	}

	@Override
	public int height() {
		
		return lambaihelper(root);
	}

	public int lambaihelper(BSTNode<T> nn) {
		if(nn == null)
			return -1;
		
		else {
			int height = 1 + Math.max(lambaihelper(nn.getLeft()), lambaihelper(nn.getRight()));
			return height ; }
		
	}

	public Iterator<T> preorderIterator() {
		
		Queue<T> q = new LinkedList<T>();
		preOrderHelper(root,q);
		return q.iterator();
	}
	
	private void preOrderHelper(BSTNode<T> nn, Queue<T> q){
		if(nn != null){
			q.add(nn.getData());
			preOrderHelper(nn.getLeft(), q);
			preOrderHelper(nn.getRight() , q	);
		}
	}


	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}


	public void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	public Iterator<T> postorderIterator() {
		
		Queue<T> q = new LinkedList<T>();
		postOrderHelper(root,q);
		return q.iterator();
	}

	private void postOrderHelper(BSTNode<T> nn, Queue<T> q){
		if(nn != null){
			postOrderHelper(nn.getLeft(), q);
			postOrderHelper(nn.getRight(),q);
			q.add(nn.getData()); }
		}

	@Override
	public boolean equals(BSTInterface<T> other) {
		
		if(other == null)throw new NullPointerException("other is null");
		return ekJaisaHelper(other.getRoot(),root);
	}
	
	private boolean ekJaisaHelper(BSTNode<T> other, BSTNode<T> nn) {
		
		
		if(other == null || nn == null){
			return other == nn;
		}
		
		if(!other.getData().equals(nn.getData())){
			return false;
		}
		return ekJaisaHelper(other.getRight(), nn.getRight()) && ekJaisaHelper(other.getLeft(),nn.getLeft()) ;
	}


	@Override
	public boolean sameValues(BSTInterface<T> other) {
		
		if(other == null){
			throw new NullPointerException();
		}
		Iterator<T> i1 = other.inorderIterator();
		Iterator<T> i2 = this.inorderIterator();
		
		while(i1.hasNext() && i2.hasNext()){
			if(!i1.next().equals(i2.next())){
				return false;
			}
		}
		return !i1.hasNext()&&!i2.hasNext();
	}

	@Override
	public boolean isBalanced() {
		
		if(isEmpty()){
			return true;
		}
		if(size()>=Math.pow(2,height()) && size()<=Math.pow(2, height()+1)){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
    @SuppressWarnings("unchecked")

	public void balance() {
		
		T[] arrarr = (T[]) new Comparable[size()];
		Iterator<T> i1 = this.inorderIterator();
		int i =0;
		while(i1.hasNext()){
			arrarr[i] = i1.next();
			i++;
		}
		root = this.ekLastHelper(0,arrarr.length-1,arrarr);
	}

	public BSTNode<T> ekLastHelper(int pehla, int aakhri, T value[]) {
		if(pehla> aakhri){
			return null;
		}
		int sum = pehla + aakhri;
        int beechka = sum/2;
        BSTNode<T> nn1 = new BSTNode<T>(value[beechka],null,null);
        nn1.setLeft(ekLastHelper(pehla,beechka-1,value));
        nn1.setRight(ekLastHelper(beechka+1,aakhri,value));
        return nn1;   }
	@Override
	public BSTNode<T> getRoot() {
        
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}
