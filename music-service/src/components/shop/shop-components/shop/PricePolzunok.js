import {React, useState} from "react";
import '../shop.css'
import 'bootstrap/dist/css/bootstrap.min.css'
const PricePolzunok = () => {
    const [maxPrice, setPrice] = useState(0)

    const handleChange = e => {
        console.log('setting level', e.target.value)
        setPrice(e.target.value);
    };
    return (
        <div className={"col-lg-12"}>
            <div className={"mb-3"}>
                <h4 className={"mb-2"}>Price</h4>
                <input
                    type={"range"}
                    className={"form-range w-100"}
                    id={"rangeInput"}
                    name={"rangeInput"}
                    min={0}
                    max={200}
                    defaultValue={0}
                    step={0.5}
                    onChange={e => console.log(e.target.value)} // Don't set state on all changes as React will re-render
                    onMouseUp={handleChange} // Only set state when the handle is released
                />
                <output htmlFor={"rangeInput"}/>
            </div>
        </div>
    )
};
export default PricePolzunok;