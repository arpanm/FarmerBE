import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Welcome.module.css';
import banner1 from '../images/banner1.jpg';
import banner2 from '../images/banner2.jpg';

const Welcome = () => {
  const navigate = useNavigate();
  const [currentSlide, setCurrentSlide] = React.useState(0);

  const slides = [
    {
      title: 'Easy onboarding.',
      description: 'Now register in just 3 easy steps!',
      image: banner1,
    },
    {
      title: 'Transparent process.',
      description: 'Sell your produce at good prices!',
      image: banner2,
    },
  ];

  React.useEffect(() => {
    const timer = setInterval(() => {
      setCurrentSlide(prev => (prev === slides.length - 1 ? 0 : prev + 1));
    }, 3000);
    return () => clearInterval(timer);
  }, []);

  const handleLogin = () => {
    navigate('/login');
  };

  return (
    <div className={styles.container}>
      <div className={styles.carousel}>
        {slides.map((slide, index) => (
          <div
            key={index}
            className={`${styles.slide} ${index === currentSlide ? styles.active : ''}`}
            style={{ backgroundImage: `url(${slide.image})` }}
          >
            <div className={styles.content}>
              <h1 className={styles.title}>{slide.title}</h1>
              <p className={styles.description}>{slide.description}</p>
            </div>
          </div>
        ))}
        <div className={styles.indicators}>
          {slides.map((_, index) => (
            <button
              key={index}
              className={`${styles.indicator} ${index === currentSlide ? styles.active : ''}`}
              onClick={() => setCurrentSlide(index)}
            />
          ))}
        </div>
      </div>
      <button className={styles.loginButton} onClick={handleLogin}>
        Login
      </button>
    </div>
  );
};

export default Welcome;
