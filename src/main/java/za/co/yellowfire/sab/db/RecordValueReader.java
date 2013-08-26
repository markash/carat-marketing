package za.co.yellowfire.sab.db;

import org.jooq.Field;
import org.jooq.Record;
import org.modelmapper.spi.ValueReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecordValueReader  extends org.modelmapper.jooq.RecordValueReader implements ValueReader<Record> {

    public Object get(Record source, String memberName) {
        /* Own implementation to by-pass the memberName.toUpperCase in the original */
        return source.getValue(memberName);
    }

    public Collection<String> memberNames(Record source) {
        Field<?>[] fields = source.fields();
        if (fields != null) {
            List<String> memberNames = new ArrayList<String>(fields.length);
            for (Field<?> field : fields) {
                /* Skip nulls because PropertyInfoImpl:137 throws a null pointer is this is the case*/
                if (null != source.getValue(field)) {
                    memberNames.add(field.getName());
                }
            }
            return memberNames;
        }

        return null;
    }
}