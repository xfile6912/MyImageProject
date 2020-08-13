package com.ulsan.project.Repository;

import com.ulsan.project.Model.Entity.UseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UseInfoRepository  extends JpaRepository<UseInfo, Long> {
}
