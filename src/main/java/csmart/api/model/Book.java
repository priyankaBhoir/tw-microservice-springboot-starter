package csmart.api.model;

import csmart.db.gen.tables.records.BookRecord;

/**
 * Created by o3-12 on 16/12/17.
 */
public class Book {
  int id;
  String name;
  int totalQnt;

  public Book() {
  }

  public Book(BookRecord bookRecord) {
    this.id = bookRecord.getId();
    this.name = bookRecord.getName();
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

  public int getTotalQnt() {
    return totalQnt;
  }

  public void setTotalQnt(int totalQnt) {
    this.totalQnt = totalQnt;
  }
}
