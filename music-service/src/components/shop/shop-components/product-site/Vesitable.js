import item from '../shop-item.jpg'
const Vesitable = () => {
    return (
        <div className="vesitable">
            <div className="owl-carousel vegetable-carousel justify-content-center">
                <div className="border border-primary rounded position-relative vesitable-item">
                    <div className="vesitable-img">
                        <img src={item} className={"img-fluid w-100 rounded-top"} alt=""/>
                    </div>
                    <div className="text-white bg-primary px-3 py-1 rounded position-absolute">
                        Vegetable
                    </div>
                    <div className="p-4 pb-0 rounded-bottom">
                        <h4>Parsely</h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit sed do eiusmod te incididunt</p>
                        <div className="d-flex justify-content-between flex-lg-wrap">
                            <p className="text-dark fs-5 fw-bold">$4.99 / kg</p>
                            <a href="#"
                               className="btn border border-secondary rounded-pill px-3 py-1 mb-4 text-primary"><i
                                className="fa fa-shopping-bag me-2 text-primary"></i> Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
};
export default Vesitable;