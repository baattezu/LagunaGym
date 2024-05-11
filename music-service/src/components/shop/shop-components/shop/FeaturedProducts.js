import {React} from "react";
import 'bootstrap/dist/css/bootstrap.min.css'

import '../shop.css'
import FeaturedProduct from "./FeaturedProduct";
const FeaturedProducts = () => {
    return (
        <div className={"col-lg-12"}>
            <h4 className={"mb-3"}>Featured products</h4>
            <FeaturedProduct/>

        </div>
    )
};
export default FeaturedProducts;