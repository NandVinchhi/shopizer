package com.salesmanager.shop.store.api.v1.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;

/**
 * Client for making REST API calls to CustomerAPI
 */
@Component
public class CustomerApiClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerFacade customerFacade;

    @Value("${api.url:http://localhost:8080}")
    private String apiUrl;

    /**
     * Get customer by ID using REST API call
     * 
     * @param id            Customer ID
     * @param merchantStore Store
     * @param language      Language
     * @return Customer object
     */
    public Customer getCustomerById(Long id, MerchantStore merchantStore, Language language) {
        if (id == null) {
            return null;
        }

        try {
            // Make REST API call to get customer
            String url = apiUrl + "/api/v1/private/customer/" + id + "?store=" + merchantStore.getCode();
            ReadableCustomer readableCustomer = restTemplate.getForObject(url, ReadableCustomer.class);

            if (readableCustomer == null) {
                return null;
            }

            // Convert ReadableCustomer to Customer domain object
            return customerFacade.getCustomerByUserName(readableCustomer.getUserName(), merchantStore);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Customer with id [" + id + "] not found");
        }
    }
}