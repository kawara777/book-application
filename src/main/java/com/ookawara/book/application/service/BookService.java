package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.exception.BookDuplicateException;
import com.ookawara.book.application.exception.BookNotFoundException;
import com.ookawara.book.application.exception.BookNotUpdatedException;
import com.ookawara.book.application.exception.CategoryDuplicateException;
import com.ookawara.book.application.exception.CategoryNotFoundException;
import com.ookawara.book.application.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public List<Book> findBy(String category, String name, Boolean isPurchased) {
        if (category.isEmpty() && name.isEmpty() && isPurchased == null) {
            return bookMapper.findAll();
        } else {
            return bookMapper.findBy(category, name, isPurchased);
        }
    }

    public Book findBook(int bookId) {
        Optional<Book> book = this.bookMapper.findByBookId(bookId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("book：" + bookId + " のデータはありません。");
        }

    }

    public Category findCategory(int categoryId) {
//        32~37行目を以下のように記述できる
        return bookMapper.findByCategoryId(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("category：" + categoryId + " のデータはありません。"));
    }

    public Book createBook(String name, LocalDate releaseDate, Boolean isPurchased, int categoryId) {
        Book book = new Book(name, releaseDate, isPurchased, categoryId);
        if (bookMapper.findByBook(book.getName(), book.getReleaseDate(), book.getIsPurchased(), book.getCategoryId()).isPresent()) {
            throw new BookDuplicateException("すでに登録されています。");
        } else {
            bookMapper.insertBook(book);
            return book;
        }
    }

    public Category createCategory(String category) {
        Category categoryName = new Category(category);
        if (bookMapper.findByCategory(categoryName.getCategory()).isPresent()) {
            throw new CategoryDuplicateException("すでに登録されています。");
        } else {
            bookMapper.insertCategory(categoryName);
            return categoryName;
        }
    }

    public Book updateBook(int bookId, String name, LocalDate releaseDate, Boolean isPurchased, Integer categoryId) {
        bookMapper.findByBookId(bookId).orElseThrow(
                () -> new BookNotFoundException("book：" + bookId + " のデータはありません。"));
        Book book = new Book(bookId, name, releaseDate, isPurchased, categoryId);
        if (bookMapper.findByBook(book.getName(), book.getReleaseDate(), book.getIsPurchased(), book.getCategoryId()).isPresent()) {
            throw new BookNotUpdatedException("book：" + bookId + " のデータは更新されていません。");
        } else {
            bookMapper.updateBook(book);
            return book;
        }
    }
}
