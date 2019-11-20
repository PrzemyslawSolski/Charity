package pl.coderslab.charity.institution;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public void save(Institution institution) {
        institutionRepository.save(institution);
    }

    public Institution getOne(long id){
        return institutionRepository.getOne(id);
    }

    public List<Institution> institutions(){
        return institutionRepository.findAll();
    }

    public void deleteById(long id){
        institutionRepository.deleteById(id);
    }
}
