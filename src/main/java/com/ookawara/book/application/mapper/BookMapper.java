package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapper {
    @Select("select * from books join categories on books.category_id = categories.category_id")
    List<Book> findAll();

    @SelectProvider(SqlProvider.class)
    List<Book> findBy(@Param("category") String category,
                      @Param("name") String name,
                      @Param("isPurchased") Boolean isPurchased);

    class SqlProvider implements ProviderMethodResolver {
        public String findBy(@Param("category") String category,
                             @Param("name") String name,
                             @Param("isPurchased") Boolean isPurchased) {
            return new SQL() {
                {
                    SELECT("*");
                    FROM("books");
                    JOIN("categories on books.category_id = categories.category_id");
                    if (category != null) {
                        WHERE("category like concat('%',#{category},'%')");
                    }
                    if (name != null) {
                        WHERE("name like concat('%',#{name},'%')");
                    }
                    if (isPurchased != null) {
                        WHERE("is_purchased = #{isPurchased}");
                    }
                }
            }.toString();
        }
    }

    @Select("select * from books join categories on books.category_id = categories.category_id where book_id = #{bookId}")
    Optional<Book> findByBookId(int bookId);

    @Select("select * from categories where category_id = #{categoryId}")
    Optional<Category> findByCategoryId(int categoryId);

    @Select("select * from books where name like #{name} and category_id like #{categoryId}")
    Optional<Book> findByNameAndCategory(String name, int categoryId);

    @Select("select * from categories where category like #{category}")
    Optional<Category> findByCategory(String category);

    @Insert("insert into books (name, release_date, is_purchased, category_id) values (#{name}, #{releaseDate}, #{isPurchased}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "bookId")
    void insertBook(Book book);

    @Insert("insert into categories (category) values (#{category})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId")
    void insertCategory(Category category);
}
