package za.co.yellowfire.sab.db;

import lombok.extern.slf4j.Slf4j;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import za.co.yellowfire.carat.db.Dao;
import za.co.yellowfire.carat.db.DataAccessException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.List;

import static za.co.yellowfire.sab.db.postgres.Tables.MEDIA_TYPE;

@Slf4j @Named
public class MediaTypeDao implements Dao<MediaTypeItem> {
    @Resource(name = "jdbc/carat")
    private DataSource dataSource;
    @Inject
    private SQLDialect dialect;
    @Inject
    private ModelMapper mapper;

    @Override
    public List<MediaTypeItem> retrieve() throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(MEDIA_TYPE).fetchInto(MediaTypeItem.class);
    }

    public MediaTypeItem retrieveById(@NotNull Integer id) throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(MEDIA_TYPE).where(MEDIA_TYPE.ID.eq(id)).fetchOneInto(MediaTypeItem.class);
    }

//    private List<TerritoryItem> fetch(Select<PositioningRecord> select) {
//        return map(select.fetch());
//    }
//
//    private List<TerritoryItem> map(List<PositioningRecord> records) {
//        List<TerritoryItem> results = new ArrayList<>();
//        for (PositioningRecord record : records) {
//            results.add(mapper.map(record, TerritoryItem.class));
//        }
//        return results;
//    }
}
