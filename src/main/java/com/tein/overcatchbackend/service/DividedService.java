package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.DividedDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.mapper.DividedMapper;
import com.tein.overcatchbackend.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class DividedService {

    private final DividedRepository dividedRepository;
    private final DividedMapper dividedMapper;
    private final DirectorDetailRepository directorDetailRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final FileStorageService fileStorageService;




    public Divided save(Divided divided,ClientDTO clientDTO, Client client )throws FileNotFoundException, JRException{

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        divided.setFileName(uuidAsString+".pdf");
        divided.setCurrency("GBP");
        divided.setIsActive(true);
        divided.setDividedEndDate(divided.getDividedEndDate());
        Divided divided1 = dividedRepository.save(divided);
        divided1.setDividedEndDate(divided1.getDividedEndDate());

        String path = "/Tein/test";


        Resource r = fileStorageService.loadFileAsResource("users.jrxml");
        File file = null;
        try {
            file = r.getFile();
        } catch (IOException e) {
            System.out.println("****** FİLE NOHUT FOUND");
        }

        //File file = ResourceUtils.getFile("C:\\Tein\\test\\users.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport("/Tein/test/users.jrxml");



        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(divided1));



        Map<String, Object> parameters = new HashMap<>();
        parameters.put("companyName", clientDTO.getClientName());
        parameters.put("name", clientDTO.getClientName()); // burası değişecek
        parameters.put("city", client.getAddressList().get(0).getCity());
        parameters.put("country", client.getAddressList().get(0).getCountry());
        parameters.put("district", client.getAddressList().get(0).getDistrict());
        parameters.put("street", client.getAddressList().get(0).getStreet());
        parameters.put("number", client.getAddressList().get(0).getNumber());


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\"+uuidAsString+".pdf");

        return divided1;
    }

    public List<Divided> getAll (){
        List<Divided> divideds = dividedRepository.findAll();
        int i = 0;
        for(Divided d : divideds){
            d.setDividedEndDate(divideds.get(i).getDividedEndDate());
            i++;
        }
        return divideds;
    }

    public DividedDTO getByDirectorId(Long directorId){
        Divided divided = dividedRepository.findDividedBy(directorId);
        DividedDTO dividedDTO = dividedMapper.toDto(divided);
        dividedDTO.setDividedEndDate(divided.getDividedEndDate());
        return dividedDTO;
    }

    public ResponseEntity<?> getAllDirectors(){
        try {
            List<Integer> directorIds = dividedRepository.getAllDirectors();
            List<Object> allUsersAndDirectors = new ArrayList<>();

            List<DirectorDetail> directorDetails = directorDetailRepository.findAllByListDirectorId(directorIds);
            for(DirectorDetail d:directorDetails){
                directorIds.remove(d.getId());
                allUsersAndDirectors.add(d);
            }

            List<User> customers = userRepository.findAllByWithoutListDirectorId(directorIds);
            for(User user : customers){
                allUsersAndDirectors.add(user);
            }

            return ResponseEntity.ok(allUsersAndDirectors);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getAllCompanyByDirectorId(){
        try {
            List<Integer> directorIds = dividedRepository.getAllDirectors();
            List<Object> allCompany = new ArrayList<>();
            List<Client> allCompanyWithDirectors = new ArrayList<>();
            List<Client> allCompanyWithUsers = new ArrayList<>();
            List<String> directorList = new ArrayList<>();

            List<DirectorDetail> directorDetails = directorDetailRepository.findAllByListDirectorId(directorIds);
            for(DirectorDetail d:directorDetails){
                directorIds.remove(d.getId());
                if(d.getCode()!=null) {
                    directorList.add(d.getCode().substring(0, 5));
                }
            }
            allCompanyWithDirectors = clientRepository.findAllCompanyWithDirectorList(directorList);
            allCompanyWithUsers = clientRepository.findAllCompanyWithUsers(directorIds);
            for(Client c:allCompanyWithDirectors){
                ClientDTO clientDTO = clientMapper.toDto(c);
                allCompany.add(clientDTO);
            }
            for(Client c:allCompanyWithUsers){
                ClientDTO clientDTO = clientMapper.toDto(c);
                allCompany.add(clientDTO);
            }
            return ResponseEntity.ok(allCompany);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
