import axios from "axios";


export const API_SERVER_HOST = "http://localhost:8080";

const prefix = `${API_SERVER_HOST}/api/todo`

export const getOne = async (tno) => {

	const res = await axios.get(`${prefix}/${tno}`);

	return res.data
}


// async 모든 리턴은 비동기

export const getList = async (pageParam) => {

	const {page, size} = pageParam

	const res = await axios.get(`${prefix}/list`,{
		params:{...pageParam}
	})

	return res.data

}