package lexer;

import java.io.*;
import java.util.*;

public class Lexer {
	public int line = 1;
	private char peek = ' ';

	private Hashtable words = new Hashtable();

	void reserve(Word t) {
		words.put(t.lexeme, t);
	}

	public Lexer() {
		reserve(new Word(Tag.TRUE, "true"));
		reserve(new Word(Tag.FALSE, "false"));
	}

	public void saltaBlancos() throws IOException {
	    /* salta espacios en blanco */
        for (; peek == ' ' || peek == '\n'; peek = (char) System.in.read()) {
            if (peek == ' ' || peek == '\t')
                continue;
            else if (peek == '\n')
                line = line + 1;
            else
                break;
        }
    }

	public Token scan() throws IOException {
	    saltaBlancos();

	    if (peek == '/'){
	        System.in.mark(1);
	        char peek2 = (char) System.in.read();
	        if(peek2 == '/'){
	            while (peek != '\n')
                    peek = (char) System.in.read();
                line = line + 1;

	            saltaBlancos();
            }
            else
                System.in.reset();
        }

		/* Para generar un entero */
		if (Character.isDigit(peek)) {
			int integer = 0;
			int decimal = 0;
			System.in.mark(1);
			char peek2 = (char) System.in.read();
			if(peek == '0' && peek2 == 'x'){
				peek = (char) System.in.read();
				String hex = "";
				do{
					hex += peek;
					peek = (char) System.in.read();
				} while(peek != '\n');
				return new Num(hex);
			}else{
				System.in.reset();
				do {
					integer = 10 * integer + Character.digit(peek, 10);
					peek = (char) System.in.read();
				} while (Character.isDigit(peek));
				/* Para generar un flotante */
				if(peek == '.'){
					peek = (char) System.in.read();
					do {
						decimal = 10 * decimal + Character.digit(peek, 10);
						peek = (char) System.in.read();
					} while (Character.isDigit(peek));
					return new MyFloat(integer, decimal);
				}else{
					return new Num(integer);
				}
			}


		}

		/* Para generar un identificador */
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				peek = (char) System.in.read();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);
			if (w != null)
				return w;
			w = new Word(Tag.ID, s);
			words.put(s, w);
			return w;
		}

		if ( peek == '<' || peek == '>' || peek == '!' || peek == '='){
			System.in.mark(1);
			char peek2 = (char) System.in.read();
			if (peek2 == '='){
                RelOper relOper = new RelOper(Tag.RELOPER, "" + peek + peek2);
                peek = ' ';
                return relOper;
			}
			else{
			    System.in.reset();
			    if (peek != '=') {
                    RelOper relOper = new RelOper(Tag.RELOPER, "" + peek);
                    peek = ' ';
                    return relOper;
                }
            }
		}




		Token t = new Token(peek);
		peek = ' ';
		return t;
	}
}
