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

import static za.co.yellowfire.sab.db.postgres.Tables.PROPERTY;

@Slf4j @Named
public class PropertyDao implements Dao<PropertyItem> {
    @Resource(name = "jdbc/carat")
    private DataSource dataSource;
    @Inject
    private SQLDialect dialect;
    @Inject
    private ModelMapper mapper;

    @Override
    public List<PropertyItem> retrieve() throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(PROPERTY).fetchInto(PropertyItem.class);
    }

    @Override
    public PropertyItem retrieveById(@NotNull Integer id) throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(PROPERTY).where(PROPERTY.ID.eq(id)).fetchOneInto(PropertyItem.class);
    }
}
