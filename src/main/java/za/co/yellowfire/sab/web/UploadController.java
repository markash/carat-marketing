package za.co.yellowfire.sab.web;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.sab.db.*;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


@Named @ViewScoped @Slf4j
public class UploadController implements Serializable {
    @Inject private CategoryDao categoryDao;
    @Inject private TerritoryDao territoryDao;
    @Inject private PropertyDao propertyDao;
    @Inject private MediaTypeDao mediaTypeDao;
    @Inject private CampaignTypeDao campaignTypeDao;
    @Inject private BrandDao brandDao;

    @Getter @Setter
    private Part uploadedFile;
    @Getter @Setter
    private String fileContent;
    @Getter @Setter
    private DocumentItem document;

    @Getter @Setter
    private List<CategoryItem> categories;
    @Getter @Setter
    private List<TerritoryItem> territories;
    @Getter @Setter
    private List<PropertyItem> properties;
    @Getter @Setter
    private List<MediaTypeItem> mediaTypes;
    @Getter @Setter
    private List<BrandItem> brands;
    @Getter @Setter
    private List<CampaignTypeItem> campaignTypes;

    @PostConstruct
    public void init() {
        try {
            /* TODO For now the document is set to a new instance, later the document id will be read */
            this.document = new DocumentItem();

            this.categories = categoryDao.retrieve();
            this.territories = territoryDao.retrieve();
            this.properties = propertyDao.retrieve();
            this.mediaTypes = mediaTypeDao.retrieve();
            this.campaignTypes = campaignTypeDao.retrieve();
            this.brands = brandDao.retrieve();
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
        }
    }

    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<>();
        Part file = (Part)value;
        if (file.getSize() > 1024) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!"text/plain".equals(file.getContentType())) {
            msgs.add(new FacesMessage("not a text file"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public String getNameValidationInfo() {
        if (hasNameError()) {
            return "has-error";
        } else if (hasNameWarning()) {
            return "has-warning";
        }
        return "";
    }

    public String getEmailValidationInfo() {
        if (hasEmailError()) {
            return "has-error";
        } else if (hasEmailWarning()) {
            return "has-warning";
        }
        return "";
    }

    public boolean hasNameWarning() {
        return hasMessages("form:name", FacesMessage.SEVERITY_WARN);
    }

    public boolean hasNameError() {
        return hasMessages("form:name", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasEmailWarning() {
        return hasMessages("form:email", FacesMessage.SEVERITY_WARN);
    }

    public boolean hasEmailError() {
        return hasMessages("form:email", FacesMessage.SEVERITY_ERROR);
    }

    protected boolean hasMessages(final String clientId, @NotNull final FacesMessage.Severity severity) {
        return !Iterables.isEmpty(Iterables.filter(
                        FacesContext.getCurrentInstance().getMessageList(clientId),
                        new Predicate<FacesMessage>() {
                            @Override
                            public boolean apply(@Nullable FacesMessage msg) {
                                return msg != null && msg.getSeverity() != null && severity.equals(msg.getSeverity());
                            }
                        }));
    }

    public void uploadFile() {
        log.error("UPLOAD");

        for(ConstraintViolation<DocumentItem> violation : document.validate()) {
            System.out.println(violation.getPropertyPath() + "violation = " + violation.getMessage());
        }

//        try {
//            //fileContent = new Scanner(uploadedFile.getInputStream()).useDelimiter("\\A").next();
//        } catch (IOException e) {
//            FacesMessage msg =
//                    new FacesMessage(
//                            FacesMessage.SEVERITY_ERROR,
//                            "error uploading file",
//                            null);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }
}
