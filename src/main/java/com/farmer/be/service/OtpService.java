package com.farmer.be.service;

import com.farmer.be.service.dto.OtpDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Otp}.
 */
public interface OtpService {
    /**
     * Save a otp.
     *
     * @param otpDTO the entity to save.
     * @return the persisted entity.
     */
    OtpDTO save(OtpDTO otpDTO);

    /**
     * Updates a otp.
     *
     * @param otpDTO the entity to update.
     * @return the persisted entity.
     */
    OtpDTO update(OtpDTO otpDTO);

    /**
     * Partially updates a otp.
     *
     * @param otpDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OtpDTO> partialUpdate(OtpDTO otpDTO);

    /**
     * Get all the otps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OtpDTO> findAll(Pageable pageable);

    /**
     * Get the "id" otp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OtpDTO> findOne(Long id);

    /**
     * Delete the "id" otp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
