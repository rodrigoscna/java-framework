package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelTable;

import java.util.List;

public class ArelNodeInsertStatement extends ArelNodeStatement {
    public List<String> columns;
    public ArelTable relation;
    public Object select;
    public Object values;
}
