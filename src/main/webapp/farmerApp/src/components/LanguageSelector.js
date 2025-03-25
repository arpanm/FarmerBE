import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './LanguageSelector.module.css';

const languages = [
  { code: 'en', label: 'English' },
  { code: 'hi', label: 'हिंदी' },
  { code: 'bn', label: 'বাংলা' },
  { code: 'gu', label: 'ગુજરાતી' },
  { code: 'mr', label: 'मराठी' },
  { code: 'kn', label: 'ಕನ್ನಡ' },
  { code: 'ml', label: 'മലയാളം' },
  { code: 'te', label: 'తెలుగు' },
  { code: 'ta', label: 'தமிழ்' },
];

const LanguageSelector = () => {
  const [selectedLanguage, setSelectedLanguage] = React.useState('en');
  const navigate = useNavigate();

  const handleContinue = () => {
    localStorage.setItem('language', selectedLanguage);
    navigate('/welcome');
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>
        Welcome to
        <br />
        Smart Farmer
      </h1>
      <p className={styles.subtitle}>Please choose the language you prefer.</p>

      <div className={styles.languageList}>
        {languages.map(lang => (
          <label key={lang.code} className={styles.languageOption}>
            <input
              type="radio"
              name="language"
              value={lang.code}
              checked={selectedLanguage === lang.code}
              onChange={e => setSelectedLanguage(e.target.value)}
              className={styles.radioInput}
            />
            <span className={styles.radioLabel}>{lang.label}</span>
          </label>
        ))}
      </div>

      <button className={styles.continueButton} onClick={handleContinue}>
        Continue
      </button>
    </div>
  );
};

export default LanguageSelector;
