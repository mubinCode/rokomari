package com.abdullah.redisDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdullah.redisDemo.model.Book;
import com.abdullah.redisDemo.repo.BookRepo;

@RestController
@EnableCaching
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookRepo repo;
	
	@PostMapping
	public Book save(@RequestBody Book book) {
		return repo.save(book);
	}
	
	@PutMapping("/{id}")
	@CachePut(key="#id", value="Book")
	public void update(@PathVariable int id, @RequestBody Book book) {
		System.out.println("id : "+id+"book : "+book);
		repo.put(id, book);
	}
	
	@GetMapping
	public List<Book> allBooks(){
		return repo.allBooks();
	}
	@GetMapping("/{id}")
	@Cacheable(key="#id", value="Book", unless="#result.bookPrice > 500")
	public Book singlebook(@PathVariable int id) {
		return repo.singleBook(id);
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(key="#id", value="Book")
	public String delete(@PathVariable int id) {
		return repo.deleteBook(id);
	}

}
