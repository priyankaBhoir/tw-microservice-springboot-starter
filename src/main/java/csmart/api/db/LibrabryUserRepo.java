package csmart.api.db;

import csmart.api.model.Book;
import csmart.api.model.LibrabryUser;
import csmart.db.gen.tables.records.BookRecord;
import csmart.db.gen.tables.records.LibrabryUserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static csmart.db.gen.tables.LibrabryUser.LIBRABRY_USER;
import static csmart.db.gen.tables.UserBookIssue.USER_BOOK_ISSUE;
import static csmart.db.gen.tables.Subscription.SUBSCRIPTION;
import static csmart.db.gen.tables.Book.BOOK;

/**
 * Created by o3-12 on 16/12/17.
 */
@Repository
@Transactional
public class LibrabryUserRepo {

  @Autowired
  private DSLContext dsl;

  public LibrabryUser getUser(int userId){
    LibrabryUserRecord user = dsl.select()
        .from(LIBRABRY_USER)
        .where((LIBRABRY_USER.ID.eq(userId)))
        .fetchOne()
        .into(LibrabryUserRecord.class);

    return new LibrabryUser(user);
  }

  public List<Book> getAllissuedBooks(int userId){
    List<BookRecord> books = dsl.select()
        .from(BOOK)
        .where(BOOK.ID.in(
            dsl.select(USER_BOOK_ISSUE.BOOK_ID).
                from(LIBRABRY_USER.join(USER_BOOK_ISSUE)
                    .on(LIBRABRY_USER.ID.eq(USER_BOOK_ISSUE.USER_ID).and(LIBRABRY_USER.ID.eq(userId))))
        ))
        .fetch()
        .into(BookRecord.class);

    return books.stream().map(b -> new Book(b)).collect(Collectors.toList());
  }

  public void issueBookToUser(int userId, int bookId) {
    if(checkBookAvaibility(bookId) && checkUserIligibility(userId)) {
      java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

      dsl.insertInto(USER_BOOK_ISSUE)
          .columns(USER_BOOK_ISSUE.USER_ID, USER_BOOK_ISSUE.BOOK_ID, USER_BOOK_ISSUE.ISSUE_DATE)
          .values(userId,
              bookId,
              sqlDate)
          .execute();
    }
  }

  public boolean checkBookAvaibility(int bookId) {
    Integer issuedBooks = dsl.selectCount()
        .from(USER_BOOK_ISSUE)
        .where(USER_BOOK_ISSUE.BOOK_ID.eq(bookId).and(USER_BOOK_ISSUE.ISSUE_ACTIVE.eq(true)))
        .fetchOne(0, int.class);

    Integer totalBooks = dsl.select(BOOK.TOTAL_QUANTITY)
        .from(BOOK)
        .where(BOOK.ID.eq(bookId))
        .fetchOne(0, int.class);
    return totalBooks - issuedBooks > 0;
  }

  public boolean checkUserIligibility(int userId) {
    Integer issuedBooksToUser = dsl.selectCount()
        .from(USER_BOOK_ISSUE)
        .where(USER_BOOK_ISSUE.USER_ID.eq(userId).and(USER_BOOK_ISSUE.ISSUE_ACTIVE.eq(true)))
        .fetchOne(0, int.class);

    Integer allowedBooks = dsl.select(SUBSCRIPTION.NUMBER_OF_BOOKS)
        .from(SUBSCRIPTION.join(LIBRABRY_USER)
            .on(LIBRABRY_USER.SUBSCRIPTION_ID.eq(SUBSCRIPTION.ID).and(LIBRABRY_USER.ID.eq(userId)))
        )
        .fetchOne(0, int.class);
    return allowedBooks - issuedBooksToUser > 0;
  }

  public boolean returnBook(int userId, int bookId) {
    int execute = dsl.update(USER_BOOK_ISSUE)
        .set(USER_BOOK_ISSUE.ISSUE_ACTIVE, false)
        .set(USER_BOOK_ISSUE.RETURN_DATE, new java.sql.Date(new Date().getTime()))
        .where(USER_BOOK_ISSUE.ISSUE_ACTIVE.eq(true)
            .and(USER_BOOK_ISSUE.USER_ID.eq(userId))
            .and(USER_BOOK_ISSUE.BOOK_ID.eq(bookId)))
        .execute();
    return execute > 0;
  }
}
