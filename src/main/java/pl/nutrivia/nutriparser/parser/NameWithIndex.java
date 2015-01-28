package pl.nutrivia.nutriparser.parser;

public final class NameWithIndex {
    private final String name;
    private final int index;

    public NameWithIndex(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
