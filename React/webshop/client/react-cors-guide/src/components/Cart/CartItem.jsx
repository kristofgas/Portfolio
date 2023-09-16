import * as api from "../../API.js";
import { Container, Button } from "react-bootstrap";
import { useState, useContext, useEffect } from "react";
import { CustomerContext } from "../../App";
import "./style.jsx";
import {
	Top,
	TopButton,
	SectionHeader,
	ProductDetail,
	TitleCart,
	CartRow,
	Total,
	Text,
	CartColumn,
	ProductName,
	PriceDetail,
	ProductAmountContainer,
	ProductAmount,
	ProductColor,
	ProductSize,
	ProductId,
} from "./style";
import "../../globalStyles.css";

//cartitem får et id fra cart

export default function CartItem(props) {
	const { item } = props;
	const itemId = item.itemId;

	const [product, setProduct] = useState([]);
	useEffect(() => {
		fetchData();
	}, []);

	const multiply = (num1, num2) => {
		return num1 * num2;
	};

	const fetchData = () => {
		api
			.getItem(itemId)
			.then((p) => setProduct(p))
			.catch((error) => {
				console.log(error);
			});
	};

	return (
		<div>
			{" "}
			{product.itemName}, {multiply(item.itemQuantity, product.itemPrice)}€,{" "}
			{item.itemQuantity}
		</div>
	);
}
