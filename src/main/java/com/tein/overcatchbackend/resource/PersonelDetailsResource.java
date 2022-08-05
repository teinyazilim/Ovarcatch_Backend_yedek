package com.tein.overcatchbackend.resource;
import com.tein.overcatchbackend.domain.model.Personel;
import com.tein.overcatchbackend.domain.model.TaskConfirmation;
import com.tein.overcatchbackend.service.PersonelDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personeldetails")
@RequiredArgsConstructor
public class PersonelDetailsResource {
    private final PersonelDetailsService personelDetailsService;

    @RequestMapping(value = "/getPersonel", method = RequestMethod.GET)
    public List<Personel> getPersonel() {
        return personelDetailsService.getPersonel();

    }

    }
