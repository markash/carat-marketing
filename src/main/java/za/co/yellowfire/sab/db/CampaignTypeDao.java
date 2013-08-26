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

import static za.co.yellowfire.sab.db.postgres.Tables.CAMPAIGN_TYPE;

@Slf4j @Named
public class CampaignTypeDao implements Dao<CampaignTypeItem> {
    @Resource(name = "jdbc/carat")
    private DataSource dataSource;
    @Inject
    private SQLDialect dialect;
    @Inject
    private ModelMapper mapper;

    @Override
    public List<CampaignTypeItem> retrieve() throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(CAMPAIGN_TYPE).fetchInto(CampaignTypeItem.class);
    }

    public CampaignTypeItem retrieveById(@NotNull Integer id) throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(CAMPAIGN_TYPE).where(CAMPAIGN_TYPE.ID.eq(id)).fetchOneInto(CampaignTypeItem.class);
    }
}
