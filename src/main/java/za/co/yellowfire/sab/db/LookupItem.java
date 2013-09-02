package za.co.yellowfire.sab.db;

public interface LookupItem extends DomainItem {

    /**
     * The name of the lookup item
     * @return The name
     */
    String getName();

    /**
     * The name of the lookup item
     * @param name The name
     */
    public void setName(String name);
}
