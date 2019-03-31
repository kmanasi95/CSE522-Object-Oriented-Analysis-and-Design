import java.util.Random;

public class ParTreeInsert {

		public static void main(String[] args) {	
		
	         Tree tr = new Tree(5000);

	         int N = 5;       

	         InsertNums[] threads = {
					new InsertNums(tr), 
					new InsertNums(tr), 
					new InsertNums(tr), 
					new InsertNums(tr),
					new InsertNums(tr)
					};        

			try {
				System.out.println("Start Parallel Insert ...");
				for (int i = 0; i < N; i++) {
					threads[i].start();
				}
				for (int i = 0; i < N; i++) {
					threads[i].join();
				}
				System.out.println("Done Parallel Insert ...\nResults:");

				tr.print();

			} catch (Exception e) { }
		}
	}

class InsertNums extends Thread {

	    Tree tr;	  
  
	    public InsertNums(Tree tr) {
	      this.tr = tr;
	    }

	    public void run() {
	      Random r = new Random();
	      
	      for (int i = 0; i < 5; i++) {
	    	  int a = r.nextInt(10000);
	    	  System.out.println("Random no : " + a);
	    	  try {
	    		  tr.insert_par(a);
	    	  }
	    	  catch(Exception e) {
	    		  System.out.println("Exception while inserting"+e);
	    	  }	  
	      }
	      tr = null;
	    }
}

class Tree {
	private boolean isLocked = false;
	
		public Tree(int n) {
			value = n;
			//left == right == null, by default
		}
		protected void insert(int n) {
			if (value == n) return;
			if (value < n)
				if (right == null) 
					right = new Tree(n);
				else 
					right.insert(n);

			else if (left == null) 
				left = new Tree(n);
			else 
				left.insert(n);
		}
		
		void print() {
			if (left != null) left.print();
			System.out.print(value + " ");
			if (right != null) right.print();
		}
		

	    void insert_par(int n) {

	    	 // define this method
	    	if (value == n) return;
			if (value < n) {
				this.lock();
				if (right == null) {
					right = new Tree(n);
					this.unlock();
				}
					
				else {
					this.unlock();
					right.insert_par(n);
				}
				
			}	

			else {
				this.lock();
				if (left == null) {
					left = new Tree(n);
					 this.unlock();
				}
					
				else {
					this.unlock();
					left.insert_par(n);
					
				}
			}		
	    }
	    
		synchronized void lock() {

		 // define this method
			while(isLocked) {
				try {
					wait();
				}
				catch(Exception e) {
					System.out.println("Exception : "+ e);
				}
			}
			
			isLocked = true;
		}
		
		synchronized void unlock() {

		 // define this method
			isLocked = false;
			notify();

		}
		
		protected int value;
		protected Tree left, right;
		
		// okay to add extra fields
		 
}


 
