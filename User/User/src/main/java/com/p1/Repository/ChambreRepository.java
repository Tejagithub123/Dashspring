package com.p1.Repository;

import com.p1.Model.Chambre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    List<Chambre> findByFoyerId(Long foyerId);
}
