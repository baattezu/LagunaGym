import {React} from 'react'
import KeywordFilter from "./shop-components/shop/KeywordFilter";
import Sorting from "./shop-components/shop/Sorting";
import Categories from "./shop-components/shop/Categories";
import PricePolzunok from "./shop-components/shop/PricePolzunok";
import Brands from "./shop-components/shop/Brands";
import FeaturedProducts from "./shop-components/shop/FeaturedProducts";
import Product from "./shop-components/shop/Product";
import Pagination from "./shop-components/shop/Pagination";

import 'bootstrap/dist/css/bootstrap.min.css'
const FruitShopPage = () => {
    return (
        <div className={"container-fluid fruite py-5"}>
            <div className={"container py-5"}>
                <h1 className={"mb-4"}>Fresh fruits shop</h1>
                <div className={"row g-4"}>
                    <div className={"col-lg-12"}>
                        <div className={"row g-4"}>
                            <KeywordFilter/>
                            <div className={"col-6"}></div>
                            <Sorting/>
                        </div>
                        <div className={"row g-4"}>
                            <div className={"col-lg-3"}>
                                <div className={"row g-4"}>
                                    <Categories/>
                                    <PricePolzunok/>
                                    <Brands/>
                                    <FeaturedProducts/>
                                </div>
                            </div>
                            <div className={"col-lg-9"}>
                                <div className={"row g-4 justify-content-center"}>
                                    <Product />
                                    <Product />
                                    <Product />
                                    <Product />
                                    <Product />
                                    <Pagination/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default FruitShopPage;