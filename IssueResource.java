package com.example.issuems.api;

import com.example.bookms.repo.BookRepo;
import com.example.issuems.Domain.Issue;
import com.example.issuems.Repo.IssueRepo;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import com.example.bookms.Domain.Book;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
public class IssueResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(IssueResource.class);
    @Autowired
    private IssueRepo repo; // Dependency Injection

    @Autowired
    private BookRepo bookrepo; // Add this line to inject BookRepo

    @GetMapping("/Issue")
    public List<Issue> getAllIssue() {
        LOGGER.info("Getting all Issued books from database");
        return repo.findAll();
    }

    @GetMapping("/Issue/{isbn}")
    public ResponseEntity<Issue> getIsbn(@PathVariable String Isbn) {
        LOGGER.info("Getting Book Issued based on book id {} from database", Isbn);
        Optional<Issue> issueFound = repo.findById(Isbn);
        if (issueFound.isPresent()) {
            LOGGER.info("Books issued with isbn {} from database", Isbn);
            return ok((Issue) issueFound.get()); // returns http status code of 200
        }
        LOGGER.error("books issue NOT Found for isbn {} from database", Isbn);
        return notFound().build(); // returns http status code of 404
    }
    @PostMapping("/Issue-isbn/{isbn}")
    public ResponseEntity<Issue> addIssue(@PathVariable String isbn, @RequestBody Issue issue) {
        Optional<Book> bookOptional = bookrepo.findById(isbn);
        if (!bookOptional.isPresent()) {
            LOGGER.error("Book not found for ISBN {}", isbn);
            return ResponseEntity.notFound().build();
        }
        Book book = bookOptional.get();
        int noOfCopies = issue.getNoOfCopies(); // Assuming Issue has this field

        if (book.getTotalCopies() - book.getIssuedCopies() < noOfCopies){
            LOGGER.info("Not enough copies available");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        // update issued copies
        LOGGER.info("Saving book issued into database");
        book.setTotalCopies(book.getIssuedCopies() + noOfCopies); //update issued copies
        bookrepo.save(book);
        // insert issue
        Issue savedissue = repo.save(issue);
        LOGGER.info("book issue saved into database with isbn as {}", savedissue.getIsbn());
        return created(URI.create(savedissue.getIsbn().toString())).body(savedissue);// returns http status code of 201
    }
}
