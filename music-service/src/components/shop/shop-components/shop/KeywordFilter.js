import {React} from "react";
import 'bootstrap/dist/css/bootstrap.min.css'
import '../shop.css'
const KeywordFilter = () => {
    return (
        <div className={"col-xl-3"}>
            <div className={"input-group w-100 mx-auto d-flex"}>
                <input className={"form-control p-3"} placeholder={"keywords"}
                       aria-describedby={"search-icon-1"}/>
                <span className={"input-group-text p-3"}><i
                    className={"fa fa-search"}></i></span>
            </div>
        </div>
    )
};
export default KeywordFilter;