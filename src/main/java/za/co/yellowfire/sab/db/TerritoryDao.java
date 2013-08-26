package za.co.yellowfire.sab.db;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
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

import static za.co.yellowfire.sab.db.postgres.Tables.POSITIONING;

@Slf4j @Named
public class TerritoryDao implements Dao<TerritoryItem> {
    @Resource(name = "jdbc/carat")
    private DataSource dataSource;
    @Inject
    private SQLDialect dialect;
    @Inject
    private ModelMapper mapper;

    @Override
    public List<TerritoryItem> retrieve() throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(POSITIONING).fetchInto(TerritoryItem.class);
    }

    public TerritoryItem retrieveById(@NotNull Integer id) throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(POSITIONING).where(POSITIONING.ID.eq(id)).fetchOneInto(TerritoryItem.class);
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
