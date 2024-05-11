import {React} from "react";
import 'bootstrap/dist/css/bootstrap.min.css'
import '../shop.css'
const Pagination = () => {
    return (
        <div className={"col-12"}>
            <div className={"pagination d-flex justify-content-center mt-5"}>
                <a href="#" className="rounded">&laquo;</a>
                <a href="#" className="active rounded">1</a>
                <a href="#" className="rounded">2</a>
                <a href="#" className="rounded">3</a>
                <a href="#" className="rounded">4</a>
                <a href="#" className="rounded">5</a>
                <a href="#" className="rounded">6</a>
                <a href="#" className="rounded">&raquo;</a>
            </div>
        </div>
    )
};
export default Pagination;