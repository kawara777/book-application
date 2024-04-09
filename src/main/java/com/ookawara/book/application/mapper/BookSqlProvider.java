package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDate;

public class BookSqlProvider implements ProviderMethodResolver {
    public String findBy(@Param("category") String category,
                         @Param("name") String name,
                         @Param("isPurchased") Boolean isPurchased) {
        return new SQL() {
            {
                SELECT("*");
                FROM("books");
                JOIN("categories on books.category_id = categories.category_id");
                if (category != null && !category.isEmpty()) {
                    WHERE("category like concat('%',#{category},'%')");
                }
                if (name != null && !name.isEmpty()) {
                    WHERE("name like concat('%',#{name},'%')");
                }
                if (isPurchased != null) {
                    WHERE("is_purchased = #{isPurchased}");
                }
            }
        }.toString();
    }

    public String updateBook(Book book) {
        return new SQL() {
            {
                UPDATE("books");
                if (book.getName() != null && !book.getName().isBlank()) {
                    SET("name = #{name}");
                }
                if (book.getReleaseDate() != null) {
                    SET("release_date = #{releaseDate}");
                }
                if (book.getIsPurchased() != null) {
                    SET("is_purchased = #{isPurchased}");
                }
                if (book.getCategoryId() >= 1) {
                    SET("category_id = #{categoryId}");
                }
                WHERE("book_id = #{bookId}");
            }
        }.toString();
    }
}
