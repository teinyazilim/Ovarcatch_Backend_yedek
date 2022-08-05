package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.Divided;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.repository.DividedRepository;
import com.tein.overcatchbackend.repository.UserRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private DividedRepository dividedRepository;


}
