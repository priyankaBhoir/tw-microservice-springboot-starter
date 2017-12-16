package csmart.api.model;

import csmart.db.gen.tables.records.LibrabryUserRecord;
import csmart.db.gen.tables.records.SubscriptionRecord;

/**
 * Created by o3-12 on 16/12/17.
 */
public class LibrabryUser {
  private int id;
  private String name;


  public LibrabryUser() {
  }

  public LibrabryUser(LibrabryUserRecord rc) {
    this.setId(rc.getId());
    this.setName(rc.getName());
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
