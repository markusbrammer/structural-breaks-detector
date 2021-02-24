package fagprojekt;

public class Pair {
	private int Left;
	private String Right;

	public Pair(int left, String right) {
		Left = left;
		Right = right;
	}
	
	public int getLeft() {
		return Left;
	}
	
	public void setLeft(int left) {
		Left = left;
	}
	
	public String getRight() {
		return Right;
	}
	
	public void setRight(String right) {
		Right = right;
	}
}
