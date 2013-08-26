package za.co.yellowfire.sab.web.converter;

import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.sab.db.TerritoryDao;
import za.co.yellowfire.sab.db.TerritoryItem;

import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Slf4j @FacesConverter(value = "yellowfire.sab.territory")
public class TerritoryItemConverter extends BaseConverter<TerritoryDao, TerritoryItem> implements Converter {
    private static final Class<TerritoryDao> DAO_CLASS = TerritoryDao.class;
    private static final String DAO_NAME = "territoryDao";
    private static final String TYPE_NAME = "territory";

    public TerritoryItemConverter() {
        super(DAO_CLASS, DAO_NAME, TerritoryItem.class, TYPE_NAME);
    }
}
