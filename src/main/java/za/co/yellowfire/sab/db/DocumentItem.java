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
import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@ToString(includeFieldNames = false, of = {"id", "category", "territory", "campaignType", "property", "mediaType", "brand", "name", "email", "creativeClaim", "attachFile", "comments"})
public class DocumentItem implements Serializable {
    @Getter @Setter
    private Integer id;
    @Getter @Setter
    private CategoryItem category;
    @Getter @Setter
    private TerritoryItem territory;
    @Getter @Setter
    private CampaignTypeItem campaignType;
    @Getter @Setter
    private PropertyItem property;
    @Getter @Setter
    private MediaTypeItem mediaType;
    @Getter @Setter
    private BrandItem brand;
    @Getter @Setter @NotEmpty @Length(min = 5, max = 100) @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    private String name;
    @Getter @Setter @NotEmpty @Length(min = 3) @Email
    private String email;
    @Getter @Setter @NotEmpty @Length(max = 100) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String creativeClaim;
    @Getter @Setter @NotEmpty @Length(max = 100) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String attachFile;
    @Getter @Setter @NotEmpty @Length(max = 100) @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC)
    private String comments;

    public Set<ConstraintViolation<DocumentItem>> validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }
}
