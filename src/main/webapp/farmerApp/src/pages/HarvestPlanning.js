import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './HarvestPlanning.module.css';
import BottomNav from '../components/BottomNav';

function HarvestPlanning() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('harvest');
  const [selectedDate, setSelectedDate] = useState(4); // Default to first date

  const dates = [
    { day: 4, label: 'Tue' },
    { day: 5, label: 'Wed' },
    { day: 6, label: 'Thu' },
    { day: 7, label: 'Fri' },
    { day: 8, label: 'Sat' },
    { day: 9, label: 'Sun' },
    { day: 10, label: 'Mon' },
  ];

  const crops = [
    { id: 1, name: 'Potato', price: '₹28.13/kg', image: '🥔', required: 100 },
    { id: 2, name: 'Cauliflower', price: '₹28.13/kg', image: '🥦', required: 100 },
    { id: 3, name: 'Peas', price: '₹28.13/kg', image: '🫛', required: 100 },
    { id: 4, name: 'Onion', price: '₹28.13/kg', image: '🧅', required: 100 },
    { id: 5, name: 'Orange', price: '₹28.13/kg', image: '🍊', required: 100 },
    { id: 6, name: 'Apple', price: '₹28.13/kg', image: '🍎', required: 100 },
  ];

  const [quantities, setQuantities] = useState(
    crops.reduce(
      (acc, crop) => ({
        ...acc,
        [crop.id]: { planned: 0, confirmed: 0 },
      }),
      {},
    ),
  );

  const handleQuantityChange = (cropId, type, value) => {
    setQuantities(prev => ({
      ...prev,
      [cropId]: { ...prev[cropId], [type]: parseInt(value) || 0 },
    }));
  };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <div className={styles.location}>
          <span>Deliver to Kumar - Mumbai 400006</span>
          <button>Change</button>
        </div>
        <div className={styles.tabs}>
          <button className={activeTab === 'harvest' ? styles.active : ''} onClick={() => setActiveTab('harvest')}>
            Harvest Planning
          </button>
          <button className={activeTab === 'demand' ? styles.active : ''} onClick={() => setActiveTab('demand')}>
            Demand
          </button>
        </div>
      </header>

      <main>
        {activeTab === 'harvest' ? (
          <div>
            <div className={styles['date-selector']}>
              {dates.map(date => (
                <button key={date.day} className={selectedDate === date.day ? styles.active : ''} onClick={() => setSelectedDate(date.day)}>
                  <span className={styles.day}>{date.day}</span>
                  <span className={styles.label}>{date.label}</span>
                </button>
              ))}
            </div>

            <div className={styles['crops-list']}>
              {crops.map(crop => (
                <div key={crop.id} className={styles['crop-card']}>
                  <div className={styles['crop-info']}>
                    <span className={styles['crop-image']}>{crop.image}</span>
                    <div className={styles['crop-details']}>
                      <span className={styles['crop-name']}>{crop.name}</span>
                      <span className={styles['crop-price']}>{crop.price}</span>
                    </div>
                  </div>
                  <div className={styles['quantity-input']}>
                    <label>Qty</label>
                    <input
                      type="number"
                      value={quantities[crop.id].planned}
                      onChange={e => handleQuantityChange(crop.id, 'planned', e.target.value)}
                      min="0"
                    />
                  </div>
                </div>
              ))}
            </div>
          </div>
        ) : (
          <div>
            <div className={styles['demand-header']}>
              <h2>Demand for tomorrow</h2>
              <span className={styles.date}>5.10.2024</span>
            </div>

            <div className={styles['crops-list']}>
              {crops.map(crop => (
                <div key={crop.id} className={styles['crop-card']}>
                  <div className={styles['crop-info']}>
                    <span className={styles['crop-image']}>{crop.image}</span>
                    <div className={styles['crop-details']}>
                      <span className={styles['crop-name']}>{crop.name}</span>
                      <span className={styles['crop-price']}>{crop.price}</span>
                    </div>
                  </div>
                  <div className={styles['quantity-details']}>
                    <div className={styles['quantity-row']}>
                      <span>Planned</span>
                      <span>{quantities[crop.id].planned} kg</span>
                    </div>
                    <div className={styles['quantity-row']}>
                      <span>Required</span>
                      <span>{crop.required} kg</span>
                    </div>
                    <div className={styles['quantity-row']}>
                      <span>Confirmed</span>
                      <input
                        type="number"
                        value={quantities[crop.id].confirmed}
                        onChange={e => handleQuantityChange(crop.id, 'confirmed', e.target.value)}
                        min="0"
                      />
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </main>

      <BottomNav />
    </div>
  );
}

export default HarvestPlanning;
