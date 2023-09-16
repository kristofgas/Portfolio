import React, { useMemo, useState } from "react";
import { Dropdown } from "react-bootstrap";

export default function Size_Filter() {
	// Store currently selected size
	//const [category, setCategory] = useState("");
	const [message, displayText] = useState("Select size");

	const updateSize = (event) => {
		displayText("hej");
	};

	// Memoized results. Will re-evaluate any time selected
	// category changes
	//const filteredData = useMemo(() => {
	// if (!category || category === 36) return data;

	//return data.filter((item) => item.type === category);
	//}, [category]);

	return (
		<Dropdown>
			<Dropdown.Toggle variant="success" id="dropdown-basic">
				{message}
			</Dropdown.Toggle>

			<Dropdown.Menu>
				<Dropdown.Item onClick={updateSize}>36</Dropdown.Item>
				<Dropdown.Item eventKey={37}>37</Dropdown.Item>
				<Dropdown.Item eventKey={38}>38</Dropdown.Item>
				<Dropdown.Item eventKey={39}>39</Dropdown.Item>
				<Dropdown.Item eventKey={40}>40</Dropdown.Item>
				<Dropdown.Item eventKey={41}>41</Dropdown.Item>
				<Dropdown.Item eventKey={42}>42</Dropdown.Item>
				<Dropdown.Item eventKey={43}>43</Dropdown.Item>
			</Dropdown.Menu>
		</Dropdown>
	);
}
