package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    //http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> findAllCountries() {
        List<Country> countries = new ArrayList<>();

        countryRepository.findAll().iterator().forEachRemaining(c -> countries.add(c));

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    //http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> findByName(@PathVariable char letter) {
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countryList::add);
        List<Country> filteredList = filterCountries(countryList,
                (c) ->c.getName().charAt(0) == letter);
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }
    private List<Country> filterCountries(List<Country> countryList, CheckCountries tester) {
        List<Country> rtnList = new ArrayList<>();
        for(Country c : countryList) {
            if (tester.test(c)) {
                rtnList.add(c);
            }
        }
        return rtnList;
    }
    //http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> getTotal() {
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining((countryList::add));

        long total = 0;
        for (Country c : countryList) {
            total += c.getPopulation();
        }
        System.out.println("The Total Population is: " + total);

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    //http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> getMin() {
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining((countryList::add));

        countryList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));

        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    //http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> getMax() {
        List<Country> countryList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining((countryList::add));
        int maxPop = countryList.size() - 1;

        countryList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        System.out.println(maxPop);
        return new ResponseEntity<>(countryList.get(maxPop), HttpStatus.OK);
    }


    //stretch
    //http://localhost:2019/population/median
}
