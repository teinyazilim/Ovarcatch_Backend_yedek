package com.tein.overcatchbackend.resource;

//import com.tein.overcatchbackend.service.CreateMockDataService;
import com.tein.overcatchbackend.service.ExistCompanyExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/mock")
@RequiredArgsConstructor
public class MockDataRepository {

//    private final CreateMockDataService createMockDataService;
    private final ExistCompanyExportService existCompanyExportService;
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public boolean getUser() {
//       createMockDataService.createMockData();
        return true;
    }

    @RequestMapping(value = "/existcompany", method = RequestMethod.GET)
    public boolean getCompany() throws IOException {
        existCompanyExportService.companyExcelExport();
        return true;
    }
}
