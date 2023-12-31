package net.javaguides.springboot.controller;

import java.util.List;

import net.javaguides.springboot.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.service.LibraryService;

@Controller
public class LibraryController {

	@Autowired
	private LibraryService libraryService;
	
	// display list of books
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "bookName", "asc", model);
	}
	
	@GetMapping("/showNewBookForm")
	public String showNewBookForm(Model model) {
		// create model attribute to bind form data
		Library library = new Library();
		model.addAttribute("book", library);
		return "new_book";
	}
	
	@PostMapping("/saveBook")
	public String saveBook(@ModelAttribute("book") Library library) {
		// save book to database
		libraryService.saveBook(library);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get book from the service
		Library library = libraryService.getBookById(id);
		
		// set book as a model attribute to pre-populate the form
		model.addAttribute("book", library);
		return "update_book";
	}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable (value = "id") long id) {
		
		// call delete book method
		this.libraryService.deleteBookById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Library> page = libraryService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Library> listLibraries = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listBooks", listLibraries);
		return "index";
	}
}
