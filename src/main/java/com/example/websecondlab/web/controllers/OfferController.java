package com.example.websecondlab.web.controllers;

import com.example.websecondlab.services.OfferService;
import com.example.websecondlab.web.view.OffersDemoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping("")
    public String getAllOffers(Model model) {
        List<OffersDemoView> allOffers = offerService.getAllOffers();
        model.addAttribute("allOffers", allOffers);
        allOffers.forEach(System.out::println);
        return "all-offers";
    }

    @GetMapping("/by-mileage")
    public String getAllOffersByMileageLowerThan(@RequestParam int mileage, Model model) {
        List<OffersDemoView> allOffersByMileageLowerThan = offerService.getAllOffersByMileageLowerThan(mileage);
        model.addAttribute("allOffers", allOffersByMileageLowerThan);
        allOffersByMileageLowerThan.forEach(System.out::println);
        return "all-offers-by-mileage";
    }

    @GetMapping("/by-price")
    public String getAllOffersByPriceLowerThan(@RequestParam BigDecimal price, Model model) {
        List<OffersDemoView> allOffersByPriceLowerThan = offerService.getAllOffersByPriceLowerThan(price);
        model.addAttribute("allOffers", allOffersByPriceLowerThan);
        allOffersByPriceLowerThan.forEach(System.out::println);
        return "all-offers-by-price";
    }

    @GetMapping("/by-engine")
    public String getAllOffersByEngineType(@RequestParam String engineType, Model model) {
        List<OffersDemoView> allOffersByEngineType = offerService.getAllOffersByEngineType(engineType);
        model.addAttribute("allOffers", allOffersByEngineType);
        allOffersByEngineType.forEach(System.out::println);
        return "all-offers-by-engine";
    }

    @GetMapping("/by-transmission")
    public String getAllOffersByTransmissionType(@RequestParam String transmissionType, Model model) {
        List<OffersDemoView> allOffersByTransmissionType = offerService.getAllOffersByTransmissionType(transmissionType);
        model.addAttribute("allOffers", allOffersByTransmissionType);
        allOffersByTransmissionType.forEach(System.out::println);
        return "all-offers-by-transmission";
    }
}