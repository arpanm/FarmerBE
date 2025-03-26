import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import styles from './BottomNav.module.css';

function BottomNav() {
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <nav className={styles.bottomNav}>
      <button
        className={`${styles.navButton} ${location.pathname === '/dashboard' ? styles.active : ''}`}
        onClick={() => navigate('/dashboard')}
      >
        <span className={styles.homeIcon}>🏠</span>
        <span>Home</span>
      </button>
      <button
        className={`${styles.navButton} ${location.pathname === '/select-produce' ? styles.active : ''}`}
        onClick={() => navigate('/select-produce')}
      >
        <span className={styles.manageIcon}>🌾</span>
        <span>Manage crops</span>
      </button>
      <button className={styles.navButton}>
        <span className={styles.ledgerIcon}>📒</span>
        <span>Ledger</span>
      </button>
      <button
        className={`${styles.navButton} ${location.pathname === '/profile' ? styles.active : ''}`}
        onClick={() => navigate('/profile')}
      >
        <span className={styles.farmsIcon}>🏡</span>
        <span>My Farms</span>
      </button>
    </nav>
  );
}

export default BottomNav;
