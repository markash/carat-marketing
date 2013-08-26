package za.co.yellowfire.sab.web.converter;

import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.sab.db.BrandDao;
import za.co.yellowfire.sab.db.BrandItem;

import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Slf4j @FacesConverter(value = "yellowfire.sab.brand")
public class BrandItemConverter extends BaseConverter<BrandDao, BrandItem> implements Converter {
    private static final Class<BrandDao> DAO_CLASS = BrandDao.class;
    private static final Class<BrandItem> TYPE_CLASS = BrandItem.class;
    private static final String DAO_NAME = "brandDao";
    private static final String TYPE_NAME = "brand";

    public BrandItemConverter() {
        super(DAO_CLASS, DAO_NAME, TYPE_CLASS, TYPE_NAME);
    }
}
