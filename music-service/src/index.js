import React from 'react';
import ReactDOM from 'react-dom/client';
import './css/index.css';
import HomePage from "./components/HomePage"
import {createBrowserRouter, RouterProvider, createRoutesFromElements, Route, Outlet} from "react-router-dom";
import ProfilePage from "./components/ProfilePage";
import NotFoundPage from "./components/Basic/NotFoundPage";
import Login from "./components/Auth/Login"
import Register from "./components/Auth/Register";
import Navbar from "./components/Basic/navigation/Navbar";
import {AuthProvider} from "./components/Auth/context/AuthProvider";
import Unauthorized from "./components/Basic/Unauthorized";
import RequireAuth from "./components/Basic/RequireAuth";
import Creator from "./components/Creator";
import router from "./components/Basic/navigation/Router";

const root = ReactDOM.createRoot(document.getElementById('root'));


root.render(
  <React.StrictMode>
      <AuthProvider>
          <RouterProvider router={router}/>
      </AuthProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
