package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.ClientCode;
import com.tein.overcatchbackend.repository.ClientCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ClientCodeService {

    private final ClientCodeRepository clientCodeRepository;

    public ClientCode getBySeqName(String seqName){
        return clientCodeRepository.findBySeqName(seqName);
    }

    public String save(String seqName){
        String value;
        ClientCode clientCode = getBySeqName(seqName);
        if ( clientCode != null){
            value = clientCode.getSeqValue();
            clientCode.setSeqValue(String.format("%03d", Long.parseLong(clientCode.getSeqValue()) + 1));
        }else{
            clientCode = new ClientCode();
            clientCode.setSeqName(seqName);
            clientCode.setSeqValue(String.format("%03d", 1));
        }
        clientCodeRepository.save(clientCode);
        return clientCode.getSeqName() + clientCode.getSeqValue();
    }

}
