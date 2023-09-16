import React from "react";
import { useNavigate } from "react-router-dom";
import { useState, useContext, useEffect } from "react";
import { CustomerContext } from "../../App";
import * as api from "../../API.js";
import {
	Container,
	Card,
	Label,
	Image,
	Link,
	Info,
	Title,
	Price,
	IconContainer,
} from "./styles/Item";
import * as Icon from "react-bootstrap-icons";

export default function Item(props) {
	const { item } = props;
	const [customerGlobal, setCustomerGlobal] = useContext(CustomerContext);
	// const [basket, addToBasket] = useState(
	// 	api.addItemToBasket(customerid, itemid, 1).then((p) => addToBasket(p))
	// );

	async function addToBasket() {
		await api.addItemToBasket(customerGlobal.id, item.itemId, 1);
	}

	// async function removeFromBasket() {
	//   await api.addItemToBasket(customerGlobal.id,product.itemId,-1);
	//   }

	let navigate = useNavigate();
	const routeChange = () => {
		let path = `/product/?id=${item.itemId}`;
		navigate(path);
	};
	const routeChange2 = () => {
		addToBasket();
		let path = `/cart`;
		navigate(path);
	};

	return (
		<Container>
			<Card>
				<Label>{item.itemOnSale} </Label>
				<Image src={item.itemIMG1} alt={item.itemName} onClick={routeChange} />
				<Info>
					<Title>{item.itemName}</Title>
					<Price>{item.itemPrice}€</Price>

					<IconContainer onClick={routeChange2}>
						<Icon.CartFill />
					</IconContainer>
				</Info>
			</Card>
		</Container>
	);
}
