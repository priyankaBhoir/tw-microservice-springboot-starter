package csmart.api.users;

import csmart.api.db.LibrabryUserRepo;
import csmart.api.model.Book;
import csmart.api.model.LibrabryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by o3-12 on 16/12/17.
 */

@RestController()
@RequestMapping("/libraryuser")
public class LibrabryUserController {
  @Autowired
  private LibrabryUserRepo librabryUserRepo;

  @GetMapping("/{id}")
  public @ResponseBody
  LibrabryUser getUser(@PathParam("id") int id) {
    LibrabryUserRepo librabryUserRepo = new LibrabryUserRepo();
    return librabryUserRepo.getUser(id);
  }

  @GetMapping("/{id}/books")
  public @ResponseBody
  List<Book> getUserBooks(@PathParam("id") int id) {
    LibrabryUserRepo librabryUserRepo = new LibrabryUserRepo();
    return librabryUserRepo.getAllissuedBooks(id);
  }

  @GetMapping("/{id}/book/{bookId}")
  public @ResponseBody
  void getUserBooks(@PathParam("id") int id, @PathParam("bookId") int bookId) {
    //returns if user have read this book before
  }

  @PostMapping("/{id}/book/{bookId}")
  public @ResponseBody
  void issueBookToUser(@PathParam("id") int id, @PathParam("bookId") int bookId) {
    LibrabryUserRepo librabryUserRepo = new LibrabryUserRepo();
    if(!librabryUserRepo.checkBookAvaibility(bookId)) {
      // msg book is not avaiable
      //return
    } else if(!librabryUserRepo.checkUserIligibility(id)) {
      // msg limit is reached for subscription
      //return
    }
    librabryUserRepo.issueBookToUser(id, bookId);
  }

  @DeleteMapping("/{id}/book/{bookId}")
  public @ResponseBody
  void returnBook(@PathParam("id") int id, @PathParam("bookId") int bookId) {
    LibrabryUserRepo librabryUserRepo = new LibrabryUserRepo();
    librabryUserRepo.returnBook(id, bookId);
  }

}

