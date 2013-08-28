package za.co.yellowfire.sab.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@ToString(includeFieldNames = false, of = {"id", "category", "territory", "campaignType", "property", "mediaType", "brand", "name", "email", "creativeClaim", "attachFile", "comments"})
public class DocumentItem implements Serializable {
    @Getter @Setter
    private Integer id;
    @Getter @Setter @NotNull
    private CategoryItem category;
    @Getter @Setter @NotNull
    private TerritoryItem territory;
    @Getter @Setter @NotNull
    private CampaignTypeItem campaignType;
    @Getter @Setter @NotNull
    private PropertyItem property;
    @Getter @Setter @NotNull
    private MediaTypeItem mediaType;
    @Getter @Setter @NotNull
    private BrandItem brand;
    @Getter @Setter @NotEmpty @Length(max = 100) @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    private String name;
    @Getter @Setter @NotEmpty @Length(max = 100) @Email
    private String email;
    @Getter @Setter @Length(max = 255) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String creativeClaim;
    @Getter @Setter @Length(max = 255) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String attachFile;
    @Getter @Setter @Length(max = 255) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String comments;
    @Getter @Setter
    private byte[] fileData;
    @Getter @Setter @Length(max = 255) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String fileName;
    @Getter @Setter @Length(max = 255) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String fileContentType;

    public Set<ConstraintViolation<DocumentItem>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }
}
