import React from 'react'
import { BrowserRouter as Router, Switch, Route, NavLink } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'
import './navbar.css'
import {MdOutlineTsunami} from "react-icons/md";
const Navbar = () => {
    return(
        (
            <nav className="navbar navbar-expand-lg navbar-dark">
                <div className="container">
                    <NavLink className="navbar-brand" to="/"> <MdOutlineTsunami/> LagunaGym </NavLink>
                    <button className="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className={"navbar-nav ms-auto"}>
                            <li className="nav-item">
                                <NavLink to={"/about"} className={"nav-link"}>About us</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to={"/memberships"} className={"nav-link"}>Get Membership</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to={"/shop"} className={"nav-link"}>Our Shop</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to={"/creator"} className={"nav-link"}>My Cabinet</NavLink>
                            </li>

                        </ul>
                    </div>
                </div>
            </nav>
        )
    )

}

export default Navbar;
