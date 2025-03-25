import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function AddProduce() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    cropType: '',
    quantity: '',
    quality: 'Standard',
    harvestDate: '',
    price: '',
  });

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = e => {
    e.preventDefault();
    // Here you would typically make an API call to save the produce
    console.log('Submitting produce:', formData);
    // Navigate back after submission
    navigate(-1);
  };

  return (
    <div>
      <header className="header">
        <button className="button" onClick={() => navigate(-1)} style={{ float: 'left' }}>
          Back
        </button>
        <h1>Add New Produce</h1>
      </header>

      <main>
        <section className="section">
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="cropType">Crop Type</label>
              <input type="text" id="cropType" name="cropType" value={formData.cropType} onChange={handleChange} required />
            </div>

            <div className="form-group">
              <label htmlFor="quantity">Quantity (kg)</label>
              <input type="number" id="quantity" name="quantity" value={formData.quantity} onChange={handleChange} required />
            </div>

            <div className="form-group">
              <label htmlFor="quality">Quality</label>
              <select id="quality" name="quality" value={formData.quality} onChange={handleChange}>
                <option value="Premium">Premium</option>
                <option value="Standard">Standard</option>
                <option value="Basic">Basic</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="harvestDate">Harvest Date</label>
              <input type="date" id="harvestDate" name="harvestDate" value={formData.harvestDate} onChange={handleChange} required />
            </div>

            <div className="form-group">
              <label htmlFor="price">Expected Price (₹/kg)</label>
              <input type="number" id="price" name="price" value={formData.price} onChange={handleChange} required />
            </div>

            <button type="submit" className="button">
              Add Produce
            </button>
          </form>
        </section>
      </main>
    </div>
  );
}

export default AddProduce;
