  import React, { useState, useEffect } from 'react';
  import { Search, Plus, Trash2, FileText, Save } from 'lucide-react';

  const PharmacySalesEntry = () => {
    // State management
    const [customer, setCustomer] = useState({
      name: '',
      phone: '',
      prescriptionRequired: false,
      prescriptionId: '',
      doctorName: ''
    });

    const [saleItems, setSaleItems] = useState([{
      id: 1,
      medicineId: '',
      medicineName: '',
      batchNumber: '',
      expiryDate: '',
      quantity: 1,
      mrp: 0,
      discount: 0,
      amount: 0,
    }]);

    const [availableMedicines, setAvailableMedicines] = useState([]);
    const [saleDate, setSaleDate] = useState(new Date().toISOString().split('T')[0]);
    const [invoiceNumber, setInvoiceNumber] = useState('');
    const [paymentMethod, setPaymentMethod] = useState('cash');
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [showSearchResults, setShowSearchResults] = useState(false);
    const [activeSearchIndex, setActiveSearchIndex] = useState(0);

    // Calculate total amount
    const totalAmount = saleItems.reduce((sum, item) => sum + item.amount, 0);
    const totalMRP = saleItems.reduce((sum, item) => sum + (item.mrp * item.quantity), 0);
    const totalDiscount = totalMRP - totalAmount;

    // Mock function to fetch medicines - replace with actual API call
    useEffect(() => {
      // This would be your API call to get medicines from inventory
      const mockMedicines = [
        { id: 1, name: 'Paracetamol 500mg', batchNumber: 'B12345', expiryDate: '2025-12-31', stock: 100, mrp: 15.50 },
        { id: 2, name: 'Amoxicillin 250mg', batchNumber: 'B22345', expiryDate: '2025-10-15', stock: 50, mrp: 65.75 },
        { id: 3, name: 'Cetirizine 10mg', batchNumber: 'B32345', expiryDate: '2026-01-20', stock: 75, mrp: 35.25 },
        { id: 4, name: 'Aspirin 75mg', batchNumber: 'B42345', expiryDate: '2025-11-05', stock: 120, mrp: 12.80 },
        { id: 5, name: 'Metformin 500mg', batchNumber: 'B52345', expiryDate: '2025-09-30', stock: 60, mrp: 45.50 },
      ];
      setAvailableMedicines(mockMedicines);
    
      // Generate invoice number correctly
      const date = new Date();
      const generatedInvoice = `INV-${date.getFullYear()}${(date.getMonth() + 1)
        .toString().padStart(2, '0')}${date.getDate()
        .toString().padStart(2, '0')}-${Math.floor(Math.random() * 1000)
        .toString().padStart(3, '0')}`;
    
      setInvoiceNumber(generatedInvoice);
    }, []);
    
  // Search for medicines
  useEffect(() => {
    if (searchTerm.length > 1) {
      const results = availableMedicines.filter(med =>
        med.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
      setSearchResults(results);
      setShowSearchResults(true);
      setActiveSearchIndex(0);
    } else {
      setShowSearchResults(false);
    }
  }, [searchTerm, availableMedicines]);

  // Handle medicine selection
  const selectMedicine = (medicine, index) => {
    const newItems = [...saleItems];
    newItems[index] = {
      ...newItems[index],
      medicineId: medicine.id,
      medicineName: medicine.name,
      batchNumber: medicine.batchNumber,
      expiryDate: medicine.expiryDate,
      mrp: medicine.mrp,
      amount: medicine.mrp * newItems[index].quantity
    };
    setSaleItems(newItems);
    setSearchTerm('');
    setShowSearchResults(false);
  };

  // Update quantity and calculate amount
  const updateQuantity = (index, value) => {
    const newItems = [...saleItems];
    newItems[index].quantity = parseInt(value) || 0;

    // Recalculate amount with discount
    const mrp = newItems[index].mrp;
    const quantity = newItems[index].quantity;
    const discountPercent = newItems[index].discount;
    const discountedPrice = mrp - (mrp * discountPercent / 100);
    newItems[index].amount = parseFloat((discountedPrice * quantity).toFixed(2));

    setSaleItems(newItems);
  };

  // Update discount and recalculate amount
  const updateDiscount = (index, value) => {
    const newItems = [...saleItems];
    newItems[index].discount = parseFloat(value) || 0;

    // Recalculate amount with new discount
    const mrp = newItems[index].mrp;
    const quantity = newItems[index].quantity;
    const discountPercent = newItems[index].discount;
    const discountedPrice = mrp - (mrp * discountPercent / 100);
    newItems[index].amount = parseFloat((discountedPrice * quantity).toFixed(2));

    setSaleItems(newItems);
  };

  // Add new item row
  const addItem = () => {
    setSaleItems([...saleItems, {
      id: saleItems.length + 1,
      medicineId: '',
      medicineName: '',
      batchNumber: '',
      expiryDate: '',
      quantity: 1,
      mrp: 0,
      discount: 0,
      amount: 0,
    }]);
  };

  // Remove item row
  const removeItem = (index) => {
    if (saleItems.length > 1) {
      const newItems = [...saleItems];
      newItems.splice(index, 1);
      setSaleItems(newItems);
    }
  };

  // Handle save/submit
  const handleSubmit = (e) => {
    e.preventDefault();

    // Validate form
    if (!customer.name || saleItems.some(item => !item.medicineName)) {
      alert('Please fill all required fields');
      return;
    }

    // Create sale record
    const saleRecord = {
      invoiceNumber,
      saleDate,
      customer,
      items: saleItems,
      paymentMethod,
      totalMRP,
      totalDiscount,
      totalAmount
    };

    // Here you would save to your database
    console.log('Sale recorded:', saleRecord);

    // Mock API call - replace with your actual API endpoint
    setTimeout(() => {
      alert('Sale recorded successfully!');
      // Reset form or redirect as needed
    }, 500);
  };

  // Check if prescription is required for any medicine
  useEffect(() => {
    // In a real app, this would check against a database of prescription-only medicines
    const requiresPrescription = saleItems.some(item =>
      item.medicineName.toLowerCase().includes('amoxicillin') ||
      item.medicineName.toLowerCase().includes('metformin')
    );

    if (requiresPrescription && !customer.prescriptionRequired) {
      setCustomer({ ...customer, prescriptionRequired: true });
    }
  }, [saleItems]);

  return (
    <div className="sales-entry-container">
      <h2 className="sales-entry-heading">💊 New Sale Entry</h2>
  
      <form onSubmit={handleSubmit} className="sales-entry-form">
        <div className="form-row">
          <div className="form-group">
            <label className="form-label">Invoice Number</label>
            <input
              type="text"
              value={invoiceNumber}
              onChange={(e) => setInvoiceNumber(e.target.value)}
              className="form-input"
              readOnly
            />
          </div>
  
          <div className="form-group">
            <label className="form-label">Customer Name</label>
            <input
              type="text"
              value={customerName}
              onChange={(e) => setCustomerName(e.target.value)}
              className="form-input"
              placeholder="Enter full name"
            />
          </div>
        </div>
  
        <div className="form-row">
          <div className="form-group">
            <label className="form-label">Phone Number</label>
            <input
              type="tel"
              value={customerPhone}
              onChange={(e) => setCustomerPhone(e.target.value)}
              className="form-input"
              placeholder="10-digit number"
            />
          </div>
  
          <div className="form-group">
            <label className="form-label">Order Type</label>
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
  
        <button type="submit" className="submit-button">
          💾 Save Sale
        </button>
      </form>
    </div>
  );
  
  

}