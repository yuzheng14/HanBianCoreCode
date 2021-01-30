package com.yuzheng14.Tools;

public class SymbolEntry {
    private final int order;
    private final char symbol;
    public SymbolEntry(int order, char symbol){
        this.order=order;
        this.symbol=symbol;
    }

    public int getOrder() {
        return order;
    }

    public char getSymbol() {
        return symbol;
    }
}
