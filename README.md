💊 PharmaEase – Pharmacy Management System
PharmaEase is a full-stack, multi-tenant pharmacy management platform designed to streamline medicine sales, inventory tracking, and vendor-pharmacy interactions across both online and offline channels.

🚀 Features
Role-Based Dashboards
Separate interfaces and workflows for Super Admin, Pharmacy Admin, Druggist, Vendor, and Customer.

Multi-Tenancy Architecture
Each pharmacy operates on an isolated MySQL database with a shared global database for authentication and cart management.

E-commerce + Offline Sales
Customers can order medicines online; druggists manage walk-in sales via an offline interface.

Real-Time Notifications
Vendors and pharmacies receive alerts for new stock uploads, stock requests, expiry issues, and more.

Secure Authentication
JWT-based backend security and Google OAuth for easy login.

Smart Analytics
Dashboards show sales trends, low-stock alerts, expiry summaries, and top-selling medicines.

Cloud Deployment
Hosted using Oracle Cloud Free Tier with Spring Boot backend, MySQL database, and React frontend.

🛠 Tech Stack
Frontend: React, Axios, Tailwind CSS

Backend: Spring Boot, RESTful APIs, JPA

Database: MySQL (multi-DB setup: per-pharmacy + global cart DB)

Auth: JWT + Google OAuth

Deployment: Oracle Cloud (Free Tier)

📂 Project Structure
sql
Copy
Edit
pharmease/
│── backend/          <-- Spring Boot APIs, Service Layer, JPA
│── frontend/         <-- React UI with role-specific pages
│── docker-compose.yml (optional for full stack testing)
💡 Highlight
Designed for real-world pharmacy operations, PharmaEase combines modular architecture, clean UI, and real-time analytics in a deployable cloud-based solution.

