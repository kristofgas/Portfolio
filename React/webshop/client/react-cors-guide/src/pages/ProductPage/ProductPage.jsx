import Carousel_Product from "../../components/Carousel/Carousel";
import Product_Info_Card from "../../components/Product_info_card/ProductInfoCard";
import { Container, Row, Col, Grid, Card, Button } from "react-bootstrap";
import { useState, useEffect, useContext } from "react";
import "./ProductPage.css";
import { CustomerContext } from "../../App";
import * as api from "../../API.js";

// Needs to fetch product id and it's attrubtes

const ProductPage = () => {
	const queryParams = new URLSearchParams(window.location.search);
	const id = queryParams.get("id");
	const [product, setProduct] = useState([]);
	useEffect(() => {
		fetchData();
	}, []);

	const fetchData = () => {
		api
			.getItem(id)
			.then((p) => setProduct(p))
			.catch((error) => {
				console.log(error);
			});
	};

	// console.log(id);
	// const [itemId, setItemId] = useState();
	// setItemId(id);   //!!!!!!!!!!!!!!!!

	// useEffect(() => {
	// 	setItemId();
	// }, []);

	return (
		<div>
			<Container>
				<Row styleClass="row">
					<Col sm={6}>
						<Carousel_Product product={product} />
					</Col>
					<Col sm={6}>
						<Row>
							<Product_Info_Card product={product} />
						</Row>
					</Col>
				</Row>
			</Container>
		</div>
	);
};

export default ProductPage;
