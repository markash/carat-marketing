package za.co.yellowfire.sab.db;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeConstants;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import za.co.yellowfire.carat.db.Dao;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.sab.db.postgres.tables.records.DocumentRecord;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static za.co.yellowfire.sab.db.postgres.Tables.DOCUMENT;

@Slf4j @Named
public class DocumentDao implements Dao<DocumentItem> {
    @Resource(name = "jdbc/carat") private DataSource dataSource;
    @Inject private SQLDialect dialect;
    @Inject private ModelMapper mapper;
    @Inject private CategoryDao categoryDao;
    @Inject private BrandDao brandDao;
    @Inject private TerritoryDao territoryDao;
    @Inject private CampaignTypeDao campaignTypeDao;
    @Inject private PropertyDao propertyDao;
    @Inject private MediaTypeDao mediaTypeDao;

    @Override
    public List<DocumentItem> retrieve() throws DataAccessException {
        return DSL.using(dataSource, dialect).selectFrom(DOCUMENT).fetchInto(DocumentItem.class);
    }

    @Override
    public DocumentItem retrieveById(@NotNull Integer id) throws DataAccessException {
        DocumentRecord record = DSL.using(dataSource, dialect).selectFrom(DOCUMENT)
                .where(DOCUMENT.ID.eq(id))
                .fetchOne();

        DocumentItem document = new DocumentItem();
        document.setName(record.getName());
        document.setEmail(record.getEmail());
        document.setAttachFile(record.getAttachFile());
        document.setCreativeClaim(record.getCreativeClaim());
        document.setComments(record.getComments());
        document.setFileData(record.getFileData());
        document.setFileName(record.getFileName());
        document.setFileContentType(record.getFileContentType());

        if (record.getCategoryId() != null) {
            document.setCategory(categoryDao.retrieveById(record.getCategoryId()));
        }
        if (record.getBrandId() != null) {
            document.setBrand(brandDao.retrieveById(record.getBrandId()));
        }
        if (record.getPositioningId() != null) {
            document.setTerritory(territoryDao.retrieveById(record.getPositioningId()));
        }
        if (record.getPropertyId() != null) {
            document.setProperty(propertyDao.retrieveById(record.getPropertyId()));
        }
        if (record.getMediaTypeId() != null) {
            document.setMediaType(mediaTypeDao.retrieveById(record.getMediaTypeId()));
        }
        if (record.getCampaignTypeId() != null) {
            document.setCampaignType(campaignTypeDao.retrieveById(record.getCampaignTypeId()));
        }

        return document;
    }

    public List<DocumentItem> retrieveCurrentWeek() throws DataAccessException {
        LocalDate today = new LocalDate();
        LocalDate monday = today.withFieldAdded(DurationFieldType.days(), DateTimeConstants.MONDAY - today.dayOfWeek().get());
        LocalDate sunday = monday.withFieldAdded(DurationFieldType.days(), DateTimeConstants.SUNDAY);

        log.info("Current month {} : {}", monday, sunday);
        return DSL.using(dataSource, dialect).selectFrom(DOCUMENT)
                .where(DOCUMENT.CREATED.between(new Timestamp(monday.toDate().getTime()), new Timestamp(sunday.toDate().getTime())))
                .fetchInto(DocumentItem.class);
    }

    public List<DocumentItem> retrieveCurrentMonth() throws DataAccessException {
        LocalDate today = new LocalDate();
        LocalDate lastMonth = today.withFieldAdded(DurationFieldType.days(), -today.dayOfMonth().get());
        LocalDate monday = today.withFieldAdded(DurationFieldType.days(), DateTimeConstants.MONDAY - today.dayOfWeek().get());

        log.debug("Current month {} : {}", lastMonth, monday);
        return DSL.using(dataSource, dialect).selectFrom(DOCUMENT)
                .where(DOCUMENT.CREATED.between(new Timestamp(lastMonth.toDate().getTime()), new Timestamp(monday.toDate().getTime())))
                .fetchInto(DocumentItem.class);
    }

    public List<DocumentItem> retrieveCurrentYear() throws DataAccessException {
        LocalDate today = new LocalDate();
        LocalDate lastMonth = today.withFieldAdded(DurationFieldType.days(), -today.dayOfMonth().get());
        LocalDate lastYear = lastMonth.withFieldAdded(DurationFieldType.days(), -365);

        log.info("Current year {} : {}", lastYear, lastMonth);
        return DSL.using(dataSource, dialect).selectFrom(DOCUMENT)
                .where(DOCUMENT.CREATED.between(new Timestamp(lastYear.toDate().getTime()), new Timestamp(lastMonth.toDate().getTime())))
                .fetchInto(DocumentItem.class);
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
                .set(DOCUMENT.FILE_DATA, document.getFileData())
                .set(DOCUMENT.FILE_NAME, document.getFileName())
                .set(DOCUMENT.FILE_CONTENT_TYPE, document.getFileContentType())
                .set(DOCUMENT.CREATED, new Timestamp(new Date().getTime()))
                .execute();

        System.out.println("i = " + i);
        return null;
    }
}
