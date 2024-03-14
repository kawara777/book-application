package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.JoinedBook;
import com.ookawara.book.application.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookMapper bookMapper;

    @Test
    public void 全ての項目が未指定のとき全てのデータを取得() {
        List<JoinedBook> allBooks = List.of(
                new JoinedBook(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"),
                new JoinedBook(2, "鬼滅の刃・1", "2016/06/08", false, 1, "漫画"),
                new JoinedBook(3, "ビブリア古書堂の事件手帖・1", "2011/03/25", true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findAll();
        List<JoinedBook> actual = bookService.findBy("", "", null);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 指定した文字列を含むカテゴリーに該当する全ての本のデータを取得() {
        List<JoinedBook> allBooks = List.of(
                new JoinedBook(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"));

        doReturn(allBooks).when(bookMapper).findBy("ラ", "", null);
        List<JoinedBook> actual = bookService.findBy("ラ", "", null);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 書籍名のみ文字を指定したときその文字が書籍名に含まれている全ての本のデータを取得() {
        List<JoinedBook> allBooks = List.of(
                new JoinedBook(2, "鬼滅の刃・1", "2016/06/08", false, 1, "漫画"),
                new JoinedBook(3, "ビブリア古書堂の事件手帖・1", "2011/03/25", true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("", "の", null);
        List<JoinedBook> actual = bookService.findBy("", "の", null);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 購入済みの全ての本のデータを取得() {
        List<JoinedBook> allBooks = List.of(
                new JoinedBook(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"),
                new JoinedBook(3, "ビブリア古書堂の事件手帖・1", "2011/03/25", true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("", "", true);
        List<JoinedBook> actual = bookService.findBy("", "", true);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 未購入の全ての本のデータを取得() {
        List<JoinedBook> allBooks = List.of(
                new JoinedBook(2, "鬼滅の刃・1", "2016/06/08", false, 1, "漫画"));

        doReturn(allBooks).when(bookMapper).findBy("", "", false);
        List<JoinedBook> actual = bookService.findBy("", "", false);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 指定した文字を含むデータが存在しないとき空のデータを返す() {
        List<JoinedBook> allBooks = List.of(new JoinedBook[0]);

        doReturn(allBooks).when(bookMapper).findBy("no"," ",null);
        List<JoinedBook> actual = bookService.findBy("no"," ",null);
        assertThat(actual).isEqualTo(allBooks);
    }


}
