package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.nodes.*;

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

    public void testCreateJoinNodesWithAFullOuterJoinKlass() {
        Object join = mRelation.createJoin("foo", "bar", ArelNodeFullOuterJoin.class);

        assertSame(join.getClass(), ArelNodeFullOuterJoin.class);
        assertEquals("foo", ((ArelNodeFullOuterJoin) join).left);
        assertEquals("bar", ((ArelNodeFullOuterJoin) join).right);
    }

    public void testCreateJoinNodesWithAnOuterJoinKlass() {
        Object join = mRelation.createJoin("foo", "bar", ArelNodeOuterJoin.class);

        assertSame(join.getClass(), ArelNodeOuterJoin.class);
        assertEquals("foo", ((ArelNodeOuterJoin) join).left);
        assertEquals("bar", ((ArelNodeOuterJoin) join).right);
    }

    public void testCreateJoinNodesWithARightOuterJoinKlass() {
        Object join = mRelation.createJoin("foo", "bar", ArelNodeRightOuterJoin.class);

        assertSame(join.getClass(), ArelNodeRightOuterJoin.class);
        assertEquals("foo", ((ArelNodeRightOuterJoin) join).left);
        assertEquals("bar", ((ArelNodeRightOuterJoin) join).right);
    }
}
