package com.farmer.be.service.mapper;

import com.farmer.be.domain.Address;
import com.farmer.be.domain.BankDetails;
import com.farmer.be.domain.Document;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.Farmer;
import com.farmer.be.domain.FieldVisit;
import com.farmer.be.domain.PanDetails;
import com.farmer.be.service.dto.AddressDTO;
import com.farmer.be.service.dto.BankDetailsDTO;
import com.farmer.be.service.dto.DocumentDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.FarmerDTO;
import com.farmer.be.service.dto.FieldVisitDTO;
import com.farmer.be.service.dto.PanDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    @Mapping(target = "farm", source = "farm", qualifiedByName = "farmId")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "panDetails", source = "panDetails", qualifiedByName = "panDetailsId")
    @Mapping(target = "bankDetails", source = "bankDetails", qualifiedByName = "bankDetailsId")
    @Mapping(target = "fieldVisit", source = "fieldVisit", qualifiedByName = "fieldVisitId")
    DocumentDTO toDto(Document s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("panDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PanDetailsDTO toDtoPanDetailsId(PanDetails panDetails);

    @Named("bankDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankDetailsDTO toDtoBankDetailsId(BankDetails bankDetails);

    @Named("fieldVisitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldVisitDTO toDtoFieldVisitId(FieldVisit fieldVisit);
}
