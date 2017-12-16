package csmart.api.books;

import csmart.api.db.BookRepo;
import csmart.api.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by o3-12 on 16/12/17.
 */

@RestController()
@RequestMapping("/book")
public class BookController {
  @Autowired
  private BookRepo bookRepo;

  @GetMapping("/{id}")
  public @ResponseBody
  Book getBook(@PathParam("id") int id) {
    return new Book();
  }





}

