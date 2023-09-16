import React, { useState, useEffect } from "react";
import "../globalStyles.css";
import Item from "../components/Item/Index";
import * as api from "../API.js";
import Loading from "../components/Loading/Loading";
import Herobanner from "../components/Herobanner/Herobanner";

//HomePage works and now is displaying nothing because data is not passing trough +
// once data will be displayed we will need a list of items --> that's why the Items component is created for
// let read = false;
export default function HomePage() {
	const [data, setData] = useState();
	const [read, setState] = useState(false);

	useEffect(() => {
		getItems();
	}, []);

	const getItems = async () => {
		try {
			let items = await api.getItems();
			await setData(items);
			console.log(items);
			setState(true);
		} catch (error) {
			console.log(error);
		}
	};

	if (read) {
		return (
			<div>
				<Herobanner />
				<div className="main-container">
					<div className="cards-container">
						{data.map((item) => (
							<Item key={item.itemId} item={item}></Item>
						))}
					</div>
				</div>
			</div>
		);
	}
	return <Loading />;
}
