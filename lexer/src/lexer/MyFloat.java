package lexer;

public class MyFloat extends Token {
    public final float value;
    public MyFloat(int integer, int decimal) { super(Tag.FLOAT); value = Float.parseFloat(integer+"."+decimal); }
    public String toString() {

        return super.toString() + "(" + value + ")";
    }
}