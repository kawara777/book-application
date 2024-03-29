package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.exception.BookNotFoundException;
import com.ookawara.book.application.exception.CategoryNotFoundException;
import com.ookawara.book.application.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookMapper bookMapper;

    @Test
    public void 全ての項目が未指定のとき全てのデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findAll();
        List<Book> actual = bookService.findBy("", "", null);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findAll();
    }

    @Test
    public void 指定した文字列を含むカテゴリーに該当する全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"));

        doReturn(allBooks).when(bookMapper).findBy("ラ", "", null);
        List<Book> actual = bookService.findBy("ラ", "", null);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("ラ", "", null);
    }

    @Test
    public void 指定した文字列を含む書籍名に該当する全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("", "の", null);
        List<Book> actual = bookService.findBy("", "の", null);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("", "の", null);
    }

    @Test
    public void 購入済みの全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("", "", true);
        List<Book> actual = bookService.findBy("", "", true);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("", "", true);
    }

    @Test
    public void 未購入の全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"));

        doReturn(allBooks).when(bookMapper).findBy("", "", false);
        List<Book> actual = bookService.findBy("", "", false);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("", "", false);
    }

    @Test
    public void 指定した文字列を含むデータが存在しないとき空のデータを返す() {
        List<Book> allBooks = List.of(new Book[0]);

        doReturn(allBooks).when(bookMapper).findBy("no", " ", null);
        List<Book> actual = bookService.findBy("no", " ", null);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("no", " ", null);
    }

    @Test
    public void 存在する本のIDを指定したときに正常に本のデータを返す() throws BookNotFoundException {
        doReturn(Optional.of(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル")))
                .when(bookMapper).findByBookId(1);
        Book actual = bookService.findBook(1);
        assertThat(actual).isEqualTo(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"));
        verify(bookMapper).findByBookId(1);
    }

    @Test
    public void 存在しない本のIDを指定したときに例外のエラーメッセージを返す() {
        doReturn(Optional.empty()).when(bookMapper).findByBookId(0);
        assertThatThrownBy(() -> bookService.findBook(0))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("book：" + 0 + " のデータはありません。");
        verify(bookMapper).findByBookId(0);
    }

    @Test
    public void 存在するカテゴリーのIDを指定したときに正常にカテゴリーのデータを返す() throws BookNotFoundException {
        doReturn(Optional.of(new Category(1, "漫画"))).when(bookMapper).findByCategoryId(1);
        Category actual = bookService.findCategory(1);
        assertThat(actual).isEqualTo(new Category(1, "漫画"));
        verify(bookMapper).findByCategoryId(1);
    }

    @Test
    public void 存在しないカテゴリーのIDを指定したときに例外のエラーメッセージを返す() {
        doReturn(Optional.empty()).when(bookMapper).findByCategoryId(0);
        assertThatThrownBy(() -> bookService.findCategory(0))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("category：" + 0 + " のデータはありません。");
        verify(bookMapper).findByCategoryId(0);
    }
}
