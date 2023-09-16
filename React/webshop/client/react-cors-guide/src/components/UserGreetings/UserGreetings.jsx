import { useContext } from "react";
import { CustomerContext } from "..//../App";
import "./UserGreetings.css";

export default function UserGreetings() {
	const [customerGlobal] = useContext(CustomerContext);
	const loggedIn = customerGlobal.loggedIn;
	if (loggedIn) {
		return (
			<h1 className="gtext">{`Hi ${customerGlobal.firstName}, end of the month?`}</h1>
		);
	}
	return <h1 className="gtext">End of the month?</h1>;
}
