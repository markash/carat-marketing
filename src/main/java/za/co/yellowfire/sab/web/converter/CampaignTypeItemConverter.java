package za.co.yellowfire.sab.web.converter;

import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.sab.db.CampaignTypeDao;
import za.co.yellowfire.sab.db.CampaignTypeItem;
import za.co.yellowfire.sab.db.MediaTypeDao;
import za.co.yellowfire.sab.db.MediaTypeItem;

import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Slf4j @FacesConverter(value = "yellowfire.sab.campaignType")
public class CampaignTypeItemConverter extends BaseConverter<CampaignTypeDao, CampaignTypeItem> implements Converter {
    private static final Class<CampaignTypeDao> DAO_CLASS = CampaignTypeDao.class;
    private static final Class<CampaignTypeItem> TYPE_CLASS = CampaignTypeItem.class;
    private static final String DAO_NAME = "campaignTypeDao";
    private static final String TYPE_NAME = "campaignType";

    public CampaignTypeItemConverter() {
        super(DAO_CLASS, DAO_NAME, TYPE_CLASS, TYPE_NAME);
    }
}
