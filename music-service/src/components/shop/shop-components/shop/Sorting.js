import {React} from "react";
import '../shop.css'
import 'bootstrap/dist/css/bootstrap.min.css'

const Sorting = () => {
    return (
        <div className={"col-xl-3"}>
            <div className="bg-light ps-3 py-3 rounded d-flex justify-content-between mb-4">
                <label htmlFor="fruits">Default Sorting:</label>
                <select id="fruits" name="fruitlist"
                        className="border-0 form-select-sm bg-light me-3" form="fruitform">
                    <option value="volvo">Nothing</option>
                    <option value="saab">Popularity</option>
                    <option value="opel">Organic</option>
                    <option value="audi">Fantastic</option>
                </select>
            </div>
        </div>
    )
};
export default Sorting;