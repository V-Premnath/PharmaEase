import { useEffect, useState } from "react";
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { Card, CardContent, Typography, Grid } from "@mui/material";

const COLORS = ["#0f766e", "#2dd4bf", "#5eead4", "#99f6e4"];

const SuperAdminDashboard = () => {
  const [pharmacyData, setPharmacyData] = useState([]);
  const [financeData, setFinanceData] = useState([]);
  const [totalUsers, setTotalUsers] = useState(0);

  // Mock data – replace with actual API calls later
  useEffect(() => {
    setPharmacyData([
      { name: "Pharmacy A", druggists: 10 },
      { name: "Pharmacy B", druggists: 5 },
      { name: "Pharmacy C", druggists: 8 },
    ]);

    setFinanceData([
      { name: "Pharmacy A", revenue: 120000 },
      { name: "Pharmacy B", revenue: 88000 },
      { name: "Pharmacy C", revenue: 104000 },
    ]);

    setTotalUsers(150); // example total
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <Typography variant="h4" gutterBottom color="#0f766e">
        Super Admin Dashboard
      </Typography>

      <Grid container spacing={4}>
        <Grid item xs={12} md={6}>
          <Card elevation={3}>
            <CardContent>
              <Typography variant="h6">Total Users: {totalUsers}</Typography>
              <ResponsiveContainer width="100%" height={300}>
                <PieChart>
                  <Pie
                    data={pharmacyData}
                    dataKey="druggists"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    outerRadius={100}
                    fill="#0f766e"
                    label
                  >
                    {pharmacyData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Grid container spacing={2}>
            {financeData.map((pharmacy, index) => (
              <Grid item xs={12} sm={6} key={index}>
                <Card sx={{ backgroundColor: "#e0f2f1", borderRadius: 3 }} elevation={2}>
                  <CardContent>
                    <Typography variant="h6" color="#0f766e">{pharmacy.name}</Typography>
                    <Typography variant="body1">Revenue: ₹{pharmacy.revenue.toLocaleString()}</Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Grid>
      </Grid>
    </div>
  );
};

export default SuperAdminDashboard;
