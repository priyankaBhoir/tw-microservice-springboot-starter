package csmart.api.db;

import csmart.api.model.Book;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by o3-12 on 16/12/17.
 */
@Repository
@Transactional
public class BookRepo {

  @Autowired
  private DSLContext dsl;

  public Book getBook(int bookId) {

  }
}
