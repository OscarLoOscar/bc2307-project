package com.hkjava.demo.demofinnhub.service;

import com.hkjava.demo.demofinnhub.exception.FinnhubException;
import com.hkjava.demo.demofinnhub.model.APImodel.CompanyProfile2DTO;

public interface CompanyService {

  CompanyProfile2DTO getCompanyProfile(String symbol) throws FinnhubException;

  // void refresh() throws FinnhubException;
  // return CompanyProfile又得，void 又得 , seems like PutMapping

}
