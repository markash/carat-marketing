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
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static za.co.yellowfire.sab.db.postgres.Tables.DOCUMENT;
import static za.co.yellowfire.sab.db.postgres.Tables.PROPERTY;

@Slf4j @Named
public class DocumentDao implements Dao<DocumentItem> {
    @Resource(name = "jdbc/carat")
    private DataSource dataSource;
    @Inject
    private SQLDialect dialect;
    @Inject
    private ModelMapper mapper;

    @Override
    public List<DocumentItem> retrieve() throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(DOCUMENT).fetchInto(DocumentItem.class);
    }

    @Override
    public DocumentItem retrieveById(@NotNull Integer id) throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(DOCUMENT).where(DOCUMENT.ID.eq(id)).fetchOneInto(DocumentItem.class);
    }

    public DocumentItem persist(@NotNull DocumentItem document) throws DataAccessException {

        int i = DSL.using(dataSource, dialect).insertInto(DOCUMENT)
                .set(DOCUMENT.NAME, document.getName())
                .set(DOCUMENT.EMAIL, document.getEmail())
                .set(DOCUMENT.COMMENTS, document.getComments())
                .set(DOCUMENT.ATTACH_FILE, document.getAttachFile())
                .set(DOCUMENT.CREATIVE_CLAIM, document.getCreativeClaim())
                .set(DOCUMENT.CATEGORY_ID, document.getCategory().getId())
                .set(DOCUMENT.BRAND_ID, document.getBrand().getId())
                .set(DOCUMENT.CAMPAIGN_TYPE_ID, document.getCampaignType().getId())
                .set(DOCUMENT.MEDIA_TYPE_ID, document.getMediaType().getId())
                .set(DOCUMENT.POSITIONING_ID, document.getTerritory().getId())
                .set(DOCUMENT.PROPERTY_ID, document.getProperty().getId())
                .set(DOCUMENT.CREATED, new Timestamp(new Date().getTime()))
                .execute();

        System.out.println("i = " + i);
        return null;
    }
}
