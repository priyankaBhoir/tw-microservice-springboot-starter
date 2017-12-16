package csmart.api.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by o3-12 on 16/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("ci")

public class LibrabryUserRepoTest {
  @Autowired
  private LibrabryUserRepo librabryUserRepo;

  @Test
  public void testUserInitialiazeWithNoBook(){
    assertEquals(0, librabryUserRepo.getAllissuedBooks(1).size());
  }

  @Test
  public void testUserIssueBooks() {
    librabryUserRepo.issueBookToUser(1, 1);
    assertEquals(1, librabryUserRepo.getAllissuedBooks(1).size());
  }

  @Test
  public void testBooksAvaiability() {
    librabryUserRepo.issueBookToUser(1, 1);
    assertEquals(true, librabryUserRepo.checkBookAvaibility(1));
    librabryUserRepo.issueBookToUser(2, 1);
    assertEquals(false, librabryUserRepo.checkBookAvaibility(1));
  }

  @Test
  public void testUserIligibility() {
    librabryUserRepo.issueBookToUser(1, 1);
    assertEquals(true, librabryUserRepo.checkUserIligibility(1));
    librabryUserRepo.issueBookToUser(1, 1);
    assertEquals(false, librabryUserRepo.checkUserIligibility(1));
  }
}
