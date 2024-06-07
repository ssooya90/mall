import React from 'react';
import AddComponent from "../../components/todo/addComponent";

function AddPage(props) {

	return (
			<div className={"p-4 w-full bg-white"}>
				<div className={'text-3xl font-extrabold'}>
					Todo ADd Page
				</div>

				<AddComponent/>

			</div>
	);
}

export default AddPage;