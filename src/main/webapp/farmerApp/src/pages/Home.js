import React from 'react';
import { useNavigate } from 'react-router-dom';

function Home() {
  const navigate = useNavigate();

  return (
    <div>
      <header className="header">
        <input type="text" placeholder="Search crops, demands, or deliveries..." className="search-bar" />
      </header>

      <main>
        <section className="section">
          <h2>Quick Actions</h2>
          <button className="button" onClick={() => navigate('/delivery-status')}>
            Today's Delivery Status
          </button>
          <button className="button" onClick={() => navigate('/price-table')}>
            Price Table
          </button>
          <button className="button" onClick={() => navigate('/demand-confirmation')}>
            Demand Confirmation
          </button>
          <button className="button" onClick={() => navigate('/add-produce')}>
            Add Produce
          </button>
        </section>
      </main>
    </div>
  );
}

export default Home;
