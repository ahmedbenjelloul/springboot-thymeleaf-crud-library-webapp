package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import net.javaguides.springboot.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.repository.LibraryRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private LibraryRepository libraryRepository;

	@Override
	public List<Library> getAllBooks() {
		return libraryRepository.findAll();
	}

	@Override
	public void saveBook(Library library) {
		this.libraryRepository.save(library);
	}

	@Override
	public Library getBookById(long id) {
		Optional<Library> optional = libraryRepository.findById(id);
		Library library = null;
		if (optional.isPresent()) {
			library = optional.get();
		} else {
			throw new RuntimeException(" Book not found for id :: " + id);
		}
		return library;
	}

	@Override
	public void deleteBookById(long id) {
		this.libraryRepository.deleteById(id);
	}

	@Override
	public Page<Library> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.libraryRepository.findAll(pageable);
	}
}
