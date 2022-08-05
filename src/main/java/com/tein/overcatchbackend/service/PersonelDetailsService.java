package com.tein.overcatchbackend.service;
import com.tein.overcatchbackend.domain.model.Personel;
import com.tein.overcatchbackend.repository.PersonelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonelDetailsService {

    private PersonelRepository personelRepository;

    public List<Personel> getPersonel(){
        List<Personel> PersonelDetail = personelRepository.findPersonelName();
        return PersonelDetail;
    }
    public void savePersonel(Personel personel) {
        Personel personel1 = new Personel();
        personel1.setUser(personel.getUser());
        personel1.setIsActive(true);
        personelRepository.save(personel1);
    }

}