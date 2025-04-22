import React, { useEffect, useState } from "react";
import axios from "axios";
import { Card, CardContent } from "@mui/material";

const DruggistList = ({ pharmacyId }) => {
  const [druggists, setDruggists] = useState([]);

  useEffect(() => {
    const fetchDruggists = async () => {
      try {
        const res = await axios.get(`/api/pharmacy/${pharmacyId}/druggists`);
        setDruggists(res.data);
      } catch (err) {
        console.error("Error fetching druggists", err);
      }
    };

    fetchDruggists();
  }, [pharmacyId]);

  return (
    <Card className="rounded-xl shadow-md my-4">
      <CardContent>
        <h2 className="text-xl font-semibold mb-2">Druggists List</h2>
        {druggists.length > 0 ? (
          <ul className="list-disc pl-5 space-y-1">
            {druggists.map((druggist) => (
              <li key={druggist.user_id}>
                {druggist.name} ({druggist.email})
              </li>
            ))}
          </ul>
        ) : (
          <p>No druggists found.</p>
        )}
      </CardContent>
    </Card>
  );
};

export default DruggistList;
