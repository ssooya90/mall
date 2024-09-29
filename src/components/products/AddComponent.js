import React, {useRef, useState} from 'react';
import {postAdd} from "../../api/productsApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";


/* 상품정의
상품 이름
상품 설명
상품 가격
이미지(파일)
 */

const initState = {
	pname: '',
	pdesc: '',
	price: 0,
	files: []
}



// 1번 방식 new FomrData() => Post 방식
// 2번 방식 new FomrData() => Put 방식

function AddComponent(props) {

	const [product, setProduct] = useState(initState)
	// const [fetch, setFetch] = useState(false)
	// const [result, setResult] = useState(false)

	const {moveToList} = useCustomMove();

	const addMutation = useMutation({
		mutationFn: (product) => postAdd(product),
	});

	const uploadRef = useRef() // 돔 노드 식별

	const handleChangeProduct = (e) => {

		product[e.target.name] = e.target.value
		setProduct({...product})

		console.log("기본")
		console.log(product)

		console.log("전개 연산자")
		console.log({...product})

		// 객체로 하나씩 펼쳐서 보여줌

	}

	const handleClickAdd = (e) => {

		console.log(product)

		const formData = new FormData();

		const files = uploadRef.current.files

		console.log(files)

		for (let i = 0; i < files.length; i++) {
			// 브라우저에서 서버로 보낼 떄 이름
			formData.append("files", files[i])
		}
		formData.append("pname", product.pname)
		formData.append("pdesc", product.pdesc)
		formData.append("price", product.price)

		console.log(formData)

		// postAdd(fo).then(result => {
		//
		// 	// console.log(result)
		// 	// setResult(result.TNO)
		// 	// // 입력 완료 후 기존 항목 제거
		// 	//
		// 	// setTodo({...initState})
		// })

		// setFetch(true);

		addMutation.mutate(formData);

		// postAdd(formData).then(result => {
		//
		// 	setFetch(false);
		//
		// 	console.log("리절트")
		// 	console.log(result)
		//
		// 	setResult(result.result)
		//
		// 	// console.log(result);
		// 	//
		// 	// setProduct({...initState})
		//
		// })
	}

	const queryClient = useQueryClient();

	const closeModal = () => {

		queryClient.invalidateQueries("products/list")
		// setResult(null);
		moveToList({page:1});
	}


	return (
			<div className="border-2 border-sky-200 mt-10 m-2 p-4">
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Product Name</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
						       name="pname" type={'text'} value={product.pname} onChange={handleChangeProduct}>
						</input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Desc</div>
						<textarea
								className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y"
								name="pdesc" rows="4" onChange={handleChangeProduct} value={product.pdesc}>
{product.pdesc}
</textarea>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Price</div>
						<input
								className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
								name="price" type={'number'} value={product.price} onChange={handleChangeProduct}>
						</input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Files</div>
						<input
								ref={uploadRef}
								className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
								type={'file'} multiple={true}>
						</input>
					</div>
				</div>
				<div className="flex justify-end">
					<div className="relative mb-4 flex p-4 flex-wrap items-stretch">
						<button type="button"
						        className="rounded p-4 w-36 bg-blue-500 text-xl text-white "
						        onClick={handleClickAdd}>
							ADD
						</button>
					</div>
				</div>

				{addMutation.isPending ? <FetchingModal/> : <></>}

				{addMutation.isSuccess ? <ResultModal
						callbackFn={closeModal}
						title={"Product Add Result"}
						// content = {`${result} 번 상품 등록 완료`}
						content = {`${addMutation.data.result} 번 상품 등록 완료`}
				/> : <></>}

			</div>




	);
}

export default AddComponent;