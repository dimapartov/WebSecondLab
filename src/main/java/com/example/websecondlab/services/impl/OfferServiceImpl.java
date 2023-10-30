package com.example.websecondlab.services.impl;

import com.example.websecondlab.repositories.ModelRepository;
import com.example.websecondlab.repositories.UserRepository;
import com.example.websecondlab.services.ModelService;
import com.example.websecondlab.services.dtos.ModelDTO;
import com.example.websecondlab.services.dtos.OfferDTO;
import com.example.websecondlab.models.Offer;
import com.example.websecondlab.repositories.OfferRepository;
import com.example.websecondlab.services.OfferService;
import com.example.websecondlab.consts.enums.EngineTypeEnum;
import com.example.websecondlab.consts.enums.TransmissionTypeEnum;
import com.example.websecondlab.services.dtos.UserDTO;
import com.example.websecondlab.web.view.OffersDemoView;
import com.example.websecondlab.web.view.user_input.CreateOfferViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final ModelMapper modelMapper;
    private OfferRepository offerRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public OfferServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    @Override
    public void addOffer(OfferDTO offerDTO) {
        Offer offer = modelMapper.map(offerDTO, Offer.class);
        offerRepository.saveAndFlush(offer);
    }

    @Override
    public void deleteOfferById(long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public List<OfferDTO> getOffersByMileageLowerThan(int mileage) {
        return offerRepository.findAllByMileageLessThan(mileage)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDTO> getOffersByPriceLowerThan(BigDecimal price) {
        return offerRepository.findAllByPriceLessThan(price)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDTO> getOffersByEngineType(String engineType) {
        return offerRepository.findAllByEngineType(EngineTypeEnum.valueOf(engineType.toUpperCase()))
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDTO> getOffersByTransmissionType(String transmissionType) {
        return offerRepository.findAllByTransmissionType(TransmissionTypeEnum.valueOf(transmissionType.toUpperCase()))
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDTO> getOffersBySeller(String username) {
        return offerRepository.findAllBySellerUsername(username)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }
//----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<OffersDemoView> getAllOffers() {

        List<OfferDTO> allOffersDtoList = offerRepository.findAll()
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .toList();

        List<OffersDemoView> allOffersDemoView = new ArrayList<>();

        for (OfferDTO offerDto : allOffersDtoList) {

            OffersDemoView offerDemoView = modelMapper.map(offerDto, OffersDemoView.class);

            offerDemoView.setModel(offerDto.getModel().getName());
            offerDemoView.setBrand(offerDto.getModel().getBrand().getName());
            offerDemoView.setSeller(offerDto.getSeller().getUsername());
            allOffersDemoView.add(offerDemoView);
        }
        return allOffersDemoView;
    }
//----------------------------------------------------------------------------------------------------------------------
//  ПЕРЕДЕЛАТЬ ПО ПРИМЕРУ ВЫШЕ
    @Override
    public List<OffersDemoView> getAllOffersByMileageLowerThan(int mileage) {
        return offerRepository.findAllByMileageLessThan(mileage)
                .stream()
                .map(offer -> modelMapper.map(offer, OffersDemoView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OffersDemoView> getAllOffersByPriceLowerThan(BigDecimal price) {
        return offerRepository.findAllByPriceLessThan(price)
                .stream()
                .map(offer -> modelMapper.map(offer, OffersDemoView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OffersDemoView> getAllOffersByEngineType(String engineType) {
        return offerRepository.findAllByEngineType(EngineTypeEnum.valueOf(engineType.toUpperCase()))
                .stream()
                .map(offer -> modelMapper.map(offer, OffersDemoView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OffersDemoView> getAllOffersByTransmissionType(String transmissionType) {
        return offerRepository.findAllByTransmissionType(TransmissionTypeEnum.valueOf(transmissionType.toUpperCase()))
                .stream()
                .map(offer -> modelMapper.map(offer, OffersDemoView.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createOffer(CreateOfferViewModel newOffer) {
        OfferDTO newOfferDTO = modelMapper.map(newOffer, OfferDTO.class);
        ModelDTO modelDTO = modelMapper.map(modelRepository.findModelByName(newOffer.getModel()), ModelDTO.class);
        UserDTO userDTO = modelMapper.map(userRepository.findUserByUsername(newOffer.getSeller()), UserDTO.class);
        newOfferDTO.setModel(modelDTO);
        newOfferDTO.setSeller(userDTO);
//        newOfferDTO.setEngineType(EngineTypeEnum.valueOf(newOffer.getEngineType()));
        newOfferDTO.setEngineType(EngineTypeEnum.DIESEL);
//        newOfferDTO.setTransmissionType(TransmissionTypeEnum.valueOf(newOffer.getTransmissionType()));
        newOfferDTO.setTransmissionType(TransmissionTypeEnum.AUTOMATIC);
        offerRepository.saveAndFlush(modelMapper.map(newOfferDTO,Offer.class));
    }
}