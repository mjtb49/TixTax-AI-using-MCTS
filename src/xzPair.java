
public class xzPair {
	public byte x;
	public byte y;
	public xzPair(byte x, byte y) {
		this.x=x;
		this.y=y;
	}
	public xzPair getSector() {
		return new xzPair((byte)(x/3),(byte)(y/3));
	}
	public boolean equals(xzPair other) {
		return (other.x==this.x&&other.y==this.y);
	}
	public xzPair getNextSector() {
		return (new xzPair((byte)(x%3),(byte)(y%3)));
	}
	public String toString() {
		return (x+" "+y);
	}
	
}
