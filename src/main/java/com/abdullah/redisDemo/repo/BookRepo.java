package com.abdullah.redisDemo.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.abdullah.redisDemo.model.Book;

import ch.qos.logback.classic.Logger;

@Repository
public class BookRepo {

	@Autowired
	private RedisTemplate template;

	public static final String Hash_Key = "Book";

	public Book save(Book book) {
		template.opsForHash().put(Hash_Key, book.getBookId(), book);
		return book;
	}
	
	public List<Book> allBooks() {
		return template.opsForHash().values(Hash_Key);
	}

	public Book singleBook(int id) {
		System.out.println("Request go to DB");
		return (Book) template.opsForHash().get(Hash_Key, id);
	}

	public String deleteBook(int id) {
		template.opsForHash().delete(Hash_Key, id);
		return "Book deleted";
	}

	public void put(int id, Book book) {
		
		template.opsForHash().put(Hash_Key, id, book);
	}

}
