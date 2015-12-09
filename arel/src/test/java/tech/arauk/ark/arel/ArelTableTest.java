package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.nodes.ArelNodeInnerJoin;
import tech.arauk.ark.arel.nodes.ArelNodeStringJoin;

public class ArelTableTest extends TestCase {
    private ArelTable mRelation;

    @Override
    protected void setUp() throws Exception {
        mRelation = new ArelTable("users");

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateStringJoinNodes() {
        Object join = mRelation.createStringJoin("foo");

        assertSame(join.getClass(), ArelNodeStringJoin.class);
        assertEquals("foo", ((ArelNodeStringJoin) join).left);
    }

    public void testCreateInnerJoinNodes() {
        Object join = mRelation.createJoin("foo", "bar");

        assertSame(join.getClass(), ArelNodeInnerJoin.class);
        assertEquals("foo", ((ArelNodeInnerJoin) join).left);
        assertEquals("bar", ((ArelNodeInnerJoin) join).right);
    }
}
