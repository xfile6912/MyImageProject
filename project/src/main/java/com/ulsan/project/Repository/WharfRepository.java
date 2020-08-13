package com.ulsan.project.Repository;

import com.ulsan.project.Model.Entity.Wharf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WharfRepository extends JpaRepository<Wharf, Long> {
}
