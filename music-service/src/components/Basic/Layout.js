import {Outlet} from "react-router-dom";
import React from "react";
import Navbar from "./navigation/Navbar";
import Footer from "./navigation/Footer";

function Layout() {
    return (
        <div>
            <header>
                <Navbar />
            </header>
            <main>
                <Outlet />
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    );
}
export default Layout;