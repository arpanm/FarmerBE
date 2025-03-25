import React from 'react';
import { useNavigate } from 'react-router-dom';

function PriceTable() {
  const navigate = useNavigate();
  const prices = [
    { id: 1, crop: 'Tomatoes', price: '₹25/kg', quality: 'Premium', date: '2023-08-15' },
    { id: 2, crop: 'Potatoes', price: '₹15/kg', quality: 'Standard', date: '2023-08-15' },
    { id: 3, crop: 'Onions', price: '₹20/kg', quality: 'Premium', date: '2023-08-15' },
  ];

  return (
    <div>
      <header className="header">
        <button className="button" onClick={() => navigate(-1)} style={{ float: 'left' }}>
          Back
        </button>
        <h1>Price Table</h1>
      </header>

      <main>
        <section className="section">
          <div style={{ overflowX: 'auto' }}>
            <table className="table">
              <thead>
                <tr>
                  <th>Crop</th>
                  <th>Price</th>
                  <th>Quality</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {prices.map(price => (
                  <tr key={price.id}>
                    <td>{price.crop}</td>
                    <td>{price.price}</td>
                    <td>{price.quality}</td>
                    <td>{price.date}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      </main>
    </div>
  );
}

export default PriceTable;
