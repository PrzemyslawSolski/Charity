package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT COALESCE(SUM(d.quantity),0) FROM Donation d")
    public long getTotalQuantity();
}
