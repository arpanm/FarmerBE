import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import styles from './VerifyOTP.module.css';

const VerifyOTP = () => {
  const [otp, setOtp] = React.useState(['', '', '', '']);
  const [timer, setTimer] = React.useState(20);
  const [canResend, setCanResend] = React.useState(false);
  const inputRefs = React.useRef([]);
  const navigate = useNavigate();
  const location = useLocation();
  const phoneNumber = location.state?.phoneNumber;

  React.useEffect(() => {
    if (timer > 0) {
      const countdown = setInterval(() => {
        setTimer(prev => prev - 1);
      }, 1000);
      return () => clearInterval(countdown);
    } else {
      setCanResend(true);
    }
  }, [timer]);

  const handleInputChange = (index, value) => {
    if (value.length <= 1 && /^\d*$/.test(value)) {
      const newOtp = [...otp];
      newOtp[index] = value;
      setOtp(newOtp);

      // Move to next input
      if (value && index < 3) {
        inputRefs.current[index + 1].focus();
      }
    }
  };

  const handleKeyDown = (index, e) => {
    if (e.key === 'Backspace' && !otp[index] && index > 0) {
      inputRefs.current[index - 1].focus();
    }
  };

  const handleResendOTP = () => {
    if (canResend) {
      setTimer(20);
      setCanResend(false);
      // Add API call to resend OTP
    }
  };

  const handleVerify = () => {
    const otpValue = otp.join('');
    if (otpValue.length === 4) {
      // Add API call to verify OTP
      navigate('/dashboard');
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

      <h1 className={styles.title}>Verify your mobile number</h1>
      <p className={styles.subtitle}>
        Enter the OTP sent to {phoneNumber}.{' '}
        <button className={styles.changeNumber} onClick={() => navigate(-1)}>
          Change number
        </button>
      </p>

      <div className={styles.otpContainer}>
        {otp.map((digit, index) => (
          <input
            key={index}
            ref={el => (inputRefs.current[index] = el)}
            type="text"
            maxLength="1"
            value={digit}
            onChange={e => handleInputChange(index, e.target.value)}
            onKeyDown={e => handleKeyDown(index, e)}
            className={styles.otpInput}
          />
        ))}
      </div>

      <p className={styles.resendText}>
        Resend OTP in{' '}
        {canResend ? (
          <button className={styles.resendButton} onClick={handleResendOTP}>
            Resend OTP
          </button>
        ) : (
          <span>00:{timer.toString().padStart(2, '0')}</span>
        )}
      </p>

      <button className={styles.verifyButton} onClick={handleVerify} disabled={otp.some(digit => !digit)}>
        Verify
      </button>
    </div>
  );
};

export default VerifyOTP;
