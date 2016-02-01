package tech.arauk.ark.arel.collectors;

public class ArelCollector {
    public StringBuilder mBuilder;

    public ArelCollector() {
        mBuilder = new StringBuilder();
    }

    public void append(String string) {
        mBuilder.append(string);
    }

    public String getValue() {
        return mBuilder.toString();
    }
}
