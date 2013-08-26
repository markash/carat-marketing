package za.co.yellowfire.sab.web.converter;

import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.sab.db.CategoryDao;
import za.co.yellowfire.sab.db.CategoryItem;

import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Slf4j @FacesConverter(value = "yellowfire.sab.category")
public class CategoryItemConverter extends BaseConverter<CategoryDao, CategoryItem> implements Converter {
    private static final Class<CategoryDao> DAO_CLASS = CategoryDao.class;
    private static final String DAO_NAME = "categoryDao";
    private static final String TYPE_NAME = "category";

    public CategoryItemConverter() {
        super(DAO_CLASS, DAO_NAME, CategoryItem.class, TYPE_NAME);
    }

//    @Override
//    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        CategoryItem result = null;
//        try {
//            result = retrieveById(DAO_CLASS, DAO_NAME, value);
//        } catch (Exception e) {
//            FacesMessage msg =
//                    new FacesMessage(
//                            FacesMessage.SEVERITY_ERROR,
//                            "Converter error",
//                            e.getMessage());
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            log.error("Unable to retrieve category {}, {}", value, e.getMessage());
//        }
//        log.debug(" Returning category from converter {} for {}", result, value);
//        return result;
//    }
//
//    @Override
//    public String getAsString(FacesContext context, UIComponent component, Object value) {
//
//        CategoryItem result = null;
//        if (value instanceof CategoryItem) {
//            result = ((CategoryItem) value);
//        } else if (value instanceof java.lang.Integer) {
//            try {
//                result = retrieveById(DAO_CLASS, DAO_NAME, (Integer) value);
//            } catch (Exception e) {
//                FacesMessage msg =
//                    new FacesMessage(
//                            FacesMessage.SEVERITY_ERROR,
//                            "Converter error",
//                            e.getMessage());
//                FacesContext.getCurrentInstance().addMessage(null, msg);
//                log.error("Unable to retrieve category {}, {}", value, e.getMessage());
//            }
//        }
//        log.debug("Returning category string from converter {} for {}", result, value);
//        return result != null ? result.getId().toString() : null;
//    }
}
