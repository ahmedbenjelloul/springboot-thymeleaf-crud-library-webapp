package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>{

}
