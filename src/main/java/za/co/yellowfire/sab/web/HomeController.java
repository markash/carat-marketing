package za.co.yellowfire.sab.web;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.sab.db.DocumentDao;
import za.co.yellowfire.sab.db.DocumentItem;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named @ViewScoped @Slf4j
public class HomeController implements Serializable {
    @Inject DocumentDao documentDao;

    @Getter
    DataModel<DocumentItem> thisWeek;
    @Getter
    DataModel<DocumentItem> thisMonth;
    @Getter
    DataModel<DocumentItem> thisYear;

    @PostConstruct
    public void init() {
        try {
            thisWeek = new ListDataModel<>(documentDao.retrieveCurrentWeek());
            thisMonth = new ListDataModel<>(documentDao.retrieveCurrentMonth());
            thisYear = new ListDataModel<>(documentDao.retrieveCurrentYear());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    @PreDestroy
    public void cleanup() { }

    public String onUpload() {
        return "/collab/upload?faces-redirect=true";
    }

    public String onView(Integer documentId) {
        return "/collab/view?faces-redirect=true&id=" + documentId;
    }
}
