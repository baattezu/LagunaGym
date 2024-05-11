import ModalSearch from "./ModalSearch";
import SinglePageHeader from "./SinglePageHeader";
import FruitShop from "./FruitShopPage";
import React , { useState, useEffect } from 'react'
const Shop = () => {
    return (
        <div>
            <ModalSearch/>
            <SinglePageHeader/>
            <FruitShop/>
        </div>
    )
};
export default Shop;