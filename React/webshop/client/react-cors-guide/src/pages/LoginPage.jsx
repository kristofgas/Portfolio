import { Form, Button, Row, Col, Container } from "react-bootstrap";
import { useState, useContext, useEffect } from "react";
import { CustomerContext } from "../App";
import * as api from "../API.js";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
	const [customerGlobal, setCustomerGlobal] = useContext(CustomerContext);
	const [customer, setCustomer] = useState({
		id: customerGlobal.id,
		firstName: "",
		lastName: "",
		email: "",
		loggedIn: customerGlobal.loggedIn,
		basket: [],
	});

	useEffect(() => {
		fetchData();
	}, []);

	const fetchData = () => {
		api
			.getBasket(customerGlobal.id)
			.then((p) => updateBasket(p))
			.catch((error) => {
				console.log(error);
			});
	};

	function updateBasket(basketUpdate) {
		setCustomer((currState) => ({
			...currState,
			basket: basketUpdate,
		}));
	}

	function handleChange(event) {
		setCustomer({
			...customer,
			[event.target.name]: event.target.value,
		});
		console.log(customer.firstName);
	}

	async function handleSubmit(event) {
		event.preventDefault();
		customer.loggedIn = true;
		updateBasket();
		await setCustomerGlobal(customer);
		await updateCustomer(customer);
		console.log(customer);
	}

	async function updateCustomer(customer) {
		await api.updateCustomer(customer);
	}

	function HomeButton() {
		let navigate = useNavigate();
		function handleClick() {
			navigate("/home");
		}
		return (
			<Button variant="outline-danger" onClick={handleClick}>
				Cancel
			</Button>
		);
	}

	if (customerGlobal.loggedIn && customerGlobal.firstName) {
		return (
			<h1 className="gtext">{`Hi ${customerGlobal.firstName}, End of the month?`}</h1>
		);
	}

	return (
		<Container className="container_form">
			<Form className="signup_form" div class="signup_form">
				<Form.Group className="mb-3" controlId="formFirstName">
					<Form.Label>First name:</Form.Label>
					<Form.Control
						type="text"
						name="firstName"
						placeholder="Enter first name:"
						value={customer.firstName.value}
						onChange={handleChange}
					/>
					<Form.Text className="text-muted">
						We'll never share your name with anyone else.
					</Form.Text>
				</Form.Group>

				<Form.Group className="mb-3" controlId="formSecondName">
					<Form.Label>Second name</Form.Label>
					<Form.Control
						type="text"
						name="secondName"
						placeholder="Enter second name"
						value={customer.lastName.value}
						onChange={handleChange}
					/>
				</Form.Group>

				<Form.Group className="mb-3" controlId="formEmail">
					<Form.Label>Email</Form.Label>
					<Form.Control
						type="email"
						name="email"
						placeholder="Enter your email"
						value={customer.email.value}
						onChange={handleChange}
					/>
				</Form.Group>
				<Form.Group className="mb-3" controlId="formBasicCheckbox"></Form.Group>

				<Row style={{ display: "flex" }}>
					<Col className="align-content-center">
						<Button variant="warning" type="submit" onClick={handleSubmit}>
							Submit
						</Button>
					</Col>
					<Col className="align-content-right">
						<HomeButton />
					</Col>
				</Row>
			</Form>
		</Container>
	);
}
