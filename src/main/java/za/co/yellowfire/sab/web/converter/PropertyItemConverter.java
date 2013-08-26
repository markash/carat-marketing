package za.co.yellowfire.sab.web.converter;

import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.sab.db.*;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Slf4j @FacesConverter(value = "yellowfire.sab.property")
public class PropertyItemConverter extends BaseConverter<PropertyDao, PropertyItem> implements Converter {
    private static final Class<PropertyDao> DAO_CLASS = PropertyDao.class;
    private static final String DAO_NAME = "propertyDao";
    private static final String TYPE_NAME = "property";

    public PropertyItemConverter() {
        super(DAO_CLASS, DAO_NAME, PropertyItem.class, TYPE_NAME);
    }
}
