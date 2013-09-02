package za.co.yellowfire.sab.web;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.sab.db.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


@Named @ViewScoped @Slf4j
public class UploadController implements Serializable {
    @Inject private CategoryDao categoryDao;
    @Inject private TerritoryDao territoryDao;
    @Inject private PropertyDao propertyDao;
    @Inject private MediaTypeDao mediaTypeDao;
    @Inject private CampaignTypeDao campaignTypeDao;
    @Inject private BrandDao brandDao;
    @Inject private DocumentDao documentDao;

    @Setter
    private DocumentItem document;
    @Getter @Setter
    private boolean readOnly = false;
    private boolean readDocument = true;
    @Getter @Setter
    private StreamedContent fileDownload;
    @Getter @Setter
    private Part fileUpload;

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
            this.document = new DocumentItem();
            this.categories = categoryDao.retrieve();
            this.territories = territoryDao.retrieve();
            this.properties = propertyDao.retrieve();
            this.mediaTypes = mediaTypeDao.retrieve();
            this.campaignTypes = campaignTypeDao.retrieve();
            this.brands = brandDao.retrieve();
        } catch (DataAccessException e) {
            log.error("Unable to retrieve data", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public DocumentItem getDocument() {
        try {
            /* Read the document if not done so and if the document has an id */
            if (readDocument && this.document.getId() != null) {
                this.document = documentDao.retrieveById(document.getId());
                if (null != this.document.getFileData()) {
                    this.fileDownload = new DefaultStreamedContent(new ByteArrayInputStream(this.document.getFileData()), this.document.getFileContentType(), this.document.getFileName());
                }
                this.readDocument = false;
            }
        } catch (DataAccessException e) {
            log.error("Unable to retrieve document", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return this.document;
    }

    public void onFileUpload(FileUploadEvent event) {
        log.info("onFileUpload : {}", event);
        UploadedFile file = event.getFile();

        log.info("onFileUpload file : {}", file);
        log.info("onFileUpload filename : {}", file.getFileName());
        log.info("onFileUpload content : {}", file.getContentType());

        document.setFileName(file.getFileName());
        document.setFileContentType(file.getContentType());
        document.setFileData(file.getContents());

        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String onSave() {
        log.error("UPLOAD");

        log.info("onFileUpload file : {}", fileUpload);
        log.info("onFileUpload name : {}", fileUpload.getName());
        log.info("onFileUpload filename : {}", fileUpload.getSubmittedFileName());
        log.info("onFileUpload content : {}", fileUpload.getContentType());

        /* 2Gb limit here, need to validate */
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream((int) fileUpload.getSize());
            int read = IOUtils.copy(fileUpload.getInputStream(), buffer);

            document.setFileName(fileUpload.getSubmittedFileName());
            document.setFileContentType(fileUpload.getContentType());
            document.setFileData(buffer.toByteArray());
        } catch (IOException e) {
            FacesMessage msg = new FacesMessage("Document read error", fileUpload.getName() + " is failed, " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }



        FacesContext context = FacesContext.getCurrentInstance();

//        Set<ConstraintViolation<DocumentItem>> violations = document.validate();
//        if (violations.size() > 0) {
//            for(ConstraintViolation<DocumentItem> violation : violations) {
//                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getMessage(), null);
//                context.addMessage("form:" + violation.getPropertyPath(), msg);
//            }
//            return null;
//        }

        try {
            documentDao.persist(document);

            FacesMessage msg = new FacesMessage("Successful", fileUpload.getName() + " is uploaded.");
            context.addMessage(null, msg);
        } catch (DataAccessException e) {
            log.error("Unable to save document", e);
            FacesMessage msg =
                    new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Unable to save document",
                    e.getMessage());
            context.addMessage(null, msg);
        }

        return "/index";
    }

    public String getCategoryValidationInfo() {
        return hasCategoryError() ? "has-error" : "";
    }

    public String getPositioningValidationInfo() {
        return hasPositioningError() ? "has-error" : "";
    }

    public String getCampaignTypeValidationInfo() {
        return hasCampaignTypeError() ? "has-error" : "";
    }

    public String getPropertyValidationInfo() {
        return hasPropertyError() ? "has-error" : "";
    }

    public String getMediaTypeValidationInfo() {
        return hasMediaTypeError() ? "has-error" : "";
    }

    public String getBrandValidationInfo() {
        return hasBrandError() ? "has-error" : "";
    }

    public String getClaimValidationInfo() {
        return hasClaimError() ? "has-error" : "";
    }

    public String getAttachFileValidationInfo() {
        return hasAttachFileError() ? "has-error" : "";
    }

    public String getCommentsValidationInfo() {
        return hasCommentsError() ? "has-error" : "";
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

    public boolean hasCategoryError() {
        return hasMessages("form:category", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasPositioningError() {
        return hasMessages("form:territory", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasCampaignTypeError() {
        return hasMessages("form:campaignType", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasPropertyError() {
        return hasMessages("form:property", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasMediaTypeError() {
        return hasMessages("form:mediaType", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasBrandError() {
        return hasMessages("form:brand", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasClaimError() {
        return hasMessages("form:claim", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasAttachFileError() {
        return hasMessages("form:attach_file", FacesMessage.SEVERITY_ERROR);
    }

    public boolean hasCommentsError() {
        return hasMessages("form:comments", FacesMessage.SEVERITY_ERROR);
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
                            public boolean apply(FacesMessage msg) {
                                return msg != null && msg.getSeverity() != null && severity.equals(msg.getSeverity());
                            }
                        }));
    }
}
