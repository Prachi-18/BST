package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;
	


	@Override
	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		upperBound++;
		BSTNode<T> nn = new BSTNode<T>(t, null, null);
		root = addToSubtree(root, nn);
		if(Math.log(upperBound)/Math.log(3.0/2.0) < height()) {
			while((double)subtreeSize(nn)/(double)subtreeSize(papaLao(nn)) <= 2.0/3.0) {
				nn = papaLao(nn);
			}
			nn = papaLao(nn);
			balanceChhotaped(nn);
		}
		
	}
	private void balanceChhotaped(BSTNode<T> nn) {
		Queue<T> q = new LinkedList<T>();
		inorderTraverse(q, nn);
		T[] arrarr = (T[]) new Comparable[q.size()];
		q.toArray(arrarr);
		BSTNode<T> nn1 = ekLastHelper( 0, arrarr.length - 1, arrarr);
		nn.setData(nn1.getData());
		nn.setLeft(nn1.getLeft());
		nn.setRight(nn1.getRight());
	} 
	
	private BSTNode<T> papaLao(BSTNode<T> nn) {
		if(nn == null) {
			throw new NullPointerException();
		}
		BSTNode<T> nn1 = root;
		while(nn1 != null) {
			if(nn1.getLeft() == nn || nn1.getRight() == nn) {
				return nn1;
			} 
		else if(nn1.getData().compareTo(nn.getData()) > 0) {
				nn1 = nn1.getLeft();
			}
			else if(nn1.getData().compareTo(nn.getData()) < 0) {
				nn1 = nn1.getRight();
			}
			
			else return nn1;
			
		}
		return null;
	}
	@Override
	public boolean remove(T value) {
		
		if(super.remove(value)){
			if(this.size()*2 < upperBound){
				this.balance();
				upperBound = this.size();
			}
			return true;
		}
		return false;
	}
	
}
