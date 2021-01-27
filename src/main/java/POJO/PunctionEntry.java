package POJO;

public class PunctionEntry {
    private final int order;
    private final char character;
    public PunctionEntry(int order,char character){
        this.order=order;
        this.character=character;
    }

    public int getOrder() {
        return order;
    }

    public char getCharacter() {
        return character;
    }
}
