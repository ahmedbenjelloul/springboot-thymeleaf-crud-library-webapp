package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Library;

public interface LibraryService {
	List<Library> getAllBooks();
	void saveBook(Library library);
	Library getBookById(long id);
	void deleteBookById(long id);
	Page<Library> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
