import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import {useState} from "react";

const getNum = (param, defaultValue) => {


	if(!param) return defaultValue;

	return parseInt(param)

}


const useCustomMove = () => {

	const navigate = useNavigate();

	// 새로고침을 위한 상태값 추가
	const [refresh, setRefresh] = useState(false)

	const [queryParams] = useSearchParams()

	const page = getNum(queryParams.get('page'), 1);
	const size = getNum(queryParams.get('size'), 10);

	const queryDefault = createSearchParams({page, size}).toString();

	const moveToList = (pageParam) => {

		let queryStr = ""

		console.log("디폴트")
		console.log(queryDefault)


		console.log("페이지 파람")
		console.log(pageParam)

		if(pageParam){
			const pageNum = getNum(pageParam.page, 1);
			const sizeNum = getNum(pageParam.size, 10);
			queryStr = createSearchParams({page:pageNum, size:sizeNum}).toString()

		}else{
			queryStr = queryDefault
		}

		console.log(queryStr)
		console.log("네비게이터")

		// 토글처럼 작동하기 위한 코드
		setRefresh(!refresh)

		navigate({pathname : `../list`, search:queryStr})
	}

	const moveToModify = (num) => {

		navigate({
			pathname : `../modify/${num}`,
			search : queryDefault
		})

	}

	const moveToRead = (num) => {

		navigate({
			pathname : `../read/${num}`,
			search : queryDefault
		})

	}

	return {moveToList, moveToModify, moveToRead, page, size, refresh}
}

export default useCustomMove