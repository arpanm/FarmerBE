import React from 'react';
import { useNavigate } from 'react-router-dom';

function DeliveryStatus() {
  const navigate = useNavigate();
  const deliveries = [
    { id: 1, crop: 'Tomatoes', quantity: '50kg', status: 'Pending', time: '10:00 AM' },
    { id: 2, crop: 'Potatoes', quantity: '100kg', status: 'Delivered', time: '9:30 AM' },
    { id: 3, crop: 'Onions', quantity: '75kg', status: 'In Transit', time: '11:00 AM' },
  ];

  return (
    <div>
      <header className="header">
        <button className="button" onClick={() => navigate(-1)} style={{ float: 'left' }}>
          Back
        </button>
        <h1>Today's Delivery Status</h1>
      </header>

      <main>
        <section className="section">
          <div style={{ overflowX: 'auto' }}>
            <table className="table">
              <thead>
                <tr>
                  <th>Crop</th>
                  <th>Quantity</th>
                  <th>Status</th>
                  <th>Time</th>
                </tr>
              </thead>
              <tbody>
                {deliveries.map(delivery => (
                  <tr key={delivery.id}>
                    <td>{delivery.crop}</td>
                    <td>{delivery.quantity}</td>
                    <td>{delivery.status}</td>
                    <td>{delivery.time}</td>
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

export default DeliveryStatus;
