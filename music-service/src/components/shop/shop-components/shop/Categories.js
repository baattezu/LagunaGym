import {React} from "react";
import 'bootstrap/dist/css/bootstrap.min.css'

import '../shop.css'
const Categories = () => {
    return (
        <div className={"col-lg-12"}>
            <div className={"mb-3"}>
                <h4>Categories</h4>
                <ul className={"list-unstyled fruite-categorie"}>
                    <li>
                        <div className={"d-flex justify-content-between fruite-name"}>
                            <a href="#"><i className={"fas fa-apple-alt me-2"}></i>Apples</a>
                            <span>(3)</span>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
    )
};
export default Categories;