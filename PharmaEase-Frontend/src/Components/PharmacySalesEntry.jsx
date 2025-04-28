import React, { useState } from "react";
import "./PharmacySalesEntry.css";

const PharmacySalesEntry = () => {
  const [invoiceNumber] = useState("INV-0001");
  const [customerName, setCustomerName] = useState("");
  const [customerPhone, setCustomerPhone] = useState("");
  const [orderType, setOrderType] = useState("OFFLINE");
  const [selectedMedicines, setSelectedMedicines] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");

 


  const handleAddMedicine = (medicine) => {
    const exists = selectedMedicines.find((m) => m.id === medicine.id);
    if (exists) {
      const updated = selectedMedicines.map((m) =>
        m.id === medicine.id ? { ...m, quantity: m.quantity + 1 } : m
      );
      setSelectedMedicines(updated);
    } else {
      setSelectedMedicines([...selectedMedicines, { ...medicine, quantity: 1 }]);
    }
    setShowModal(false);
  };

  const handleRemove = (id) =>
    setSelectedMedicines(selectedMedicines.filter((m) => m.id !== id));

  const handleQtyChange = (id, qty) => {
    const updated = selectedMedicines.map((m) =>
      m.id === id ? { ...m, quantity: Number(qty) } : m
    );
    setSelectedMedicines(updated);
  };

  const totalAmount = selectedMedicines.reduce(
    (sum, m) => sum + m.cost * m.quantity,
    0
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({
      invoiceNumber,
      customerName,
      customerPhone,
      orderType,
      selectedMedicines,
    });
  };

  // 🔍 Dummy data for demo
  const dummyMedicines = [
    { id: 1, name: "Paracetamol", cost: 10 },
    { id: 2, name: "Amoxicillin", cost: 25 },
    { id: 3, name: "Cetirizine", cost: 15 },
  ];

  const filteredMedicines = dummyMedicines.filter((m) =>
    m.name.toLowerCase().includes(searchQuery.toLowerCase())
  );
  return (
    <div className="sales-entry-container">
      <h2 className="sales-entry-heading">🛒 Offline Sales Entry</h2>

      <form onSubmit={handleSubmit} className="sales-entry-form">
        <div className="form-row">
          <div className="form-group">
            <label>Invoice Number</label>
            <input type="text" value={invoiceNumber} readOnly className="form-input" />
          </div>
          <div className="form-group">
            <label>Customer Name</label>
            <input
              type="text"
              value={customerName}
              onChange={(e) => setCustomerName(e.target.value)}
              placeholder="Enter full name"
              className="form-input"
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group">
            <label>Phone Number</label>
            <input
              type="tel"
              value={customerPhone}
              onChange={(e) => setCustomerPhone(e.target.value)}
              placeholder="10-digit number"
              className="form-input"
            />
          </div>
          <div className="form-group">
            <label>Payment Type</label>
            <select
              value={orderType}
              onChange={(e) => setOrderType(e.target.value)}
              className="form-input"
            >
              <option value="OFFLINE">Offline</option>
              <option value="ONLINE">Online</option>
            </select>
          </div>
        </div>

        <button type="button" className="add-medicine-button" onClick={() => setShowModal(true)}>
          ➕ Add Medicine
        </button>

        {selectedMedicines.length > 0 && (
          <div className="medicine-table">
            <table>
              <thead>
                <tr>
                  <th>Medicine</th>
                  <th>Price</th>
                  <th>Quantity</th>
                  <th>Subtotal</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {selectedMedicines.map((med) => (
                  <tr key={med.id}>
                    <td>{med.name}</td>
                    <td>₹{med.cost}</td>
                    <td>
                      <input
                        type="number"
                        value={med.quantity}
                        onChange={(e) => handleQtyChange(med.id, e.target.value)}
                        min={1}
                        className="qty-input"
                      />
                    </td>
                    <td>₹{med.cost * med.quantity}</td>
                    <td>
                      <button
                        type="button"
                        className="remove-btn"
                        onClick={() => handleRemove(med.id)}
                      >
                        ❌
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <div className="total-amount">Total: ₹{totalAmount}</div>
          </div>
        )}

        <button type="submit" className="submit-button">
          Sell
        </button>
      </form>

      {showModal && (
        <div className="modal-backdrop" onClick={() => setShowModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h3>🔍 Search & Add Medicines</h3>

            <input
              type="text"
              placeholder="Search for medicines..."
              className="search-bar"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />

            <ul className="medicine-list">
              {filteredMedicines.map((m) => (
                <li key={m.id}>
                  <span>{m.name} - ₹{m.cost}</span>
                  <button onClick={() => handleAddMedicine(m)}>Add</button>
                </li>
              ))}
            </ul>
          </div>
        </div>
      )}
    </div>
  );
};

export default PharmacySalesEntry;
