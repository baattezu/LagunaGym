import './footer.css'
import { FaInstagram } from "react-icons/fa";
import { FaWhatsapp } from "react-icons/fa";
import { FaYoutube } from "react-icons/fa";
import {NavLink} from "react-router-dom";
import React from "react";

const Footer = () => {
    return (
        <div className="footer">
            <div className={"social-media "}>
                <ul>
                    <li><NavLink to={"https://instagram.com/baattezu"} className={"footer-link"}><FaInstagram/></NavLink></li>
                    <li><NavLink to={"https://instagram.com/baattezu"} className={"footer-link"}><FaWhatsapp/></NavLink></li>
                    <li><NavLink to={"https://instagram.com/baattezu"} className={"footer-link"}><FaYoutube/></NavLink></li>
                </ul>
            </div>
            <p className={"copyright"}> © 2024 LagunaGym. All rights reserved.</p>
            <p className={"address"}> Address: 100000, Astana, Turan 55 </p>


        </div>
    );
}
export default Footer;