package pl.coderslab.charity.institution;

import java.util.List;

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
