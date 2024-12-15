# E-Commerce API (Version 2)

This is a Restful API for an e-commerce platform built using Java Spring Boot. The project allows users to browse
products, place orders, manage inventory, and handle customer and seller data. It supports essential e-commerce
functionalities such as product categorization, reviews, order management, and user authentication.

## Features

### **Version 1 Features** (Done✅)

- **User Management**:
    - Supports both customers and sellers.
    - Customers can manage their orders and loyalty points.
    - Sellers can manage their products, inventory, and orders.
    - Basic profile management for all users (email, password, address, etc.).

- **Product Management**:
    - Product catalog with support for categories and product variants.
    - Sellers can add, update, and delete products.
    - Customers can see all products and filter by category.
    - Products are associated with categories.

- **Order Management**:
    - Customers can place, update, and track orders.
    - Sellers can process and update order statuses.

- **Admin Dashboard**:
    - Admin users can view reports and manage users, products, and orders.

### **Version 2 Features** (Done✅)

- **Favorites and Wishlist**:
    - Customers can add products to their favorites and wishlist.
- **Reviews and Ratings**:
    - Customers can leave reviews and ratings for products.
- **Discounts and Coupons**:
    - Sellers can create discounts and coupons for products.
- **Product Recommendations**:
    - Customers can receive personalized product recommendations.
- **Product Images**:
    - Supports multiple images for products.

### **Version 3 Features** (In Progress🚧)

- **Activity Logs**: (In Progress🚧)
    - Logs all user activities for auditing and tracking.
- **Notifications**: (In Progress🚧)
    - Sends notifications for order updates, promotions, etc.
- **AI-Powered Recommendations**: (Done✅)
    - Uses AI to provide personalized product recommendations.
- **Payment Integration**: (Done✅)
    - Customers can choose from various payment methods.
- **Currency Conversion**: (Done✅)
    - Supports multiple currencies for products and orders.
- **Sales Reports**: (In Progress🚧)
    - Sellers can view sales reports and analytics.
- **Product Views and Analytics**: (In Progress🚧)
    - Sellers can view product views and analytics.

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgresSql
- JWT (JSON Web Tokens) for authentication
- Swagger for API documentation
