package lexer;

public class Token {
	public final int tag; 
	public Token(int t) {tag = t;}
	
	@Override
	public String toString() {

		return "lexer.Token [tag=" + tag + "]";
	}
	
}

