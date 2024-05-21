import React, {useEffect} from 'react';
import {getOne} from "../../api/todoApi";

const initState = {
	tno : 0,
	title : '',
	content : '',
	dueDate : '',
	complete : false,
}

function ReadComponent({tno}) {


	const [todo, setTodo] = React.useState(initState);

	// 어떤 상태가 됐을 때 작동할건지

	useEffect(() => {

		getOne(tno)
				.then(data => {
					console.log(data);
					setTodo(data);
				})
	}, [tno]);

	// deps : 상태 변경 조건


	return (
			<div className={"border-2 border-sky-200 mt-10 m-2 p-4"}>

				{makeDiv('Tno', todo.tno)}
				{makeDiv('Content', todo.content)}
				{makeDiv('title',todo.title)}
				{makeDiv('Due Date', todo.dueDate)}
				{makeDiv('Complete', todo.complete ? 'Completed' : 'Not Yet')}

			</div>
	);
}

const makeDiv = (title, value) =>
		<div className={"flex justify-center"}>
			<div className={"relative mb-4 flex w-full flex-wrap items-stretch"}>
				<div className={"w-1/5 p-6 text-right font-bold"}>{title}</div>
				<div className={"w-4/5 p-6 rounded-r border border-solid shadow-md"}>
					{value}
				</div>
			</div>
		</div>

export default ReadComponent;