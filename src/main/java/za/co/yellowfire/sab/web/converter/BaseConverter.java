package za.co.yellowfire.sab.web.converter;

import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.carat.db.Dao;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.sab.db.DomainItem;
import za.co.yellowfire.sab.db.TerritoryItem;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class BaseConverter<D extends Dao<T>, T extends DomainItem> implements Converter {

    private D dao;
    private Class<D> daoType;
    private Class<T> itemType;
    private String daoName;
    private String itemName;

    //private boolean initialState;
    //private boolean transientFlag = false;
    private ConcurrentHashMap<Integer, T> items = new ConcurrentHashMap<>();

    protected BaseConverter(Class<D> daoType, String daoName, Class<T> itemType, String itemName) {
        this.daoType = daoType;
        this.itemType = itemType;
        this.daoName = daoName;
        this.itemName = itemName;
    }

    protected BeanManager getBeanManager() throws NamingException {
        String name = "java:comp/env/BeanManager";
        return (BeanManager) new InitialContext().lookup(name);
    }

    protected D getDao(Class<D> daoClass, String name) throws NamingException {
        BeanManager bm = getBeanManager();
        Bean bean = bm.getBeans(name).iterator().next();
        CreationalContext ctx =  bm.createCreationalContext(bean);
        return (D) bm.getReference(bean, daoClass, ctx);
    }

    /**
     * Attempts to parse the id, if unsuccessful then -1 is returned else the parsed int
     * @param id The id value to parse
     * @return -1 if parsing failed else id decoded as an int
     */
    protected int parseInt(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected T retrieveById(Class<D> daoClass, String name, @NotNull String id) throws DataAccessException, NamingException {
        return retrieveById(daoClass, name, parseInt(id));
    }

    protected T retrieveById(Class<D> daoClass, String name, @NotNull Integer id) throws DataAccessException, NamingException {

        if (items != null && items.containsKey(id)) {
            return items.get(id);
        }
        if (dao == null) {
            dao = getDao(daoClass, name);
        }

        T result = dao.retrieveById(id);
        if (result != null) {
            items.put(id, result);
        }

        return result;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        T result = null;
        try {
            result = retrieveById(daoType, daoName, value);
        } catch (Exception e) {
            FacesMessage msg =
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Converter error",
                            e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            log.error("Unable to retrieve {} {}, {}", itemName, value, e.getMessage());
        }
        log.debug(" Returning {} from converter {} for {}", itemName, result, value);
        return result;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        T result = null;
        if (itemType.isInstance(value)) {
            result = (T) value;
        } else if (value instanceof Integer) {
            try {
                result = retrieveById(daoType, daoName, (Integer) value);
            } catch (Exception e) {
                FacesMessage msg =
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Converter error",
                                e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                log.error("Unable to retrieve {} {}, {}", itemName, value, e.getMessage());
            }
        }
        log.debug("Returning {} string from converter {} for {}", itemName, result, value);
        return result != null ? result.getId().toString() : null;
    }

//    @Override
//    public Object saveState(FacesContext context) {
//        if (/*!initialStateMarked() && */ items != null) {
//            return items;
//        }
//        return null;
//    }
//
//    @Override
//    public void restoreState(FacesContext context, Object state) {
//        if (context == null) {
//            throw new NullPointerException();
//        }
//        if (state instanceof Map) {
//            this.items = new ConcurrentHashMap<Integer, T>((Map) state);
//        }
//    }
//
//    @Override
//    public boolean isTransient() {
//        return (transientFlag);
//    }
//
//    @Override
//    public void setTransient(boolean transientFlag) {
//        this.transientFlag = transientFlag;
//    }

//    @Override
//    public void markInitialState() {
//        initialState = true;
//    }
//
//    @Override
//    public boolean initialStateMarked() {
//        return initialState;
//    }
//
//    @Override
//    public void clearInitialState() {
//        initialState = false;
//    }
}
