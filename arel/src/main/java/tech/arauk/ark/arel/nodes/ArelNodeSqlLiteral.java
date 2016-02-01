package tech.arauk.ark.arel.nodes;

public class ArelNodeSqlLiteral {
    private String mValue;

    public ArelNodeSqlLiteral(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return toString();
    }

    @Override
    public String toString() {
        return mValue;
    }
}
