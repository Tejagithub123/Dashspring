package com.p1.Repository;

import com.p1.Model.Etudiant;
import com.p1.Model.Foyer;
import com.p1.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
