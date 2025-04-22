const ProductCard2 = ( { productImage, productName, id }) => {
    return (
      <Link to={/ProductPaga/+id} key={id}>
      <div className="product-card" >
        <div className="card-img">
          <img className="product-card-img" src={`../image/ProductImage/${productImage}`} alt="" />
        </div>
        <div className="product-discription">
          <p>
            <span className="discount">Up to 17% off</span>
            <span className="dealOfTheDay">Deal of the Day</span>
          </p>
          <p className="top-bottom-padding product-name">{productName}</p>
        </div>
      </div>
      </Link>
    );
  };
  export default ProductCard2;