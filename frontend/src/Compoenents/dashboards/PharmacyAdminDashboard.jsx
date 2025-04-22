import { useEffect, useState } from "react";
import { Card, CardContent, Typography, Button, Grid } from "@mui/material";
import { Pie } from "react-chartjs-2";
import axios from "axios";
import './chartSetup'; // Assuming you have a file for chart setup

const PharmacyAdminDashboard = () => {
  const [druggistCount, setDruggistCount] = useState(0);
  const [financeSummary, setFinanceSummary] = useState({});
  const [expiringMeds, setExpiringMeds] = useState([]);
  const [salesToday, setSalesToday] = useState(0);
  const [monthlySales, setMonthlySales] = useState([]);

//   useEffect(() => {
//     const token = localStorage.getItem("token");
//     const headers = { Authorization: token };

//     axios.get("/pharmacy/druggists/count", { headers }).then(res => setDruggistCount(res.data.count));
//     axios.get("/pharmacy/finances/summary", { headers }).then(res => setFinanceSummary(res.data));
//     axios.get("/pharmacy/medicines/expiring", { headers }).then(res => setExpiringMeds(res.data));
//     axios.get("/pharmacy/sales/today", { headers }).then(res => setSalesToday(res.data.total));
//     axios.get("/pharmacy/sales/monthly-summary", { headers }).then(res => setMonthlySales(res.data));
//   }, []);

  const pieChartData = {
    labels: ["Revenue", "Expenses", "Profit"],
    datasets: [
      {
        data: [financeSummary.revenue || 0, financeSummary.expenses || 0, financeSummary.profit || 0],
        backgroundColor: ["#00796b", "#80cbc4", "#004d40"],
      },
    ],
  };

  return (
    <div style={{ padding: "2rem" }}>
      <Typography variant="h4" gutterBottom>Pharmacy Admin Dashboard</Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} sm={4}>
          <Card>
            <CardContent>
              <Typography variant="h6">Total Druggists</Typography>
              <Typography variant="h4">{druggistCount}</Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={4}>
          <Card>
            <CardContent>
              <Typography variant="h6">Today's Sales</Typography>
              <Typography variant="h4">₹{salesToday}</Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={4}>
          <Card>
            <CardContent>
              <Typography variant="h6">Finance Summary</Typography>
              <Pie data={pieChartData} />
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Card>
            <CardContent>
              <Typography variant="h6">Expiring Medicines</Typography>
              <ul>
                {expiringMeds.map((med, idx) => (
                  <li key={idx}>
                    {med.name} – Expires on {med.exp_date}
                  </li>
                ))}
              </ul>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Button variant="contained" color="primary" onClick={() => window.location.href = "/admin/purchase-history"}>
            View Purchase History
          </Button>
        </Grid>
      </Grid>
    </div>
  );
};

export default PharmacyAdminDashboard;
