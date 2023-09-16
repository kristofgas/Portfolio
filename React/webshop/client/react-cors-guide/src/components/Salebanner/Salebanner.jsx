import React from "react";
import { useContext } from "react";
import { CustomerContext } from "..//../App";

import "./Salebanner.css";
export default function Salebanner() {
	const [customerGlobal] = useContext(CustomerContext);
	const loggedIn = customerGlobal.loggedIn;
	if (!loggedIn) {
		return (
			<h1 className="sale">
				{" "}
				25% OFF FOR YOUR FIRST PURCHASE - GET THE DISCOUNT, SIGN UP
			</h1>
		);
	}
}
