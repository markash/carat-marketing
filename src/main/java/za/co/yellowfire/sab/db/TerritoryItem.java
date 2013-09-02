package za.co.yellowfire.sab.db;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@EqualsAndHashCode(of = {"id"})
@ToString(includeFieldNames = false, of = {"name"})
public class TerritoryItem implements LookupItem {
    @Getter
    @Setter
    private Integer id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Date created;
    @Getter @Setter
    private Date updated;
}