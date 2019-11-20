package pl.coderslab.charity.institution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public void save(Institution institution) {
        institutionRepository.save(institution);
    }

    public Institution getOne(long id){
        return institutionRepository.getOne(id);
    }

    public List<Institution> findAll(){
        return institutionRepository.findAll();
    }

    public void deleteById(long id){
        institutionRepository.deleteById(id);
    }
}
