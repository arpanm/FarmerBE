import React from 'react';
import styles from './RcwsDetails.module.css';

function RcwsDetails({ rcws, onClose }) {
  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <div className={styles.header}>
          <button className={styles.backButton} onClick={onClose}>
            ← Receipt Details
          </button>
          <button className={styles.infoButton}>?</button>
        </div>

        <div className={styles.content}>
          <h2>Rate and weighment slip details</h2>

          <div className={styles.receiptCard}>
            <div className={styles.paymentStatus}>{rcws.paymentStatus === 'done' ? 'Payment done' : 'Payment pending'}</div>

            <div className={styles.receiptInfo}>
              <p>Generated on {rcws.date}</p>
              <p>Receipt no.: {rcws.id}</p>
              <p>Bank name: Andhara Bank</p>
              <p>Bank Account no.: XXXX XXXX 4567</p>
            </div>

            <table className={styles.cropTable}>
              <thead>
                <tr>
                  <th>Crop</th>
                  <th>Demand (kg)</th>
                  <th>Amount</th>
                </tr>
              </thead>
              <tbody>
                {rcws.items.map((item, index) => (
                  <tr key={index}>
                    <td>{item.crop}</td>
                    <td>{parseInt(item.quantity)}</td>
                    <td>₹20,000</td>
                  </tr>
                ))}
              </tbody>
            </table>

            <div className={styles.totalAmount}>
              <span>Total Amount</span>
              <span>₹{rcws.items.length * 20000}</span>
            </div>

            <button className={styles.downloadButton}>
              <span>Download</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RcwsDetails;
