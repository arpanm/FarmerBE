import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LanguageSelector from './components/LanguageSelector';
import Welcome from './components/Welcome';
import Login from './components/Login';
import VerifyOTP from './components/VerifyOTP';
import Dashboard from './pages/Dashboard';
import HarvestPlanning from './pages/HarvestPlanning';
import SelectProduce from './pages/SelectProduce';
import Profile from './pages/Profile';
import Ledger from './pages/Ledger';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LanguageSelector />} />
        <Route path="/welcome" element={<Welcome />} />
        <Route path="/login" element={<Login />} />
        <Route path="/verify-otp" element={<VerifyOTP />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/harvest-planning" element={<HarvestPlanning />} />
        <Route path="/select-produce" element={<SelectProduce />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/ledger" element={<Ledger />} />
      </Routes>
    </Router>
  );
}

export default App;
