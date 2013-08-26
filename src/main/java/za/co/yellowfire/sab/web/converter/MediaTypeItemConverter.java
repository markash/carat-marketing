package za.co.yellowfire.sab.web.converter;

import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.sab.db.MediaTypeDao;
import za.co.yellowfire.sab.db.MediaTypeItem;

import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Slf4j @FacesConverter(value = "yellowfire.sab.mediaType")
public class MediaTypeItemConverter extends BaseConverter<MediaTypeDao, MediaTypeItem> implements Converter {
    private static final Class<MediaTypeDao> DAO_CLASS = MediaTypeDao.class;
    private static final Class<MediaTypeItem> TYPE_CLASS = MediaTypeItem.class;
    private static final String DAO_NAME = "mediaTypeDao";
    private static final String TYPE_NAME = "mediaType";

    public MediaTypeItemConverter() {
        super(DAO_CLASS, DAO_NAME, TYPE_CLASS, TYPE_NAME);
    }
}
