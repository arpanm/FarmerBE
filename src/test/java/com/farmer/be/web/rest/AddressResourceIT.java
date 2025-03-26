package com.farmer.be.web.rest;

import static com.farmer.be.domain.AddressAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Address;
import com.farmer.be.repository.AddressRepository;
import com.farmer.be.service.dto.AddressDTO;
import com.farmer.be.service.mapper.AddressMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressResourceIT {

    private static final String DEFAULT_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Long DEFAULT_PINCODE = 1L;
    private static final Long UPDATED_PINCODE = 2L;

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LON = 1F;
    private static final Float UPDATED_LON = 2F;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    private Address insertedAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity() {
        return new Address()
            .line1(DEFAULT_LINE_1)
            .line2(DEFAULT_LINE_2)
            .landmark(DEFAULT_LANDMARK)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .pincode(DEFAULT_PINCODE)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .isActive(DEFAULT_IS_ACTIVE)
            .createddBy(DEFAULT_CREATEDD_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity() {
        return new Address()
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .landmark(UPDATED_LANDMARK)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .pincode(UPDATED_PINCODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        address = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAddress != null) {
            addressRepository.delete(insertedAddress);
            insertedAddress = null;
        }
    }

    @Test
    @Transactional
    void createAddress() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        var returnedAddressDTO = om.readValue(
            restAddressMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AddressDTO.class
        );

        // Validate the Address in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAddress = addressMapper.toEntity(returnedAddressDTO);
        assertAddressUpdatableFieldsEquals(returnedAddress, getPersistedAddress(returnedAddress));

        insertedAddress = returnedAddress;
    }

    @Test
    @Transactional
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLine1IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setLine1(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setState(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setCountry(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPincodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setPincode(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setCreateddBy(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setCreatedTime(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setUpdatedBy(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        address.setUpdatedTime(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].line1").value(hasItem(DEFAULT_LINE_1)))
            .andExpect(jsonPath("$.[*].line2").value(hasItem(DEFAULT_LINE_2)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAddress() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.line1").value(DEFAULT_LINE_1))
            .andExpect(jsonPath("$.line2").value(DEFAULT_LINE_2))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddress() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.saveAndFlush(address);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .landmark(UPDATED_LANDMARK)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .pincode(UPDATED_PINCODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAddressToMatchAllProperties(updatedAddress);
    }

    @Test
    @Transactional
    void putNonExistingAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(longCount.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(longCount.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(longCount.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.saveAndFlush(address);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .lon(UPDATED_LON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddressUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAddress, address), getPersistedAddress(address));
    }

    @Test
    @Transactional
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.saveAndFlush(address);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .landmark(UPDATED_LANDMARK)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .pincode(UPDATED_PINCODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAddressUpdatableFieldsEquals(partialUpdatedAddress, getPersistedAddress(partialUpdatedAddress));
    }

    @Test
    @Transactional
    void patchNonExistingAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(longCount.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(longCount.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddress() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        address.setId(longCount.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddress() throws Exception {
        // Initialize the database
        insertedAddress = addressRepository.saveAndFlush(address);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the address
        restAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return addressRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Address getPersistedAddress(Address address) {
        return addressRepository.findById(address.getId()).orElseThrow();
    }

    protected void assertPersistedAddressToMatchAllProperties(Address expectedAddress) {
        assertAddressAllPropertiesEquals(expectedAddress, getPersistedAddress(expectedAddress));
    }

    protected void assertPersistedAddressToMatchUpdatableProperties(Address expectedAddress) {
        assertAddressAllUpdatablePropertiesEquals(expectedAddress, getPersistedAddress(expectedAddress));
    }
}
