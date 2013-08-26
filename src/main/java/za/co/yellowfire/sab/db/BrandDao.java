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

import static za.co.yellowfire.sab.db.postgres.Tables.BRAND;
import static za.co.yellowfire.sab.db.postgres.Tables.MEDIA_TYPE;

@Slf4j @Named
public class BrandDao implements Dao<BrandItem> {
    @Resource(name = "jdbc/carat")
    private DataSource dataSource;
    @Inject
    private SQLDialect dialect;
    @Inject
    private ModelMapper mapper;

    @Override
    public List<BrandItem> retrieve() throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(BRAND).fetchInto(BrandItem.class);
    }

    public BrandItem retrieveById(@NotNull Integer id) throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(BRAND).where(BRAND.ID.eq(id)).fetchOneInto(BrandItem.class);
    }
}
