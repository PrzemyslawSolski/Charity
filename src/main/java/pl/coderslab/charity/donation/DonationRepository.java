package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.institution.Institution;

import java.util.Set;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT COALESCE(SUM(d.quantity),0) FROM Donation d")
    public long getTotalQuantity();

    @Query("select d.institution from Donation d")
    Set<Institution> getDonatedInstitutions();
}
