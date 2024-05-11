import {React} from "react";
import 'bootstrap/dist/css/bootstrap.min.css'

import '../shop.css'
import {Link} from "react-router-dom";
import item from '../shop-item.jpg'
const Product = () => {
    let id = 123;
    return (
        <div className={"col-md-6 col-lg-6 col-xl-4"}>
            <div className={"rounded position-relative fruite-item"}>
                <div className={"fruite-img"}>
                    <Link to={`/shop/product/${id}`}>
                        <img src={item}
                             className={"img-fluid w-100 rounded-top"}/>
                    </Link>
                </div>
                <div
                    className={"text-white bg-secondary px-3 py-1 rounded position-absolute"}>Fruits
                </div>
                <div
                    className={"p-4 border border-secondary border-top-0 rounded-bottom"}>

                    <Link to={`/shop/product/${id}`}>
                        <h4>Grapes</h4>
                    </Link>
                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit sed do
                        eiusmod te incididunt</p>
                    <div className={"d-flex justify-content-between flex-lg-wrap"}>
                        <p className={"text-dark fs-5 fw-bold mb-0"}>$4.99 / kg</p>
                        <a href={"#"}
                           className={"btn border border-secondary rounded-pill px-3 text-primary"}><i
                            className={"fa fa-shopping-bag me-2 text-primary"}></i> Add to
                            cart</a>
                    </div>
                </div>
            </div>
        </div>
    )
};
export default Product;