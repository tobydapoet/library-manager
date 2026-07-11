package com.example.LibraryManager.services;

import lombok.RequiredArgsConstructor;

import com.example.LibraryManager.entities.Book;
import com.example.LibraryManager.entities.Location;
import com.example.LibraryManager.repositories.BookRepository;
import com.example.LibraryManager.requests.book.BookCreateRequest;
import com.example.LibraryManager.requests.book.BookUpdateRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private final UploadService uploadService;

    private final LocationService locationService;

    @Cacheable(value = "uploadCache", key = "'book:all'")
    public Page<Book> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    @Cacheable(value = "uploadCache", key = "'book:' + #id")
    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find this book!"));
    }

    public Page<Book> searchBook(String type, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (type.equals("author")) {
            return bookRepository.findByAuthorContainingIgnoreCase(keyword, pageable);
        } else if (type.equals("title")) {
            return bookRepository.findByTitleIgnoreCase(keyword, pageable);
        } else {
            return Page.empty(pageable);
        }
    }

    @Cacheable(value = "uploadCache", key = "'book:all'")
    public Book create(BookCreateRequest req) {
        Book book = new Book();
        book.setTitle(req.getTitle());
        book.setAuthor(req.getAuthor());
        book.setDescription(req.getDescription());
        Location location = locationService.findById(req.getLocation_id());
        book.setLocation(location);
        book.setSell_price(req.getSell_price());
        book.setBorrow_price(req.getBorrow_price());
        book.setImport_price(req.getImport_price());
        String savedFile = uploadService.upload(req.getFile(), "book");
        book.setImage_url(savedFile);
        return bookRepository.save(book);
    }

    @Caching(evict = {
            @CacheEvict(value = "uploadCache", key = "'book:all'"),
            @CacheEvict(value = "uploadCache", key = "'book:' + #id")
    })
    public Book update(String id, BookUpdateRequest req) {
        Book book = findById(id);
        if (req.getTitle() != null) book.setTitle(req.getTitle());
        if (req.getAuthor() != null) book.setAuthor(req.getAuthor());
        if (req.getDescription() != null) book.setDescription(req.getDescription());
        if (req.getLocation_id() != null) {
            Location location = locationService.findById(req.getLocation_id());
            book.setLocation(location);
        }
        if (req.getBorrow_price() != null) {
            book.setBorrow_price(req.getBorrow_price());
        }
        if (req.getImport_price() != null) {
            book.setImport_price(req.getImport_price());
        }
        if (req.getSell_price() != null) {
            book.setSell_price(req.getSell_price());
        }
        if (req.getStatus() != null) book.setStatus(req.getStatus());

        MultipartFile file = req.getFile();
        if (file != null && !file.isEmpty()) {
            String savedFile = uploadService.upload(file, "book");
            if (book.getImage_url() != null) {
                uploadService.delete(book.getImage_url());
            }
            book.setImage_url(savedFile);
        }

        return bookRepository.save(book);
    }

    public Page<Book> getBookByCategory(String category_name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findBooksByCategoryName(category_name, pageable);
    }
}
