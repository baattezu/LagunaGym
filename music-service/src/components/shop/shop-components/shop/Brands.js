import {React} from "react";
import 'bootstrap/dist/css/bootstrap.min.css'
import '../shop.css'
const Brands = () => {
    return (
        <div className={"col-lg-12"}>
            <div className={"mb-3"}>
                <h4>Additional</h4>
                <div className={"mb-2"}>
                    <input type={"radio"} className={"me-2"} id={"Categories-1"}
                           name={"Categories-1"} value={"Beverages"}/>
                    <label htmlFor="Categories-1"> Organic</label>
                </div>
            </div>
        </div>
    )
};
export default Brands;