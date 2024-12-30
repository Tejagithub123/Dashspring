package com.p1.Repository;

import com.p1.Model.Etudiant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

}
