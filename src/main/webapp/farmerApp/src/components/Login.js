import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Login.module.css';

const Login = () => {
  const [phoneNumber, setPhoneNumber] = React.useState('');
  const navigate = useNavigate();

  const handleGetOTP = () => {
    if (phoneNumber.length === 10) {
      navigate('/verify-otp', { state: { phoneNumber: `+91-${phoneNumber}` } });
    }
  };

  return (
    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate(-1)}>
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
          <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12l4.58-4.59z" fill="currentColor" />
        </svg>
        Back
      </button>

      <h1 className={styles.title}>Login</h1>
      <p className={styles.subtitle}>You'll receive a 4-digit OTP to verify your mobile number.</p>

      <div className={styles.inputGroup}>
        <div className={styles.countryCode}>+91</div>
        <input
          type="tel"
          placeholder="Enter your phone number"
          value={phoneNumber}
          onChange={e => setPhoneNumber(e.target.value.replace(/\D/g, '').slice(0, 10))}
          className={styles.input}
        />
      </div>

      <p className={styles.terms}>
        By continuing, you agree to our{' '}
        <a href="/terms" className={styles.link}>
          Terms of Service
        </a>{' '}
        and{' '}
        <a href="/privacy" className={styles.link}>
          Privacy & Legal Policy
        </a>
      </p>

      <button className={styles.button} onClick={handleGetOTP} disabled={phoneNumber.length !== 10}>
        Get OTP
      </button>
    </div>
  );
};

export default Login;
