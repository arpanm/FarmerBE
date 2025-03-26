import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './SelectProduce.module.css';

function SelectProduce() {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');

  const allCrops = [
    { id: 5, name: 'Orange', image: '🍊', selected: false },
    { id: 6, name: 'Apple', image: '🍎', selected: false },
    { id: 7, name: 'Banana', image: '🍌', selected: false },
    { id: 8, name: 'Pineapple', image: '🍍', selected: false },
    { id: 1, name: 'Potato', image: '🥔', selected: true },
    { id: 2, name: 'Cauliflower', image: '🥦', selected: true },
    { id: 3, name: 'Peas', image: '🫛', selected: true },
    { id: 4, name: 'Onion', image: '🧅', selected: true },
  ];

  const [crops, setCrops] = useState(allCrops);
  const [showToast, setShowToast] = useState(false);

  useEffect(() => {
    let toastTimer;
    if (showToast) {
      toastTimer = setTimeout(() => {
        setShowToast(false);
      }, 1000);
    }
    return () => clearTimeout(toastTimer);
  }, [showToast]);

  const handleSearch = e => {
    setSearchQuery(e.target.value);
    const filtered = allCrops.filter(crop => crop.name.toLowerCase().includes(e.target.value.toLowerCase()));
    setCrops(filtered);
  };

  const handleCropToggle = id => {
    setCrops(crops.map(crop => (crop.id === id ? { ...crop, selected: !crop.selected } : crop)));
    setShowToast(true);
  };

  const selectedCrops = crops.filter(crop => crop.selected);
  const unselectedCrops = crops.filter(crop => !crop.selected);

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <h1>Select produce</h1>
        <p>Select the produced crops you want to sell.</p>
      </header>

      <div className={styles.searchContainer}>
        <input type="text" placeholder="Search crop" value={searchQuery} onChange={handleSearch} className={styles.searchInput} />
        <button className={styles.voiceButton}>🎤</button>
      </div>

      <div className={styles.cropList}>
        {crops.map(crop => (
          <div key={crop.id} className={styles.cropItem}>
            <label className={styles.checkboxLabel}>
              <input type="checkbox" checked={crop.selected} onChange={() => handleCropToggle(crop.id)} className={styles.checkbox} />
              <span className={styles.cropImage}>{crop.image}</span>
              <span className={styles.cropName}>{crop.name}</span>
            </label>
          </div>
        ))}
      </div>

      {showToast && selectedCrops.length > 0 && (
        <div className={styles.toast}>
          <span className={styles.checkmark}>✓</span>
          Your request to add more crop types has been recorded successfully.
        </div>
      )}

      <div className={styles.buttonContainer}>
        <button className={styles.closeButton} onClick={() => navigate(-1)}>
          Close
        </button>
        <button className={styles.addButton} onClick={() => navigate(-1)}>
          Add
        </button>
      </div>
    </div>
  );
}

export default SelectProduce;
