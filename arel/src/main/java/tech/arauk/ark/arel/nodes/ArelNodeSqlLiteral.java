package tech.arauk.ark.arel.nodes;

public class ArelNodeSqlLiteral {
    private String mValue;

    public ArelNodeSqlLiteral(Object value) {
        this.mValue = String.valueOf(value);
    }

    public String getValue() {
        return toString();
    }

    @Override
    public String toString() {
        return this.mValue;
    }
}
