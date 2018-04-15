
public class xzPair {
	public int x;
	public int y;
	public xzPair(int x, int y) {
		this.x=x;
		this.y =y;
	}
	public xzPair getSector() {
		return new xzPair(x/3,y/3);
	}
	public boolean equals(xzPair other) {
		return (other.x==this.x&&other.y==this.y);
	}
	public xzPair getNextSector() {
		return (new xzPair(x%3,y%3));
	}
	public String toString() {
		return (x+" "+y);
	}
	
}
