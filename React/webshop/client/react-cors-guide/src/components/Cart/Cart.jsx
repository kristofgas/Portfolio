import * as api from "../../API.js";
import { Container, Button } from "react-bootstrap";
import { useState, useContext, useEffect } from "react";
import { CustomerContext } from "../../App";
import CartItem from "./CartItem.jsx";
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

import swal from "sweetalert";

export default function Cart() {
	const [customerGlobal, setCustomerGlobal] = useContext(CustomerContext);
	// const [products, setBasket] = useState(api.getBasket(customerGlobal.id).then(p => setBasket(p)));
	const [products, setBasket] = useState([]);
	const [totalItemCount, setTotalItemCount] = useState([]);
	useEffect(() => {
		fetchData();
	}, []);

	const fetchData = () => {
		api
			.getBasket(customerGlobal.id)
			.then((p) => setBasket(p))
			.then()
			.catch((error) => {
				console.log(error);
			});
	};

	async function addToBasket(idd) {
		await api.addItemToBasket(customerGlobal.id, idd, 1);
		calculateTotal();
		fetchData();
	}

	async function removeFromBasket(idd) {
		await api.addItemToBasket(customerGlobal.id, idd, -1);
		calculateTotal();
		// setTotalItemCount(calculateTotal());
		fetchData();
	}

	const handleClickOpen = (event) => {
		//setOpen(true);
		swal(
			"Congratulations!",
			"Your order has been successfully realized!\nThank you for shopping with us!"
		);
	};

	const calculateTotal = () => {
		const totalItem = Array.from(products)?.reduce((total, item) => {
			return total + item.itemQuantity;
		}, 0);

		setTotalItemCount(totalItem);
	};

	return (
		<>
			<div>
				<Container>
					<CartRow>
						<Top>
							<TopButton>BACK TO SHOP</TopButton>
						</Top>
					</CartRow>
					<SectionHeader>
						<TitleCart>CART</TitleCart>
					</SectionHeader>

					<ProductName>
						<Text>ITEM</Text>
					</ProductName>
					<ProductName>
						<Text>PRICE</Text>
					</ProductName>
					<ProductName>
						<Text>QUANTITY</Text>
					</ProductName>

					{/* <Button variant="outline-secondary" onClick={getBasket}>
						Show basket
					</Button> */}

					{Array.from(products)?.map((item, i) => (
						<div key={i}>
							<span>
								<CartColumn>
									<Text>
										<b>Product:</b>SHOES
									</Text>{" "}
									<ProductDetail>
										{/* itemID: {item.itemId} , ItemQuantity: {item.itemQuantity}&nbsp;
								&nbsp; info: */}
										<CartItem key={item.itemId} item={item}></CartItem>

										<Button
											className="removeBtn"
											onClick={() => removeFromBasket(item.itemId)}
										>
											-
										</Button>
										<ProductAmount>{item.itemQuantity}</ProductAmount>
										<Button
											className="addBtn"
											onClick={() => addToBasket(item.itemId)}
										>
											+
										</Button>
									</ProductDetail>
								</CartColumn>

								<CartRow></CartRow>
							</span>
						</div>
					))}

					<Total>
						<PriceDetail>
							<Text>
								Ammount of items: {totalItemCount} <br />
								(note: Ammount only works if quantity-buttons are clicked, and
								the ammout deviates by one )
							</Text>
						</PriceDetail>
					</Total>

					<CartColumn />
				</Container>
				<Button className="purchaseBtn" onClick={handleClickOpen}>
					CHECKOUT
				</Button>
			</div>
		</>
	);
	// const addToBasket = async () => {
	// 	fetch(`http://localhost:4000/basket/${user.id}/product/${prodID}`, {
	// 		method: "PUT",
	// 	});
	// 	alert(`${product.name} has been added to basket`);
	// };
}
