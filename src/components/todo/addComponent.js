import React, {useState} from 'react';


const initState = {
	title : '',
	content : '',
	dueDate: ''
}

function AddComponent(props) {

	const [todo, setTodo] = useState({...initState}); // 새로 만들어서 처리?

	const handleChangeTodo = (e) => {
		// todo[title] // 동적처리

		console.log(e.target.name, e.target.value);
		todo[e.target.name] = e.target.value;

		setTodo({...todo});
	}

	const handleClickAdd = () => {
		console.log(todo)
	}

	return (
			<div className = "border-2 border-sky-200 mt-10 m-2 p-4">
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">TITLE</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="title" type={'text'} value={todo.title} onChange={handleChangeTodo}></input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">CONTENTS</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="content" type={'text'} value={todo.content} onChange={handleChangeTodo}></input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">DUEDATE</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="dueDate" type={'date'} value={todo.dueDate} onChange={handleChangeTodo}></input>
					</div>
				</div>
				<div className="flex justify-end">
					<div className="relative mb-4 flex p-4 flex-wrap items-stretch">
						<button type="button"
						        onClick={handleClickAdd}
						        className="rounded p-4 w-36 bg-blue-500 text-xl text-white" > ADD </button>
					</div>
				</div>
			</div>
	);
}

export default AddComponent;