package za.co.yellowfire.sab.db;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import za.co.yellowfire.carat.db.Dao;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.sab.db.postgres.tables.records.CategoryRecord;

import static za.co.yellowfire.sab.db.postgres.Tables.CATEGORY;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j @Named
public class CategoryDao implements Dao<CategoryItem> {

    @Resource(name = "jdbc/carat")
    private DataSource dataSource;

    @Inject
    private SQLDialect dialect;

    @Override
    public List<CategoryItem> retrieve() throws DataAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().addValueReader(new RecordValueReader());

        DSLContext create = DSL.using(dataSource, dialect);
        Result<CategoryRecord> records = create.selectFrom(CATEGORY).fetch();
        List<CategoryItem> results = new ArrayList<>();
        for (CategoryRecord record : records) {
            results.add(modelMapper.map(record, CategoryItem.class));
        }

        return results;
    }

    public CategoryItem retrieveById(@NotNull Integer id) throws DataAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().addValueReader(new RecordValueReader());

        DSLContext create = DSL.using(dataSource, dialect);
        Result<CategoryRecord> records = create.selectFrom(CATEGORY).where(CATEGORY.ID.eq(id)).fetch();
        List<CategoryItem> results = new ArrayList<>();
        for (CategoryRecord record : records) {
            results.add(modelMapper.map(record, CategoryItem.class));
        }
        return results.size() > 0 ? results.get(0) : null;
    }
}
