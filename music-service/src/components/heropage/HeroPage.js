import './heropage.css'
import gym from './gym.jpg';
import FruitShop from "../shop/FruitShopPage";
import React from "react";
const HeroPage = () => {
    return (

        <div className={"heropage container-fluid"}>
            <div className={"row align-items-center"}>
                <div className={"col-md-6"}>
                    <p className={"heropage-text"}>Your Journey to Better Fitness Starts Here.</p>
                    <div className={"button-group"}>
                        <button className={"heropage-button1"}>Join Now</button>
                        <button className={"heropage-button2"}>Sign In</button>

                    </div>
                </div>
                <div className={"col-md-6"}>
                    <div className={"image-container"}>
                        <img src={gym} className={"img-fluid image"} alt={"Gym Image"}/>
                    </div>
                </div>
            </div>
        </div>

    );
}
export default HeroPage;