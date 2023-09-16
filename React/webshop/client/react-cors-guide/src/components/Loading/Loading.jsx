import React from "react";
import ReactLoading from "react-loading";
import "./Loading.css";

export default function Loading() {
	return (
		<div className="loadingPage">
			<ReactLoading type="spokes" color="#24242c" height={100} width={50} />
		</div>
	);
}
