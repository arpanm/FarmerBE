import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function DemandConfirmation() {
  const navigate = useNavigate();
  const [demands] = useState([
    { id: 1, crop: 'Tomatoes', quantity: '200kg', price: '₹25/kg', deadline: '2023-08-20' },
    { id: 2, crop: 'Potatoes', quantity: '500kg', price: '₹15/kg', deadline: '2023-08-22' },
    { id: 3, crop: 'Onions', quantity: '300kg', price: '₹20/kg', deadline: '2023-08-21' },
  ]);

  const [selectedQuantities, setSelectedQuantities] = useState({});

  const handleQuantityChange = (id, value) => {
    setSelectedQuantities(prev => ({
      ...prev,
      [id]: value,
    }));
  };

  const handleConfirm = id => {
    // Here you would typically make an API call to confirm the demand
    console.log(`Confirming demand ${id} with quantity ${selectedQuantities[id]}`);
  };

  return (
    <div>
      <header className="header">
        <button className="button" onClick={() => navigate(-1)} style={{ float: 'left' }}>
          Back
        </button>
        <h1>Demand Confirmation</h1>
      </header>

      <main>
        <section className="section">
          <div style={{ overflowX: 'auto' }}>
            <table className="table">
              <thead>
                <tr>
                  <th>Crop</th>
                  <th>Required Quantity</th>
                  <th>Price</th>
                  <th>Deadline</th>
                  <th>Your Quantity</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {demands.map(demand => (
                  <tr key={demand.id}>
                    <td>{demand.crop}</td>
                    <td>{demand.quantity}</td>
                    <td>{demand.price}</td>
                    <td>{demand.deadline}</td>
                    <td>
                      <input
                        type="number"
                        className="form-control"
                        placeholder="Enter quantity"
                        value={selectedQuantities[demand.id] || ''}
                        onChange={e => handleQuantityChange(demand.id, e.target.value)}
                        style={{ width: '100px' }}
                      />
                    </td>
                    <td>
                      <button className="button" onClick={() => handleConfirm(demand.id)} disabled={!selectedQuantities[demand.id]}>
                        Confirm
                      </button>
                    </td>
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

export default DemandConfirmation;
