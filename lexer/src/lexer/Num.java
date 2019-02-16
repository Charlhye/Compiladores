package lexer;

public class Num extends Token {
	public final long value;
	public Num(int v) { super(Tag.NUM); value = v; }
	public Num (String v) {super(Tag.NUM); value = Long.parseLong(v, 16);}
	public String toString() {

		return super.toString() + "(" + value + ")";
	}
} 