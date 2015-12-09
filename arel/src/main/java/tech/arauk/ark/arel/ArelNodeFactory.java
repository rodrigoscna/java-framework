package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeInnerJoin;
import tech.arauk.ark.arel.nodes.ArelNodeJoin;
import tech.arauk.ark.arel.nodes.ArelNodeStringJoin;

import java.lang.reflect.InvocationTargetException;

/**
 * Class designed for creating various kinds of Arel Nodes.
 *
 * @author Rodrigo Scomazzon do Nascimento <rodrigo.sc.na@gmail.com>
 */
public class ArelNodeFactory {
    public static ArelNodeJoin createJoin(String to) {
        return createJoin(to, null);
    }

    public static ArelNodeJoin createJoin(String to, String constraint) {
        return createJoin(to, constraint, ArelNodeInnerJoin.class);
    }

    public static ArelNodeJoin createJoin(String to, String constraint, Class<? extends ArelNodeJoin> kclass) {
        try {
            return kclass.getConstructor(String.class, String.class, Class.class).newInstance(to, constraint, kclass);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            return null;
        }
    }

    public static ArelNodeJoin createStringJoin(String to) {
        return createJoin(to, null, ArelNodeStringJoin.class);
    }
}
