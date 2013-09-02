package za.co.yellowfire.sab.web;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.sab.db.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @ViewScoped @Slf4j
public class DataController implements Serializable{
    @Inject private CategoryDao categoryDao;
    @Inject private TerritoryDao territoryDao;
    @Inject private PropertyDao propertyDao;
    @Inject private MediaTypeDao mediaTypeDao;
    @Inject private CampaignTypeDao campaignTypeDao;
    @Inject private BrandDao brandDao;
    @Inject private DocumentDao documentDao;

    @Getter @Setter
    private DataModel<? extends LookupItem> values;

    enum Menu {
        CATEGORIES,
        TERRITORIES,
        BRANDS,
        CAMPAIGN_TYPES,
        PROPERTIES,
        MEDIA_TYPES
    }

    @Getter @Setter
    private Menu selectedNav = Menu.CATEGORIES;

    @PostConstruct
    public void init() {
        onCategories(null);
    }

    public String getSelectedItem() {
        switch(selectedNav) {
            case CATEGORIES :       return "Category";
            case TERRITORIES :      return "Positioning Territory";
            case BRANDS :           return "Brand";
            case CAMPAIGN_TYPES :   return "Campaign Type";
            case PROPERTIES :       return "Property";
            case MEDIA_TYPES :      return "Media Type";
            default: return "";
        }
    }

    public void onBrands(ActionEvent event) {
        selectedNav = Menu.BRANDS;
        try {
            this.values = new ListDataModel<>(brandDao.retrieve());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCampaignTypes(ActionEvent event) {
        selectedNav = Menu.CAMPAIGN_TYPES;
        try {
            this.values = new ListDataModel<>(campaignTypeDao.retrieve());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onMediaTypes(ActionEvent event) {
        selectedNav = Menu.MEDIA_TYPES;
        try {
            this.values = new ListDataModel<>(mediaTypeDao.retrieve());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCategories(ActionEvent event) {
        selectedNav = Menu.CATEGORIES;
        try {
            this.values = new ListDataModel<>(categoryDao.retrieve());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onProperties(ActionEvent event) {
        selectedNav = Menu.PROPERTIES;
        try {
            this.values = new ListDataModel<>(propertyDao.retrieve());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onTerritories(ActionEvent event) {
        selectedNav = Menu.TERRITORIES;
        try {
            this.values = new ListDataModel<>(territoryDao.retrieve());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
