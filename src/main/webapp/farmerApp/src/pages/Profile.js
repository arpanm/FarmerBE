import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Profile.module.css';
import BottomNav from '../components/BottomNav';

function Profile() {
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);
  const [expandedSection, setExpandedSection] = useState(null);

  const mockUserData = {
    personalDetails: {
      name: 'Kumar Suraj',
      mobileNo: '+91-986 5475 546',
      customerId: 'AB67J04V',
      panNumber: 'XXXX XXXX 0234',
      dob: '12 Jan 1990',
      email: 'govindsuraj@gmail.com',
      fatherName: 'Harbans Kumar',
      farmerType: 'Own Farm',
      address: 'Thana Vihar Phase 3, Sector 18, B Block, Ghansoli, Navi Mumbai 400708',
    },
    farmDetails: {
      farmType: 'Own Farm',
      areaInAcres: '2.5',
      address: 'Thana Vihar Phase 3, Sector 18, B Block, Ghansoli, Navi Mumbai 400708',
      selectedCrops: ['Potato', 'Cauliflower', 'Peas', 'Onion', 'Orange', 'Apple'],
      accessories: ['Core', 'tractor'],
    },
    bankDetails: {
      accountName: 'Govind Kumar',
      accountNumber: 'XXXXX5675',
      ifscCode: 'ICIC000078',
      bankName: 'State Bank of India',
      branch: 'Ghansoli sector 8',
    },
  };

  const toggleSection = section => {
    if (expandedSection === section) {
      setExpandedSection(null);
    } else {
      setExpandedSection(section);
    }
  };

  const handleSubmit = section => {
    // Handle form submission for each section
    console.log(`Submitting ${section} details`);
    setExpandedSection(null);
    setIsEditing(false);
  };

  return (
    <div className={styles.profileContainer}>
      <header className={styles.header}>
        <button className={styles.backButton} onClick={() => navigate(-1)}>
          ←
        </button>
        <h1>Profile</h1>
      </header>

      <div className={styles.profileInfo}>
        <div className={styles.avatar}>👤</div>
        <div className={styles.userInfo}>
          <h2>{mockUserData.personalDetails.name}</h2>
          <p>Mobile No: {mockUserData.personalDetails.mobileNo}</p>
          <p>Customer ID: {mockUserData.personalDetails.customerId}</p>
        </div>
        {!isEditing && (
          <button className={styles.editButton} onClick={() => setIsEditing(true)}>
            Edit
          </button>
        )}
      </div>

      {isEditing && (
        <div className={styles.editSections}>
          <div className={styles.section}>
            <button className={styles.sectionHeader} onClick={() => toggleSection('personal')}>
              Personal details
              <span className={styles.arrow}>{expandedSection === 'personal' ? '▼' : '▶'}</span>
            </button>
            {expandedSection === 'personal' && (
              <div className={styles.sectionContent}>
                <div className={styles.formGroup}>
                  <label>Name</label>
                  <input type="text" defaultValue={mockUserData.personalDetails.name} />
                </div>
                <div className={styles.formGroup}>
                  <label>Mobile No</label>
                  <input type="text" value={mockUserData.personalDetails.mobileNo} disabled />
                </div>
                <div className={styles.formGroup}>
                  <label>PAN Number</label>
                  <input type="text" defaultValue={mockUserData.personalDetails.panNumber} />
                </div>
                <div className={styles.formGroup}>
                  <label>Date of Birth</label>
                  <input type="text" defaultValue={mockUserData.personalDetails.dob} />
                </div>
                <div className={styles.formGroup}>
                  <label>Email ID</label>
                  <input type="email" defaultValue={mockUserData.personalDetails.email} />
                </div>
                <div className={styles.formGroup}>
                  <label>Father's Name</label>
                  <input type="text" defaultValue={mockUserData.personalDetails.fatherName} />
                </div>
                <div className={styles.formGroup}>
                  <label>Farmer Type</label>
                  <input type="text" defaultValue={mockUserData.personalDetails.farmerType} />
                </div>
                <div className={styles.formGroup}>
                  <label>Address</label>
                  <textarea defaultValue={mockUserData.personalDetails.address} />
                </div>
                <button className={styles.submitButton} onClick={() => handleSubmit('personal')}>
                  Submit
                </button>
              </div>
            )}
          </div>

          <div className={styles.section}>
            <button className={styles.sectionHeader} onClick={() => toggleSection('farm')}>
              Farm details
              <span className={styles.arrow}>{expandedSection === 'farm' ? '▼' : '▶'}</span>
            </button>
            {expandedSection === 'farm' && (
              <div className={styles.sectionContent}>
                <div className={styles.formGroup}>
                  <label>Farm Type</label>
                  <input type="text" defaultValue={mockUserData.farmDetails.farmType} />
                </div>
                <div className={styles.formGroup}>
                  <label>Area in Acres</label>
                  <input type="text" defaultValue={mockUserData.farmDetails.areaInAcres} />
                </div>
                <div className={styles.formGroup}>
                  <label>Farm Address</label>
                  <textarea defaultValue={mockUserData.farmDetails.address} />
                </div>
                <div className={styles.formGroup}>
                  <label>Selected Crops</label>
                  <div className={styles.chipContainer}>
                    {mockUserData.farmDetails.selectedCrops.map((crop, index) => (
                      <span key={index} className={styles.chip}>
                        {crop}
                      </span>
                    ))}
                  </div>
                </div>
                <div className={styles.formGroup}>
                  <label>Farm Accessories</label>
                  <div className={styles.chipContainer}>
                    {mockUserData.farmDetails.accessories.map((accessory, index) => (
                      <span key={index} className={styles.chip}>
                        {accessory}
                      </span>
                    ))}
                  </div>
                </div>
                <button className={styles.submitButton} onClick={() => handleSubmit('farm')}>
                  Submit
                </button>
              </div>
            )}
          </div>

          <div className={styles.section}>
            <button className={styles.sectionHeader} onClick={() => toggleSection('bank')}>
              Bank details
              <span className={styles.arrow}>{expandedSection === 'bank' ? '▼' : '▶'}</span>
            </button>
            {expandedSection === 'bank' && (
              <div className={styles.sectionContent}>
                <div className={styles.formGroup}>
                  <label>Account Name</label>
                  <input type="text" defaultValue={mockUserData.bankDetails.accountName} />
                </div>
                <div className={styles.formGroup}>
                  <label>Account Number</label>
                  <input type="text" defaultValue={mockUserData.bankDetails.accountNumber} />
                </div>
                <div className={styles.formGroup}>
                  <label>IFSC Code</label>
                  <input type="text" defaultValue={mockUserData.bankDetails.ifscCode} />
                </div>
                <div className={styles.formGroup}>
                  <label>Bank Name</label>
                  <input type="text" defaultValue={mockUserData.bankDetails.bankName} />
                </div>
                <div className={styles.formGroup}>
                  <label>Branch</label>
                  <input type="text" defaultValue={mockUserData.bankDetails.branch} />
                </div>
                <button className={styles.submitButton} onClick={() => handleSubmit('bank')}>
                  Submit
                </button>
              </div>
            )}
          </div>
        </div>
      )}
      <BottomNav />
    </div>
  );
}

export default Profile;
