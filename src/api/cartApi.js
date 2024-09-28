import {API_SERVER_HOST} from "./todoApi";
import JwtUtil from "../util/jwtUtil";
import jwtAxios from "../util/jwtUtil";


const host = `${API_SERVER_HOST}/api/cart`

export const getCartItems = async () => {

	console.log("items 실행 전")

	const res = await jwtAxios.get(`${host}/items`)

	console.log("items 실행 후")
	console.log(res.data)
	
	return res.data
}


export const postChangeCart = async (cartItem) => {
	const res = await jwtAxios.post(`${host}/change`,cartItem)
	return res.data
}
