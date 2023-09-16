
# Webshop - A React, Node.js, and Express-based E-commerce Platform

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technical Stack](#technical-stack)
- [Web Design](#web-design)
  - [Information Architecture](#information-architecture)
  - [Design Principles](#design-principles)
- [API Design](#api-design)
- [Software Architecture](#software-architecture)
- [Installation and Setup](#installation-and-setup)
- [Contributing](#contributing)
- [Reflections](#reflections)

## Introduction

This webshop is a single-page e-commerce application that allows users to browse products by category, view detailed product information, and add items to their shopping basket.

## Features

- Browse products by category (Women, Men, Sale)
- View detailed product information
- Add and remove items from the shopping basket
- User registration and login

## Technical Stack

- Frontend: React
- Backend: Node.js, Express
- Database: JSON File Storage
- Other Libraries: React Bootstrap, Styled Components

## Web Design

### Information Architecture

The website starts with a home page featuring a sale banner, a navbar, and a hero banner. Users can navigate to different product categories using the navbar. The main area of the home page lists all products as clickable cards.

### Design Principles

#### Gestalt Design Principles:

- **Proximity**: Grouping related elements close together.
- **Symmetry**: Utilized grid system based on Bootstrap's 12 column structure.
- **Usability**: Intuitive navigation and relevant product details.

#### Principles of Interaction Design:

- Friendly website language
- Greeting users with their name
- Loading indicators

## API Design

### Endpoints

<!-- | Resource Path | POST | GET | PUT | DELETE |
|--------------|------|-----|-----|--------|
| /customers | Create a new customer | Retrieve all customers | Error | Error |
| /items | Create a new item | Retrieve all items | Error | Error |
| ... | ... | ... | ... | ... | -->

_Note: This is a simplified version; please see code for full API specifications._

## Software Architecture

The application follows a three-layer architecture:
- Server-side developed in Node.js and Express
- Client-side developed in React
- API as the bridge between server and client

Data is stored in a JSON file. The client and server communicate through CORS, via a proxy that directs server data from `localhost:8080` to `localhost:3000`.

## Installation and Setup

1. Clone the repo.
2. Run `npm install` to install all dependencies.
3. Run `npm start` to start the development server.
4. Visit `http://localhost:3000` to view the application.

## Contributing
Co-studens Lukasz, Linnea, Emma and Paulina also contributed to the project. 
