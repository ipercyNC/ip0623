/*
 * Index.js
 * 7/10/2023
 * Ian Percy
 * 
 * 
 * Render the App.js class
 */
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
