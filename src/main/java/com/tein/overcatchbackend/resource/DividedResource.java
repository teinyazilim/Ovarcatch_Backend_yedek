package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.DividedDTO;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Divided;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.service.DividedService;
import com.tein.overcatchbackend.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/divided")
@RequiredArgsConstructor
public class DividedResource {
    private final DividedService dividedService;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final FileStorageService fileStorageService;


    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
      /*  HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "document.pdf");
        headers.add("content-disposition", "inline;filename=" + fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");*/

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    /*@RequestMapping("/file/{fileName:.+}")
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("fileName") String fileName) throws IOException {

        File file = new File(EXTERNAL_FILE_PATH + fileName);
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        }

    }*/

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Divided saveDivided(@RequestBody Divided divided,
                               @RequestParam("clientId") String clientId) throws JRException, FileNotFoundException{

        Client client= clientRepository.findByClientIdByAddress(Long.parseLong(clientId)).get();
        System.out.println("******Client"+client);
        ClientDTO clientDTO=clientMapper.toDto(client);
        System.out.println("******Client DTO"+clientDTO);
        divided = dividedService.save(divided, clientDTO, client);
        return divided;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Divided> getAll (){
         List<Divided> divideds = dividedService.getAll();
        return divideds;
    }
    @RequestMapping(value = "/getAllDirectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDirectors(){
        try {
            return ResponseEntity.ok(dividedService.getAllDirectors());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getAllClient", method = RequestMethod.GET)
    public ResponseEntity<?> getAllClient(){
        try {
            return ResponseEntity.ok(dividedService.getAllCompanyByDirectorId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getByDirectorId", method = RequestMethod.GET)
    public DividedDTO getByDirectorId (@RequestParam("directorId") String directorId){
        if(directorId == null  || directorId.equals("undefined") || directorId.equals("null")) {
            directorId = "0";
        }
        DividedDTO dividedDTO = dividedService.getByDirectorId(Long.valueOf(directorId));

        return dividedDTO;
    }
}
