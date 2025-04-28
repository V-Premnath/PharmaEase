import { useEffect, useState } from "react";
import { Card, CardContent, Typography, Button, Grid } from "@mui/material";
import { Pie } from "react-chartjs-2";
import styles from "./PharmacyAdminDashboard.module.css"; // Assuming you have a CSS module for styles
import './chartSetup'; // Assuming you have a file for chart setup

const PharmacyAdminDashboard = () => {
  const [druggistCount, setDruggistCount] = useState(0);
  const [financeSummary, setFinanceSummary] = useState({});
  const [expiringMeds, setExpiringMeds] = useState([]);
  const [salesToday, setSalesToday] = useState(0);
  const [inventory,setInventory]=useState(0);
  const [pharmacyName,setPharmacyName]=useState("");


  useEffect(() => {
setPharmacyName(localStorage.getItem("dbname"));
  })
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
    <div className={styles.dashboardContainer}>
  <Typography variant="h4" className={styles.header} sx={{backgroundColor :"#002f40",color:"#ffffff",justifyContent:"space-around",alignContent:"centre",padding:"10px",fontWeight:"bold",borderRadius:"20px",display:"flex"}}><div>Welcome {pharmacyName}</div></Typography>
  <Typography variant="h4" className={styles.header}>Pharmacy Admin Dashboard</Typography>

  <Grid container spacing={3}>
    <Grid item xs={12} sm={4}>
      <Card className={styles.card}>
        <CardContent className={styles.cardContent}>
          <Typography className={styles.cardTitle}>Total Druggists</Typography>
          <Typography className={styles.cardValue}>{druggistCount}</Typography>
        </CardContent>
      </Card>
    </Grid>

    <Grid item xs={12} sm={4}>
      <Card className={styles.card}>
        <CardContent className={styles.cardContent}>
          <Typography className={styles.cardTitle}>Today's Sales</Typography>
          <Typography className={styles.cardValue}>₹{salesToday}</Typography>
        </CardContent>
      </Card>
    </Grid>
    <Grid item xs={12} sm={4}>
      <Card className={styles.card}>
        <CardContent className={styles.cardContent}>
          <Typography className={styles.cardTitle}>Inventory</Typography>
          <Typography className={styles.cardValue}>{inventory}</Typography>
        </CardContent>
      </Card>
    </Grid>

    <Grid item xs={12} sm={4}>
      <Card className={styles.card}>
        <CardContent className={styles.cardContent}>
          <Typography className={styles.cardTitle}>Finance Summary</Typography>
          <Pie data={pieChartData} />
        </CardContent>
      </Card>
    </Grid>
    

    <Grid item xs={12}>
      <Card className={styles.card}>
        <CardContent className={styles.cardContent}>
          <Typography className={styles.cardTitle}>Expiring Medicines</Typography>
          <ul className={styles.expiringList}>
            {expiringMeds.map((med, idx) => (
              <li key={idx}>
                {med.name} – Expires on {med.exp_date}
              </li>
            ))}
          </ul>
        </CardContent>
      </Card>
    </Grid>

    <Grid item xs={12} className={styles.buttonWrapper}>
      <Button className={styles.customButton} onClick={() => window.location.href = "/admin/purchase-history"}>
        View Purchase History
      </Button>
    </Grid>
  </Grid>
</div>

  );
};

export default PharmacyAdminDashboard;
