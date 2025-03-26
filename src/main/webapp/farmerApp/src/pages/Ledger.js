import React, { useState } from 'react';
import styles from './Ledger.module.css';
import RcwsDetails from '../components/RcwsDetails';
import BottomNav from '../components/BottomNav';

function Ledger() {
  const [activeTab, setActiveTab] = useState('rcws');
  const [selectedRcws, setSelectedRcws] = useState(null);

  const rcwsData = [
    {
      id: 'GH84458514',
      date: '13 Oct 2022, 04:24 pm',
      paymentStatus: 'done',
      items: [{ crop: 'Cauliflower', quantity: '300 kg' }],
    },
    {
      id: 'GH84458514',
      date: '13 Oct 2022, 04:24 pm',
      paymentStatus: 'pending',
      items: [
        { crop: 'Potato', quantity: '300 kg' },
        { crop: 'Cauliflower', quantity: '250 kg' },
        { crop: 'Onion', quantity: '150 kg' },
      ],
    },
  ];

  const passbookData = [
    { date: '10/09/2024', amount: '30,000.00', status: 'Credit' },
    { date: '09/09/2024', amount: '50,490.00', status: 'Credit' },
    { date: '08/09/2024', amount: '45,000.00', status: 'Credit' },
    { date: '06/09/2024', amount: '57,430.00', status: 'Credit' },
    { date: '05/09/2024', amount: '23,650.00', status: 'Credit' },
    { date: '04/09/2024', amount: '34,670.00', status: 'Credit' },
    { date: '03/09/2024', amount: '17,000.00', status: 'Credit' },
  ];

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1>Ledger</h1>
      </div>

      <div className={styles.tabs}>
        <button className={`${styles.tab} ${activeTab === 'rcws' ? styles.active : ''}`} onClick={() => setActiveTab('rcws')}>
          RCWS
        </button>
        <button className={`${styles.tab} ${activeTab === 'passbook' ? styles.active : ''}`} onClick={() => setActiveTab('passbook')}>
          Passbook
        </button>
      </div>

      {activeTab === 'rcws' && (
        <div className={styles.rcwsContainer}>
          <div className={styles.filterContainer}>
            <button className={styles.filterButton}>
              <span>Filter</span>
              <span className={styles.filterIcon}>⚡</span>
            </button>
          </div>

          {rcwsData.map((rcws, index) => (
            <div key={index} className={styles.rcwsCard}>
              <div className={styles.rcwsHeader}>
                <div className={styles.rcwsInfo}>
                  <p>RCWS ID: {rcws.id}</p>
                  <p>Generated on {rcws.date}</p>
                </div>
                <div className={styles.paymentStatus}>{rcws.paymentStatus === 'done' ? 'Payment done' : 'Payment pending'}</div>
              </div>

              <div className={styles.rcwsItems}>
                {rcws.items.map((item, itemIndex) => (
                  <div key={itemIndex} className={styles.rcwsItem}>
                    <div className={styles.cropInfo}>
                      <span className={styles.cropName}>{item.crop}</span>
                      <span className={styles.quantity}>Quantity: {item.quantity}</span>
                    </div>
                  </div>
                ))}
              </div>

              <div className={styles.rcwsActions}>
                <button className={styles.viewButton} onClick={() => setSelectedRcws(rcws)}>
                  View
                </button>
                <button className={styles.downloadButton}>Download</button>
              </div>
            </div>
          ))}
        </div>
      )}

      {activeTab === 'passbook' && (
        <div className={styles.passbookContainer}>
          <div className={styles.filterContainer}>
            <button className={styles.filterButton}>
              <span>Filter</span>
              <span className={styles.filterIcon}>⚡</span>
            </button>
          </div>

          <table className={styles.passbookTable}>
            <thead>
              <tr>
                <th>Delivery Date</th>
                <th>Transaction Amount</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {passbookData.map((entry, index) => (
                <tr key={index}>
                  <td>{entry.date}</td>
                  <td>₹{entry.amount}</td>
                  <td>{entry.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      {selectedRcws && <RcwsDetails rcws={selectedRcws} onClose={() => setSelectedRcws(null)} />}
      <BottomNav />
    </div>
  );
}

export default Ledger;
