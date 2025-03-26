import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Dashboard.module.css';
import BottomNav from '../components/BottomNav';

function Dashboard() {
  const navigate = useNavigate();

  const deliveryItems = [
    { id: 1, name: 'Cauliflower', quantity: '20 kg' },
    { id: 2, name: 'Potato', quantity: '50 kg' },
    { id: 3, name: 'Onion', quantity: '30 kg' },
  ];

  const priceTable = [
    { id: 1, produce: 'Potato', price: '₹28.13/kg' },
    { id: 2, produce: 'Cauliflower', price: '₹28.13/kg' },
    { id: 3, produce: 'Peas', price: '₹28.13/bundle' },
    { id: 4, produce: 'Onion', price: 'Price awaiting' },
  ];

  const tomorrowDemand = [
    { id: 1, name: 'Cauliflower', quantity: '20 kg' },
    { id: 2, name: 'Potato', quantity: '50 kg' },
    { id: 3, name: 'Onion', quantity: '30 kg' },
  ];

  const additionalProduce = [
    { id: 1, name: 'Pomegranate', image: '🍎' },
    { id: 2, name: 'Apple', image: '🍎' },
    { id: 3, name: 'Banana', image: '🍌' },
  ];

  return (
    <div className={styles.container}>
      {/* Header */}
      <header className={styles.header}>
        <button className={styles.menuButton}>
          <span className={styles.menuIcon}></span>
        </button>
        <div className={styles.searchBar}>
          <input type="text" placeholder="Search fruits, veg..." />
        </div>
        <button className={styles.cartButton}>
          <span className={styles.cartIcon}></span>
        </button>
      </header>

      {/* Main Content */}
      <main className={styles.main}>
        {/* Today's Crop Delivery */}
        <section className={styles.section}>
          <h2>Today's crop delivery</h2>
          <div className={styles.deliveryStatus}>
            <span className={styles.pendingDelivery}>Pending delivery</span>
          </div>
          <div className={styles.deliveryInfo}>
            <p>To be delivered today</p>
            <h3>100 kg</h3>
          </div>
          <div className={styles.deliveryItems}>
            {deliveryItems.map(item => (
              <div key={item.id} className={styles.deliveryItem}>
                <div className={styles.itemImage}></div>
                <div className={styles.itemDetails}>
                  <span className={styles.quantity}>{item.quantity}</span>
                  <span className={styles.name}>{item.name}</span>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* Today's Price */}
        <section className={styles.section}>
          <h2>Today's price</h2>
          <div className={styles.priceTable}>
            <table>
              <thead>
                <tr>
                  <th>Produce</th>
                  <th>Today's Price</th>
                </tr>
              </thead>
              <tbody>
                {priceTable.map(item => (
                  <tr key={item.id}>
                    <td>{item.produce}</td>
                    <td className={item.price.includes('awaiting') ? styles.priceAwaiting : ''}>{item.price}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <button className={styles.viewMore}>View more ▼</button>
          </div>
        </section>

        {/* Confirm Tomorrow's Demand */}
        <section className={styles.section}>
          <h2>Confirm tomorrow's demand</h2>
          <div className={styles.demandItems}>
            {tomorrowDemand.map(item => (
              <div key={item.id} className={styles.demandItem}>
                <div className={styles.itemImage}></div>
                <div className={styles.itemDetails}>
                  <span className={styles.quantity}>{item.quantity}</span>
                  <span className={styles.name}>{item.name}</span>
                </div>
              </div>
            ))}
            <span className={styles.moreItems}>+2 more</span>
          </div>
          <button className={styles.planHarvestButton} onClick={() => navigate('/harvest-planning')}>
            Plan harvest for tomorrow
          </button>
        </section>

        {/* Add More Produce */}
        <section className={styles.section}>
          <div className={styles.sectionHeader}>
            <h2>Add more produce</h2>
            <button className={styles.viewAllButton}>View all ›</button>
          </div>
          <div className={styles.produceGrid}>
            {additionalProduce.map(item => (
              <div key={item.id} className={styles.produceCard}>
                <div className={styles.produceImage}>{item.image}</div>
                <span className={styles.produceName}>{item.name}</span>
                <button className={styles.addButton}>Add +</button>
              </div>
            ))}
          </div>
        </section>
      </main>

      <BottomNav />
    </div>
  );
}

export default Dashboard;
