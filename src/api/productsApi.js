import {API_SERVER_HOST} from "./todoApi";
import axios from "axios";

const host = `${API_SERVER_HOST}/api/products`

// async 모든 리턴은 비동기
export const postAdd = async (product) => {

	const header = {headers : {'Content-Type' : 'multipart/form-data'}}
	const res = await axios.post(`${host}/`,product,header)
	return res.data
}

export const getList = async (pageParam) => {

	const {page,size} = pageParam

	const res = await axios.get(`${host}/list`, {params:
				{page:page,size:size }})

	return res.data;



}



